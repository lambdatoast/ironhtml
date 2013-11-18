package ironhtml
import scala.language.postfixOps

object IronHTML {

  def main(args: Array[String]) {
    import Operations._
    import Element._
    import Element.constructors._
    import Renderer._

    val e1 = a("111".text).copy(attrs = List("href" -> "http://www.google.com"))
    val e2 = strong("222".text)
    val e3 = span("333".text)
    val e4 = "input".void.copy(attrs = List("type" -> "checkbox", "checked" -> ""))
    val e5 = fold(List(e1, e2, e3, e4).map(li))(ul(empty))(add)
    val e6 = map(e5) { 
      case HTMLElement("ul", c, _) => p(c)
      case HTMLElement("li", c, _) => div(c)
      case e => e 
    }
    val t1 = render(e5) // <ul><li><a href="http://www.google.com">111</a></li><li><strong>222</strong></li><li><span>333</span></li><li><input type="checkbox" checked></li></ul>
    val t2 = render(e6) // <p><div><a href="http://www.google.com">111</a></div><div><strong>222</strong></div><div><span>333</span></div><div><input type="checkbox" checked></div></p>
    println(t1)

    println(render(li("hey".text) ++ li("second".text)))
    println(map(e5) {
      case list@HTMLElement("ul", c, _) => list <<< ul(li("hey".text) ++ li("second".text))
      case e => e 
    } render)
  }

}

