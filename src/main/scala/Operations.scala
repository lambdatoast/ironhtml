package ironhtml

object Operations {
  import Element._

  /*
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
    def add(e2: HTMLExpr): HTMLExpr = Operations.add(e, e2)
    def `|+|`(e2: HTMLExpr): HTMLExpr = Operations.add(e, e2)
    def concat(e2: HTMLExpr): HTMLExpr = Operations.concat(e, e2)
    def `++`(e2: HTMLExpr): HTMLExpr = Operations.concat(e, e2)
    def merge(e2: HTMLExpr): HTMLExpr = Operations.merge(e, e2)
    def `<<<`(e2: HTMLExpr): HTMLExpr = Operations.merge(e, e2)
  }
  */
}
