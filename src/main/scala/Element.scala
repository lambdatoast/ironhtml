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

  sealed trait NonElementContent
  case class Text(text: String) extends NonElementContent
  case class ElementList(els: List[Element]) extends NonElementContent
  object NonElementContent {
    object evidences {
      implicit def toRight(t: NonElementContent): Either[Nothing, NonElementContent] = Right(t)
      implicit def toLeft(e: Element): Either[Element, Nothing] = Left(e)
    }
  }

  import NonElementContent.evidences._

  sealed trait Add {
    type ContentModel
    type AdditionResult
    def add(a: ContentModel): AdditionResult
    def `|+|`(a: ContentModel) = add(a)
  }

  sealed trait Element {
    val elType: ElementType
    val name: String
    val attrs: List[Attribute]
    val content: Option[Either[Element,NonElementContent]]
  }

  case class Div(content: Option[Either[Element, NonElementContent]], attrs: List[Attribute] = Nil) extends Element with Flow with Add {
    val elType = Normal
    val name = "div"
    type ContentModel = Any3[Element with Flow,Element with Interactive,Element with Sectioning]
    type AdditionResult = Div
    def add(a: Any3[Element with Flow,Element with Interactive,Element with Sectioning]) = a match {
      case FstOf3(e) => Div(Some(e), attrs)
      case SndOf3(e) => Div(Some(e), attrs)
      case TrdOf3(e) => Div(Some(e), attrs)
    }
  }
  case class H1(content: Option[Either[Element, NonElementContent]], attrs: List[Attribute] = Nil) extends Element with Flow with Heading with Add {
    val elType = Normal
    val name = "h1"
    type ContentModel = Element with Phrasing
    type AdditionResult = H1
    def add(a: ContentModel) = H1(Some(a), attrs)
  }

}
