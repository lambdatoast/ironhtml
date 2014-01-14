package ironhtml

object Elements {

  import UnionTypes._
  import ContentModels._
  import Element._
  import Element.evidences._

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
