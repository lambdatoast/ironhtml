package ironhtml

object ContentModels {
  sealed trait AnyContent
  sealed trait Embedded extends AnyContent
  sealed trait Flow extends AnyContent
  sealed trait Heading extends AnyContent
  sealed trait Interactive extends AnyContent
  sealed trait Metadata extends AnyContent
  sealed trait Phrasing extends AnyContent
  sealed trait Sectioning extends AnyContent
}

object elements {
  import ContentModels._
  trait Element { 
    type ContentModel
    val content: String
  }
  def add[E <: Element](e: E)(b: Either[E,e.ContentModel]): E = e
  def div(c: String) = new Element { 
    type ContentModel = AnyContent
    val content = c
  }
  def a(c: String) = new Element with Phrasing {
    type ContentModel = Embedded with Phrasing
    val content = c
  }
  def select(c: String) = new Element with Flow with Phrasing with Interactive {
    type ContentModel = Embedded with Phrasing
    val content = c
  }
  object conversions {
    implicit def toEitherL[E <: Element](e: E): Either[E,Nothing] = Left(e)
    implicit def toEitherR[C <: AnyContent](c: C): Either[Nothing, C] = Right(c)
  }

}

object IronHTML {
  import elements._
  import elements.conversions._
  import ContentModels._

  def main(args: Array[String]) {

    println("")
    println(add(div("hello"))(a("world")))
    println(add(div("hello"))(div("world")))
    println(add(a("hello"))(div("world")))
    println("")

  }

}
