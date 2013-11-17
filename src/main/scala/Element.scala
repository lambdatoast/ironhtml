package ironhtml

object Element {
  import ContentModels._
  import Union._
  sealed trait Element { 
    val content: String
  }
  trait AddAnyOf[B[_], R] {
    type AllowedContent[_] = B[_]
    def add[T : AllowedContent](e: T): R 
  }
  trait Add[T,U] {
    def add(e: T): U 
  }
  case class Anchor(c: String) extends Element with AddAnyOf[`|v|3`[Phrasing,Interactive,Embedded]#λ, Element] {
    val content = c
    def add[T : AllowedContent](e: T) = e match {
      case e: Phrasing => new Element { val content = "Added Phrasing content" }
      case p: Interactive => new Element { val content = "Added Interactive content" }
      case p: Embedded => new Element { val content = "Added Embedded content" }
    }
  }
  case class Select(c: String) extends Element with Flow with Phrasing with Interactive {
    val content = c
  }
  case class Span(c: String) extends Element with Flow with Phrasing with Add[Phrasing, Element] {
    val content = c
    def add(e: Phrasing) = new Element { val content = "Added Phrasing content" }
  }
  /**
   * TODO: Should only add "Flow content, but with no main element descendants."
   * @see [[http://www.whatwg.org/specs/web-apps/current-work/multipage/sections.html#the-nav-element]]
   */
  case class Nav(c: String) extends Element with Flow with Sectioning with Add[Flow, Element] {
    val content = c
    def add(e: Flow) = new Element { val content = "Added Flow content" }
  }
}
