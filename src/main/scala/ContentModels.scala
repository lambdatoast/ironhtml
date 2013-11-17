package ironhtml

object ContentModels {
  sealed trait AnyContent
  trait Embedded extends AnyContent
  trait Flow extends AnyContent
  trait Heading extends AnyContent
  trait Interactive extends AnyContent
  trait Metadata extends AnyContent
  trait Phrasing extends AnyContent
  trait Sectioning extends AnyContent
}

