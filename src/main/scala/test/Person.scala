package test

/**
 * User: bigbully
 * Date: 14/11/16
 * Time: 下午12:59
 */
class Person(val name:String) extends Selfish with Unselfish{

  val subName = "peter"

  def printFullname = {
    println(name + "|" + subName)
  }

}
