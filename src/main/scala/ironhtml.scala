package ironhtml

object ContentModels {
  sealed trait Embedded
  sealed trait Flow
  sealed trait Heading
  sealed trait Interactive
  sealed trait Metadata
  sealed trait Phrasing
  sealed trait Sectioning
}

object IronHTML {
  type HTMLAttribute = (String,String)
  sealed trait HTMLExpr
  case class HTMLElement(name: String, content: HTMLExpr, attrs: List[HTMLAttribute] = Nil) extends HTMLExpr
  case class HTMLVoidElement(name: String, attrs: List[HTMLAttribute] = Nil) extends HTMLExpr
  case class HTMLContent(content: List[HTMLExpr]) extends HTMLExpr
  case class Text(content: String) extends HTMLExpr

  object ops {
    def fold(xs: List[HTMLExpr])(z: HTMLExpr)(f: (HTMLExpr, HTMLExpr) => HTMLExpr): HTMLExpr =
      xs.foldLeft(z)(f)
    def map(e: HTMLExpr)(f: HTMLExpr => HTMLExpr): HTMLExpr = e match {
      case HTMLElement(n,c,xs) => f(HTMLElement(n, map(c)(f), xs))
      case HTMLVoidElement(n,_) => f(e)
      case HTMLContent(xs) => HTMLContent(xs.foldRight(List[HTMLExpr]())((e, acc) => map(e)(f) :: acc))
      case Text(c) => f(e)
    }
    def concat(e1: HTMLExpr, e2: HTMLExpr): HTMLExpr =
      HTMLContent(List(e1, e2))

    def add(e1: HTMLExpr, e2: HTMLExpr): HTMLExpr =
      e1 match {
        case HTMLElement(n,c,xs) => HTMLElement(n, concat(c,e2), xs)
        case _ => concat(e1, e2)
      }

    def merge(e1: HTMLExpr, e2: HTMLExpr): HTMLExpr =
      e1 match {
        case HTMLElement(n,c,xs) => e2 match {
          case HTMLElement(n2,c2,ys) if (n2 == n) => HTMLElement(n, concat(c, c2), xs ++ ys)
          case _ => concat(e1,e2)
        }
        case _ => concat(e1, e2)
      }

    implicit class SyntaxHTMLExpr(e: HTMLExpr) {
      def add(e2: HTMLExpr): HTMLExpr = ops.add(e, e2)
      def `|+|`(e2: HTMLExpr): HTMLExpr = ops.add(e, e2)
      def concat(e2: HTMLExpr): HTMLExpr = ops.concat(e, e2)
      def `++`(e2: HTMLExpr): HTMLExpr = ops.concat(e, e2)
      def merge(e2: HTMLExpr): HTMLExpr = ops.merge(e, e2)
      def `<<<`(e2: HTMLExpr): HTMLExpr = ops.merge(e, e2)
    }
  }

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

  object renderer {
    def attrs(xs: List[HTMLAttribute]): String = xs.foldLeft("") { (acc,a) => acc + " " + a._1 + (if (a._2 != "") s"""="${a._2}"""" else "") }
    def render(e: HTMLExpr): String = e match {
      case HTMLElement(n,c,xs) => s"<${n}${attrs(xs)}>${render(c)}</$n>"
      case HTMLVoidElement(n,xs) => s"<${n}${attrs(xs)}>"
      case HTMLContent(xs) => xs.foldLeft("") { (acc, e) => acc + render(e) }
      case Text(c) => c
    }
    implicit class SyntaxRender(e: HTMLExpr) {
      def render: String = renderer.render(e)
    }
  }

  def main(args: Array[String]) {
    import ops._
    import constructors._
    import renderer._

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

}
