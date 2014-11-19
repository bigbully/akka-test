package subclass

/**
 * User: bigbully
 * Date: 14/11/19
 * Time: 下午10:25
 */
class MySubscriber(id:Int) {

  def print(event:AnyRef) {
    println(event)
  }

}
