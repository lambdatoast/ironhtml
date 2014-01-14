package ironhtml
import scala.language.implicitConversions

object ContentModels {
  sealed trait AnyContent
  trait Embedded extends AnyContent
  trait Flow extends AnyContent
  trait Heading extends AnyContent
  trait Interactive extends AnyContent
  trait Metadata extends AnyContent
  trait Phrasing extends AnyContent
  trait Sectioning extends AnyContent

  object evidences {
    import UnionTypes._
    sealed trait Lowest {
      implicit def toFstOf3[T <: AnyContent](x: T): Any3[T,Nothing,Nothing] = FstOf3(x)
    }
    trait Low extends Lowest {
      implicit def toSndOf3[T <: AnyContent](x: T): Any3[Nothing,T,Nothing] = SndOf3(x)
    }
    implicit object High extends Low {
      implicit def toTrdOf3[T <: AnyContent](x: T): Any3[Nothing,Nothing,T] = TrdOf3(x)
    }
  }
}

