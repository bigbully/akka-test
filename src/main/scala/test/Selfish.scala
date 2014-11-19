package test

/**
 * User: bigbully
 * Date: 14/11/16
 * Time: 下午12:58
 */
trait Selfish { this:Person =>

  val a = "1"

  final def attack:String = {
    name + "-" + subName
  }

  final def myPrint = printFullname

}
