package ironhtml

object Element {

  type HTMLAttribute = (String,String)
  sealed trait HTMLExpr
  case class HTMLElement(name: String, content: HTMLExpr, attrs: List[HTMLAttribute] = Nil) extends HTMLExpr
  case class HTMLVoidElement(name: String, attrs: List[HTMLAttribute] = Nil) extends HTMLExpr
  case class HTMLContent(content: List[HTMLExpr]) extends HTMLExpr
  case class Text(content: String) extends HTMLExpr

  object constructors {
    def a(e: HTMLExpr): HTMLElement = "a".el(e)
    def div(e: HTMLExpr): HTMLElement = "div".el(e)
    def empty: HTMLExpr = HTMLContent(Nil)
    def li(e: HTMLExpr): HTMLElement = "li".el(e)
    def p(e: HTMLExpr): HTMLElement = "p".el(e)
    def span(e: HTMLExpr): HTMLElement = "span".el(e)
    def strong(e: HTMLExpr): HTMLElement = "strong".el(e)
    def ul(e: HTMLExpr): HTMLElement = "ul".el(e)

    implicit class SyntaxString(s: String) {
      def el(content: HTMLExpr) = HTMLElement(s, content)
      def void = HTMLVoidElement(s)
      def text = Text(s)
    }
  }
}
