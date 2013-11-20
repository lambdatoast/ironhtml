package ironhtml

object Renderer {
  import Element._
  def attrs(xs: List[Attribute]): String = xs.foldLeft("") { (acc,a) => acc + " " + a._1 + (if (a._2 != "") s"""="${a._2}"""" else "") }

  def render(e: Element): String =
    e.content match {
      case Some(Left(c)) => e.elType match {
        case Normal => s"<${e.name}${attrs(e.attrs)}>${render(c)}</${e.name}>"
        case Void   => s"<${e.name}${attrs(e.attrs)}>"
      }
      case Some(Right(Text(c))) => e.elType match {
        case Normal => s"<${e.name}${attrs(e.attrs)}>${c}</${e.name}>"
        case Void   => s"<${e.name}${attrs(e.attrs)}>"
      }
      case Some(Right(ElementList(xs))) => e.elType match {
        case _ => xs.foldLeft("") { (acc, e) => acc + render(e) }
      }
      case None => e.elType match {
        case Normal => s"<${e.name}${attrs(e.attrs)}></${e.name}>"
        case Void   => s"<${e.name}${attrs(e.attrs)}>"
      }
    }

}

