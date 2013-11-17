package ironhtml

object IronHTML {
  import Element._
  import Element.evidences._
  import Renderer._

  def main(args: Array[String]) {
    println("")
    println(render(Anchor(Some(Text("go to github")), List("href" -> "http://github.com"))))
    println("")
  }

}
