package test

import java.util.concurrent.Executor
import scala.concurrent.{CanAwait, BlockContext}
import scala.annotation.tailrec

/**
 * User: bigbully
 * Date: 14/11/10
 * Time: 下午11:10
 */
trait BatchingExecutor extends Executor {

  // invariant: if "_tasksLocal.get ne null" then we are inside BatchingRunnable.run; if it is null, we are outside
  private val _tasksLocal = new ThreadLocal[List[Runnable]]()

  private class Batch(val initial: List[Runnable]) extends Runnable with BlockContext {
    private var parentBlockContext: BlockContext = _

    // this method runs in the delegate ExecutionContext's thread
    override def run(): Unit = {
      require(_tasksLocal.get eq null)

      val prevBlockContext = BlockContext.current
      BlockContext.withBlockContext(this) {
        try {
          parentBlockContext = prevBlockContext

          @tailrec def processBatch(batch: List[Runnable]): Unit = batch match {
            case Nil ⇒ ()
            case head :: tail ⇒
              _tasksLocal set tail
              try {
                head.run()
              } catch {
                case t: Throwable ⇒
                  // if one task throws, move the
                  // remaining tasks to another thread
                  // so we can throw the exception
                  // up to the invoking executor
                  val remaining = _tasksLocal.get
                  _tasksLocal set Nil
                  unbatchedExecute(new Batch(remaining)) //TODO what if this submission fails?
                  throw t // rethrow
              }
              processBatch(_tasksLocal.get) // since head.run() can add entries, always do _tasksLocal.get here
          }

          processBatch(initial)
        } finally {
          _tasksLocal.remove()
          parentBlockContext = null
        }
      }
    }

    override def blockOn[T](thunk: ⇒ T)(implicit permission: CanAwait): T = {
      // if we know there will be blocking, we don't want to keep tasks queued up because it could deadlock.
      {
        val tasks = _tasksLocal.get
        _tasksLocal set Nil
        if ((tasks ne null) && tasks.nonEmpty)
          unbatchedExecute(new Batch(tasks))
      }

      // now delegate the blocking to the previous BC
      require(parentBlockContext ne null)
      parentBlockContext.blockOn(thunk)
    }
  }

  protected def unbatchedExecute(r: Runnable): Unit

  override def execute(runnable: Runnable): Unit = {
    if (batchable(runnable)) {
      // If we can batch the runnable
      _tasksLocal.get match {
        case null ⇒ unbatchedExecute(new Batch(List(runnable))) // If we aren't in batching mode yet, enqueue batch
        case some ⇒ _tasksLocal.set(runnable :: some) // If we are already in batching mode, add to batch
      }
    } else unbatchedExecute(runnable) // If not batchable, just delegate to underlying
  }

  /** Override this to define which runnables will be batched. */
  def batchable(runnable: Runnable): Boolean = runnable match {
    case b: Batchable ⇒ b.isBatchable
    case _: scala.concurrent.OnCompleteRunnable ⇒ true
    case _ ⇒ false
  }
}
trait Batchable extends Runnable {
  def isBatchable: Boolean
}
class MyExecutor extends BatchingExecutor{
  override protected def unbatchedExecute(r: Runnable): Unit = r.run()
}


