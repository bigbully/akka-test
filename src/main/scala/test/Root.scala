package test

/**
 * User: bigbully
 * Date: 14/11/19
 * Time: 下午8:57
 */
class Root(var values1:String) {
  values1 += "Root"
}
class Node(val values: String) extends Root(values) {

  def getValues = {
    values1 + "|" + values
  }
}



object MyApp extends App {
  val node = new Node("myNode")
  println(node.getValues)
}

