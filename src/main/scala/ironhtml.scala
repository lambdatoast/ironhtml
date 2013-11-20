package ironhtml
import scala.language.postfixOps
import scala.language.implicitConversions

object IronHTML {

  def main(args: Array[String]) {
    testElement()
  }
  def testElement() {
    import Element._
    import ContentModels.evidences.High._
    import Renderer._
    import Operations._
    import Operations.evidences._
    import Element.NonElementContent.evidences._

    val a = Div(None, List("class" -> "row-fluid"))
    val b = Div(None) |+| H1(Some(Text("Introduction")), List("id" -> "introduction"))
    // val c = H1(None).add(Div(None)) -- Compile Error :)
    val c = Div(Some(a ++ b))
    val d = Div(Some(Text("Hello "))) <<< Div(Some(Text("World!")))
    val e = Div(None) |+| H1(Some(Text("Conclusion")), List("id" -> "conclusion"))

    println("")
    println(render(a))
    println(render(b))
    println(render(c))
    println(render(d))
    println(render(a |+| b <<< e))
    println("")
  }

}

