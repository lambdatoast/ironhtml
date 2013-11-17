package ironhtml

object Renderer {
  import Element._

  def render(e: Element): String = e match {
    case Text(s) => s
    case _ => e.content match {
      case Some(c) => e.name.getOrElse("")
      case None => ""
    }
  }
}

