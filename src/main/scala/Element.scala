package ironhtml

object Element {
  import ContentModels._
  import Union._

  sealed trait Element {
    val content: Option[Element]
    val name: Option[String]
  }
  trait AddAnyOf[B[_], R] {
    type AllowedContent[_] = B[_]
    def add[T : AllowedContent](e: T): R 
  }
  trait Add[T,U] {
    def add(e: T): U 
  }
  case class Anchor(content: Option[Element]) extends Element with AddAnyOf[`|v|3`[Phrasing,Interactive,Embedded]#λ, Element] {
    val name = Some("a")
    def add[T : AllowedContent](e: T) = e match {
      case e: Phrasing => this
      case p: Interactive => this
      case p: Embedded => this
    }
  }
  case class Select(content: Option[Element]) extends Element with Flow with Phrasing with Interactive {
    val name = Some("select")
  }
  case class Span(content: Option[Element]) extends Element with Flow with Phrasing with Add[Phrasing, Element] {
    val name = Some("span")
    def add(e: Phrasing) = this
  }
  /**
   * TODO: Should only add "Flow content, but with no main element descendants."
   * @see [[http://www.whatwg.org/specs/web-apps/current-work/multipage/sections.html#the-nav-element]]
   */
  case class Nav(content: Option[Element]) extends Element with Flow with Sectioning with Add[Flow, Element] {
    val name = Some("nav")
    def add(e: Flow) = this
  }

  // Especial Elements

  case class Text(text: String) extends Element { 
    val content = None
    val name = None
  }

  implicit object evidences {
    implicit def toElementOption(e: Element): Option[Element] = Some(e)
  }

}
