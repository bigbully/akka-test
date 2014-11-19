package subclass

import SubclassifiedIndex.{SubclassifiedIndex, Subclassification}
import scala.collection.immutable


/**
 * User: bigbully
 * Date: 14/11/19
 * Time: 下午10:21
 */
class SubchannelClassification {

  type Event = AnyRef
  type Classifier = Class[_]
  type Subscriber = MySubscriber


  protected implicit def subclassification: Subclassification[Classifier]  = new Subclassification[Class[_]] {
    def isEqual(x: Class[_], y: Class[_]) = x == y
    def isSubclass(x: Class[_], y: Class[_]) = y isAssignableFrom x
  }

  private lazy val subscriptions = new SubclassifiedIndex[Classifier, Subscriber]()

  @volatile
  private var cache = Map.empty[Classifier, Set[Subscriber]]

  protected def classify(event: AnyRef): Class[_] = event.getClass

  def subscribe(subscriber: Subscriber, to: Classifier): Boolean = subscriptions.synchronized {
    val diff = subscriptions.addValue(to, subscriber)
    addToCache(diff)
    diff.nonEmpty
  }

  def unsubscribe(subscriber: Subscriber, from: Classifier): Boolean = subscriptions.synchronized {
    val diff = subscriptions.removeValue(from, subscriber)
    // removeValue(K, V) does not return the diff to remove from or add to the cache
    // but instead the whole set of keys and values that should be updated in the cache
    diff.nonEmpty
  }

  private def addToCache(changes: immutable.Seq[(Classifier, Set[Subscriber])]): Unit =
    cache = (cache /: changes) {
      case (m, (c, cs)) ⇒ m.updated(c, m.getOrElse(c, Set.empty[Subscriber]) ++ cs)
    }

  protected def publish(event: AnyRef, subscriber: Subscriber) = {
    subscriber.print(event)
  }

  def publish(event: Event): Unit = {
    val c = classify(event)
    val recv =
      if (cache contains c) cache(c) // c will never be removed from cache
      else subscriptions.synchronized {
        if (cache contains c) cache(c)
        else {
          addToCache(subscriptions.addKey(c))
          cache(c)
        }
      }
    recv foreach (publish(event, _))
  }
}

object MyApp extends App {
  val s = new SubchannelClassification
  s.subscribe(new MySubscriber(1), classOf[Person])
  s.subscribe(new MySubscriber(2), classOf[Teacher])

  s.publish(new Teacher("peter"))

  println("finish")
}
