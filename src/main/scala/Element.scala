package ironhtml

object Element {
  import ContentModels._
  import Union._

  sealed trait ElementType
  case object Normal extends ElementType
  case object Void extends ElementType

  type Attribute = (String,String)

  sealed trait Element {
    val name: String
    val elType: ElementType
    val attrs: List[Attribute]
    val content: Option[Either[Element,Text]]
  }
  trait AddAnyOf[B[_], R] {
    type AllowedContent[_] = B[_]
    def add[T : AllowedContent](e: T): R 
  }
  trait Add[T,U] {
    def add(e: T): U 
  }
  case class Anchor(content: Option[Either[Element, Text]], attrs: List[Attribute] = Nil) extends Element with AddAnyOf[`|v|3`[Phrasing,Interactive,Embedded]#λ, Element] {
    val name = "a"
    val elType = Normal
    def add[T : AllowedContent](e: T) = e match {
      case e: Phrasing => this
      case p: Interactive => this
      case p: Embedded => this
    }
  }
  case class Select(content: Option[Either[Element, Text]], attrs: List[Attribute] = Nil) extends Element with Flow with Phrasing with Interactive {
    val elType = Normal
    val name = "select"
  }
  case class Span(content: Option[Either[Element, Text]], attrs: List[Attribute] = Nil) extends Element with Flow with Phrasing with Add[Phrasing, Element] {
    val elType = Normal
    val name = "span"
    def add(e: Phrasing) = this
  }
  /**
   * TODO: Should only add "Flow content, but with no main element descendants."
   * @see [[http://www.whatwg.org/specs/web-apps/current-work/multipage/sections.html#the-nav-element]]
   */
  case class Nav(content: Option[Either[Element, Text]], attrs: List[Attribute] = Nil) extends Element with Flow with Sectioning with Add[Flow, Element] {
    val elType = Normal
    val name = "nav"
    def add(e: Flow) = this
  }

  sealed trait Content
  case class Text(text: String) extends Content

  implicit object evidences {
    implicit def toRight(t: Text): Either[Nothing, Text] = Right(t)
    implicit def toLeft(e: Element): Either[Element, Nothing] = Left(e)
  }

}
