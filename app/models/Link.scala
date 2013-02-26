package models

import com.novus.salat._
import com.mongodb.casbah.Imports._
import mongoHelper._
import play.api.libs.json._
import com.mongodb.casbah.commons.TypeImports.ObjectId
import play.api.libs.functional.syntax._


case class Link(
  url: String,
  description: String,
  id: ObjectId = new ObjectId
)

object Links {
  implicit object linkWrites extends Writes[models.Link] {
    def writes(l: models.Link) = Json.toJson(
      Map(
        "url" -> Json.toJson(l.url),
        "description" -> Json.toJson(l.description),
        "id" -> Json.toJson(l.id.toString)
      )
    )
  }

  implicit val linkReads: Reads[models.Link] = (
    (__ \ "url").read[String] and
      (__ \ "description").read[String] and
      (__ \ "id").read[ObjectId]
    )(models.Link.apply _)

  val links = MongoConnection()("PlayersNext")("links")

  def all = links.map(grater[Link].asObject(_)).toList

  def create(link: Link) {
    links += grater[Link].asDBObject(link)
  }

  def findById(id: String): Option[Link] = {
    val o : DBObject = MongoDBObject("_id" -> new ObjectId(id))
    links.findOne(o).map(grater[Link].asObject(_))
  }

  def delete(link: Link) {
    links -= grater[Link].asDBObject(link)
  }
}
