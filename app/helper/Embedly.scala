package helper

import play.api.Play
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.ws.WS

import models.Thumbnail
import models.Links.thumbnailWrites
import models.Links.thumbnailReads

object Embedly {
  case class Keyword(score: Int, name: String)
  case class Extract(title: Option[String],
                      description: Option[String],
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


  implicit object ExtractWrites extends Writes[Extract] {
    def writes(e: Extract) = Json.toJson(
      Map(
        "title"  -> Json.toJson(e.title.getOrElse("")),
        "description" -> Json.toJson(e.description.getOrElse("")),
        "thumbnails" -> Json.toJson(e.thumbnails),
        "keywords" -> Json.toJson(e.keywords)
      )
    )
  }

  implicit val keywordReads: Reads[Keyword] = (
      (__ \ "score").read[Int] and
      (__ \ "name").read[String]
    )(Keyword.apply _)


  implicit val extractReads: Reads[Extract] = (
      (__ \ "title").readNullable[String] and
      (__ \ "description").readNullable[String] and
      (__ \ "images").read[List[Thumbnail]] and
      (__ \ "keywords").read[List[Keyword]]
    )(Extract.apply _)

  def lookup(url: String) = {
    WS.url(LOOKUP_URL)
      .withQueryString("key" -> KEY, "url" -> url).get
  }
}
