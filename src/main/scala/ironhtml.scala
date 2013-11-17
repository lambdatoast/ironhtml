package ironhtml

object IronHTML {
  import Element._

  def main(args: Array[String]) {

    println("")
    //println(add(div("hello"))(a("world")))
    //println(add(div("hello"))(div("world")))
    //println(add(a("hello"))(select("world")))
    println(Anchor("hello").add(Select("world")).content)
    println("")

  }

}
