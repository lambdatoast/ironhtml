package ironhtml

object Renderer {
  import Element._
  def attrs(xs: List[HTMLAttribute]): String = xs.foldLeft("") { (acc,a) => acc + " " + a._1 + (if (a._2 != "") s"""="${a._2}"""" else "") }
  def render(e: HTMLExpr): String = e match {
    case HTMLElement(n,c,xs) => s"<${n}${attrs(xs)}>${render(c)}</$n>"
    case HTMLVoidElement(n,xs) => s"<${n}${attrs(xs)}>"
    case HTMLContent(xs) => xs.foldLeft("") { (acc, e) => acc + render(e) }
    case Text(c) => c
  }
  implicit class SyntaxRender(e: HTMLExpr) {
    def render: String = Renderer.render(e)
  }
}

