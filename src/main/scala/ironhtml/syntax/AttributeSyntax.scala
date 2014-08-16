package ironhtml
package syntax
import scalaz._
import Scalaz._
import HTML.{Attribute, AttributeList, NormalAttribute, BooleanAttribute}

final class AttributeListSyntax(attrs: AttributeList) {
  def get(k: String): Option[NormalAttribute] = AttributeList.Ops.leftFind(kv => kv._1 === k)(attrs)
  def :?(k: String): Boolean = get(k).map(_ => true).getOrElse(false)
}

final class AttributeSyntax(a: Attribute) {
  def asNormalAttr: Option[NormalAttribute] =
    Attribute.Ops.asNormalAttr(a)

  def asBooleanAttr: Option[BooleanAttribute] =
    Attribute.Ops.asBooleanAttr(a)
}

object AttributeSyntax {
  implicit def ToAttributeListSyntax(attrs: AttributeList): AttributeListSyntax =
    new AttributeListSyntax(attrs)

  implicit def ToAttributeSyntax(a: Attribute): AttributeSyntax =
    new AttributeSyntax(a)
}
