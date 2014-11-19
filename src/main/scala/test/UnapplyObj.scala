package test

/**
 * User: bigbully
 * Date: 14/11/17
 * Time: 下午9:40
 */
object UnapplyObj {

  def unapply(str:String):Option[Int] = {
    Some(str.toInt)
  }


}
