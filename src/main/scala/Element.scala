package ironhtml
import scala.language.higherKinds
import scala.language.implicitConversions

object Element {

  type Attribute = (String,String)

  sealed trait ElementType
  case object Normal extends ElementType
  case object Void extends ElementType

  sealed trait NonElementContent
  case class Text(text: String) extends NonElementContent
  case class ElementList(els: List[Element]) extends NonElementContent
  object evidences {
    implicit def toRight(t: NonElementContent): Either[Nothing, NonElementContent] = Right(t)
    implicit def toLeft(e: Element): Either[Element, Nothing] = Left(e)
  }

  trait Add {
    type ContentModel
    type AdditionResult
    def add(a: ContentModel): AdditionResult
    def `|+|`(a: ContentModel) = add(a)
  }

  trait Element {
    val elType: ElementType
    val name: String
    val attrs: List[Attribute]
    val content: Option[Either[Element,NonElementContent]]
  }

}
