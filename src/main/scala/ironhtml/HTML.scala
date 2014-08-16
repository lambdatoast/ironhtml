package ironhtml
import scalaz._
import Scalaz._

object HTML {

  import scala.collection.immutable.Set

  type NormalAttribute = (String,String)
  type BooleanAttribute = String
  type Attribute = Either[NormalAttribute, BooleanAttribute]
  // TODO: Represent "enumerated attributes".
  type AttributeList = Set[Attribute]
  type StringOrElement = Either[String, HTMLElement]

  sealed abstract class HTMLElement

  /** http://www.whatwg.org/specs/web-apps/current-work/multipage/text-level-semantics.html#the-a-element */
  final case class HTMLA(content: List[StringOrElement], attrs: AttributeList) extends HTMLElement

  /** http://www.whatwg.org/specs/web-apps/current-work/multipage/grouping-content.html#the-div-element */
  final case class HTMLDiv(content: List[StringOrElement], attrs: AttributeList) extends HTMLElement

  /** http://www.whatwg.org/specs/web-apps/current-work/multipage/the-button-element.html#the-fieldset-element */
  final case class HTMLFieldset(content: (Option[HTMLLegend], List[HTMLElement]), attrs: AttributeList) extends HTMLElement

  /** http://www.whatwg.org/specs/web-apps/current-work/multipage/text-level-semantics.html#the-i-element */
  final case class HTMLI(content: String, attrs: AttributeList) extends HTMLElement

  /** http://www.whatwg.org/specs/web-apps/current-work/multipage/the-input-element.html#the-input-element */
  final case class HTMLInput(attrs: AttributeList) extends HTMLElement

  /** http://www.whatwg.org/specs/web-apps/current-work/multipage/forms.html#the-label-element */
  final case class HTMLLabel(content: String, attrs: AttributeList) extends HTMLElement

  final case class HTMLLegend(content: String, attrs: AttributeList) extends HTMLElement

  /** http://www.whatwg.org/specs/web-apps/current-work/multipage/grouping-content.html#the-li-element */
  final case class HTMLLi(content: List[HTMLElement], attrs: AttributeList) extends HTMLElement

  /** http://www.whatwg.org/specs/web-apps/current-work/multipage/sections.html#the-nav-element */
  final case class HTMLNav(content: List[HTMLElement], attrs: AttributeList) extends HTMLElement

  final case class HTMLOption(content: String, attrs: AttributeList) extends HTMLElement
  final case class HTMLSelect(content: List[HTMLOption], attrs: AttributeList) extends HTMLElement

  /** http://www.whatwg.org/specs/web-apps/current-work/multipage/text-level-semantics.html#the-span-element */
  final case class HTMLSpan(content: List[Either[String, HTMLElement]], attrs: AttributeList) extends HTMLElement

  /** http://www.whatwg.org/specs/web-apps/current-work/multipage/the-button-element.html#the-textarea-element */
  final case class HTMLTextarea(content: String, attrs: AttributeList) extends HTMLElement

  /** http://www.whatwg.org/specs/web-apps/current-work/multipage/grouping-content.html#the-ul-element */
  final case class HTMLUl(content: List[HTMLLi], attrs: AttributeList) extends HTMLElement

  object AttributeList {

    object Ops {

      def leftFilter(f: NormalAttribute => Boolean)(attrs: AttributeList): AttributeList =
        attrs.toList filter {
          _ match {
            case Left(kv) => f(kv)
            case k            => false
          }
        } toSet

      def leftMap(f: NormalAttribute => NormalAttribute)(attrs: AttributeList): AttributeList =
        attrs.toList map {
          _ match {
            case Left(kv) => Left(f(kv))
            case k            => k
          }
        } toSet

      def leftFind(f: NormalAttribute => Boolean)(attrs: AttributeList): Option[NormalAttribute] =
        attrs.find {
          _ match {
            case Left(kv) => f(kv)
            case k => false
          }
        } flatMap {
          _ match {
            case Left(kv) => Some(kv)
            case k => None
          }
        }

      def find(name: String)(attrs: AttributeList): Option[Attribute] =
        attrs.find {
          _ match {
            case Left((k, v)) if (k === name) => true
            case Right(k) if (k === name) => true
          }
        }
    }

  }

  object Attribute {
    object Ops {
      def asNormalAttr(a: Attribute): Option[NormalAttribute] =
        a match {
          case Left(kv) => Some(kv)
          case Right(_) => None
        }

      def asBooleanAttr(a: Attribute): Option[BooleanAttribute] =
        a match {
          case Left(_) => None
          case Right(x) => Some(x)
        }
    }
  }

}

object HTMLInputDataTypes {
  /** http://www.whatwg.org/specs/web-apps/current-work/multipage/the-input-element.html#attr-input-type */
  sealed abstract class HTMLInputType
  final case object Hidden extends HTMLInputType
  final case object Text extends HTMLInputType
  final case object Email extends HTMLInputType
  final case object Password extends HTMLInputType
  final case object Number extends HTMLInputType
  final case object File extends HTMLInputType
  final case object Submit extends HTMLInputType
  final case object Button extends HTMLInputType
}
