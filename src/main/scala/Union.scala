package ironhtml

/**
 * Union type using Curry-Howard isomorphism
 *
 * @see [[http://www.chuusai.com/2011/06/09/scala-union-types-curry-howard/]]
 */
object Union {

  type ¬[A] = A => Nothing
  type ¬¬[A] = ¬[¬[A]]
  type v[T, U] = ¬[¬[T] with ¬[U]]
  type `|v|`[T, U] = { type λ[X] = ¬¬[X] <:< (T v U) }

  type `|v|3`[T, U, V] = { type λ[X] = ¬¬[X] <:< ¬[¬[T] with ¬[U] with ¬[V]] }

  /**
  def size[T](t: T)(implicit ev: (¬¬[T] <:< (Int v String))): Int = t match {
    case i: Int => i
    case s: String => s.length
  }
  */
  def size[T : `|v|`[Int,String]#λ](t: T): Int = t match {
    case i: Int => i
    case s: String => s.length
  }

}

