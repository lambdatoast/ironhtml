package ironhtml

object Element {
  import ContentModels._
  import Union._
  trait Element { 
    val content: String
  }
  trait Add[B[_], R] {
    def add[T : B](e: T): R 
  }
  def a(c: String) = new Element with Add[`|v|3`[Phrasing,Interactive,Embedded]#λ, Element] {
    val content = c
    def add[T : `|v|3`[Phrasing, Interactive, Embedded]#λ](e: T) = e match {
      case e: Phrasing => new Element { val content = "Added Phrasing content" }
      case p: Interactive => new Element { val content = "Added Interactive content" }
      case p: Embedded => new Element { val content = "Added Embedded content" }
    }
  }
  def select(c: String) = new Element with Flow with Phrasing with Interactive {
    val content = c
  }
}
