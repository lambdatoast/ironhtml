package ironhtml

object IronHTML {
  import Element._
  import Element.evidences._
  import Renderer._

  def main(args: Array[String]) {
    val a = Anchor(Some(Text("go to github")), List("href" -> "http://github.com"))
    println("")
    println(render(a))
    println("")
  }

}
