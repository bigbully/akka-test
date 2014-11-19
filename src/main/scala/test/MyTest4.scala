package test

/**
 * User: bigbully
 * Date: 14/11/17
 * Time: 下午9:41
 */
object MyTest4 extends App{

  "123" match {
    case UnapplyObj(i) => println(i)
  }

}
