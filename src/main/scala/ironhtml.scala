package ironhtml

object IronHTML {
  import Element._
  import Element.evidences._
  import Renderer._

  def main(args: Array[String]) {

    println("")
    //println(add(div("hello"))(a("world")))
    //println(add(div("hello"))(div("world")))
    //println(add(a("hello"))(select("world")))
    //println(render(Anchor(Text("google.com")).add(Select(None))))
    println(render(Anchor(Text("google.com"))))
    println("")

  }

}
