package ironhtml
import scala.language.postfixOps
import scala.language.implicitConversions

object IronHTML {

  def main(args: Array[String]) {
    //testConstraints()
    testElement()
  }
  def testElement() {
    import Element._
    import ContentModels.evidences.High._
    import Renderer._
    println("")
    val a = Div(None, List("class" -> "row-fluid")).add(Div(None))
    val b = Div(None).add(H1(None, List("id" -> "introduction")))
    println(render(a))
    println(render(b))
    // val c = H1(None).add(Div(None)) -- Error
    println("")
  }

  def testConstraints() {
    object ContentModels {
      sealed trait AnyContent
      trait Embedded extends AnyContent
      trait Flow extends AnyContent
      trait Heading extends AnyContent
      trait Interactive extends AnyContent
      trait Metadata extends AnyContent
      trait Phrasing extends AnyContent
      trait Sectioning extends AnyContent

      object evidences {
        import UnionTypes._
        sealed trait Lowest {
          implicit def toFstOf3[T <: AnyContent](x: T): Any3[T,Nothing,Nothing] = FstOf3(x)
        }
        trait Low extends Lowest {
          implicit def toSndOf3[T <: AnyContent](x: T): Any3[Nothing,T,Nothing] = SndOf3(x)
        }
        implicit object High extends Low {
          implicit def toTrdOf3[T <: AnyContent](x: T): Any3[Nothing,Nothing,T] = TrdOf3(x)
        }
      }
    }
    import UnionTypes._
    import ContentModels._
    import ContentModels.evidences.High._

    def add(a: Flow, b: Any3[Embedded, Flow, Heading]): Flow = a

    println("")

    println(add(new Flow {}, new Embedded {})) // OK
    println(add(new Flow {}, new Flow {})) // OK
    println(add(new Flow {}, new Heading {})) // OK
    // println(add(new Flow {}, new Interactive {})) // compile time ERROR :)
    println(add(new Flow {}, new Interactive with Embedded {})) // OK
    println(add(new Flow {}, new Embedded with Flow {})) // OK! implicit conflict resolved by prioritization

    println("")
  }

  /*
  def testHTMLCreation() {
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
  */

}

