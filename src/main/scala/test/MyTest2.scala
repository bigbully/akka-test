package test

/**
 * User: bigbully
 * Date: 14/11/16
 * Time: 下午1:02
 */
object MyTest2 extends App{

  val p = new Person("Carter") with Selfish with Unselfish

  println(p.attack)

}
