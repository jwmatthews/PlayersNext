package helper

import play.api.Play
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.ws.WS

object Embedly {
  case class Keyword(score: Int, name: String)
  case class Thumbnail(size: Int, width: Int, height: Int, url: String)
  case class Extract(title: String,
                      description: String,
                      thumbnails: List[Thumbnail],
                      keywords: List[Keyword])

  val KEY = Play.current.configuration.getString("embedly.key").get
  val LOOKUP_URL = Play.current.configuration.getString("embedly.extract.url").get

  implicit object KeywordWrites extends Writes[Keyword] {
    def writes(k: Keyword) = Json.toJson(
      Map(
        "score"  -> Json.toJson(k.score.toString),
        "name" -> Json.toJson(k.name)
      )
    )
  }

  implicit object ThumbailWrites extends Writes[Thumbnail] {
    def writes(t: Thumbnail) = Json.toJson(
      Map(
        "size"  -> Json.toJson(t.size.toString),
        "width" -> Json.toJson(t.width.toString),
        "height" -> Json.toJson(t.height.toString),
        "url" -> Json.toJson(t.url)
      )
    )
  }

  implicit object ExtractWrites extends Writes[Extract] {
    def writes(e: Extract) = Json.toJson(
      Map(
        "title"  -> Json.toJson(e.title),
        "description" -> Json.toJson(e.description),
        "thumbnails" -> Json.toJson(e.thumbnails),
        "keywords" -> Json.toJson(e.keywords)
      )
    )
  }

  implicit val keywordReads: Reads[Keyword] = (
      (__ \ "score").read[Int] and
      (__ \ "name").read[String]
    )(Keyword.apply _)

  implicit val thumbnailReads: Reads[Thumbnail] = (
      (__ \ "size").read[Int] and
      (__ \ "width").read[Int] and
      (__ \ "height").read[Int] and
      (__ \ "url").read[String]
    )(Thumbnail.apply _)

  implicit val extractReads: Reads[Extract] = (
      (__ \ "title").read[String] and
      (__ \ "description").read[String] and
      (__ \ "images").read[List[Thumbnail]] and
      (__ \ "keywords").read[List[Keyword]]
    )(Extract.apply _)

  def lookup(url: String) = {
    WS.url(LOOKUP_URL)
      .withQueryString("key" -> KEY, "url" -> url).get
  }
}
