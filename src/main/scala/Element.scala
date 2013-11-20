package ironhtml
import scala.language.implicitConversions
import scala.language.higherKinds

object Element {
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

  type Attribute = (String,String)

  sealed trait ElementType
  case object Normal extends ElementType
  case object Void extends ElementType

  sealed trait Content
  case class Text(text: String) extends Content
  object Content {
    object evidences {
      implicit def toRight(t: Text): Either[Nothing, Text] = Right(t)
      implicit def toLeft(e: Element): Either[Element, Nothing] = Left(e)
    }
  }

  import Content.evidences._

  sealed trait Element {
    val elType: ElementType
    val name: String
    val attrs: List[Attribute]
    val content: Option[Either[Element,Text]]
  }

  case class Div(content: Option[Either[Element, Text]], attrs: List[Attribute] = Nil) extends Element with Flow {
    val elType = Normal
    val name = "div"
    def add[T <: Element](a: Any3[T with Flow,T with Interactive,T with Sectioning]) = a match {
      case FstOf3(e) => Div(Some(e), attrs)
      case SndOf3(e) => Div(Some(e), attrs)
      case TrdOf3(e) => Div(Some(e), attrs)
    }
  }
  case class H1(content: Option[Either[Element, Text]], attrs: List[Attribute] = Nil) extends Element with Flow with Heading {
    val elType = Normal
    val name = "h1"
    def add[T <: Element](e: T with Phrasing) = H1(Some(e), attrs)
  }

}
