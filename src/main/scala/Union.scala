package ironhtml

object UnionTypes {
  sealed trait Any3[+A, +B, +C]
  case class FstOf3[A](x: A) extends Any3[A, Nothing, Nothing]
  case class SndOf3[B](x: B) extends Any3[Nothing, B, Nothing]
  case class TrdOf3[C](x: C) extends Any3[Nothing, Nothing, C]
}
