package ironhtml

object Operations {
  import Element._
  import Elements._

  def concat(a: Element, b: Element): ElementList = ElementList(List(a, b))

  def merge[A <: Element,B <: Element](a: A, b: B)(implicit m: Merge[A]) = m.merge(a,b)

  object evidences extends Syntax with MergeEvidences

  trait Syntax {
    implicit class OpSyntax[T <: Element](a: T) {
      def concat(b: Element): ElementList = Operations.concat(a, b)
      def `++`(b: Element): ElementList = concat(b)
      def `<<<`(b: Element)(implicit m: Merge[T]) = Operations.merge(a,b)
    }
  }

  // Typeclasses for operations

  trait Merge[A <: Element] {
    def merge[B <: Element](a: A, b: B): A
  }

  trait MergeEvidences {
    import Element.evidences._

    implicit object divMerge extends Merge[Div] {
      def merge[T <: Element](a: Div, b: T) = a.content match {
        case Some(Left(ea)) => b.content match {
          case Some(Left(eb)) => a.copy(content = Some(concat(ea,eb)))
          case _ => a
        }
        case Some(Right(Text(ta))) => b.content match {
          case Some(Right(Text(tb))) => a.copy(content = Some(Text(ta ++ tb)))
          case _ => a
        }
        case _ => a
      }
    }
  }
}
