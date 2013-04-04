package models

import com.novus.salat._
import com.mongodb.casbah.Imports._
import mongoHelper._
import play.api.libs.json._
import com.mongodb.casbah.commons.TypeImports.ObjectId
import play.api.libs.functional.syntax._


case class Link(
  id: Option[ObjectId] = Some(new ObjectId),
  url: String,
  title: Option[String],
  description: Option[String],
  thumbnail: Option[Thumbnail],
  tags: List[String]
)

case class Thumbnail(
  url: String,
  width: Int,
  height: Int,
  size: Int)

case class Comment(
  msg: String,
  id: ObjectId = new ObjectId,
  parent: ObjectId
)


object Links {

  implicit object thumbnailWrites extends Writes[Thumbnail] {
    def writes(t: Thumbnail) = Json.toJson(
      Map(
        "url" -> Json.toJson(t.url),
        "width" -> Json.toJson(t.width),
        "height" -> Json.toJson(t.height),
        "size" -> Json.toJson(t.size)
      )
    )
  }
  implicit object commentWrites extends Writes[models.Comment] {
    def writes(c: models.Comment) = Json.toJson(
      Map(
        "msg" -> Json.toJson(c.msg),
        "id" -> Json.toJson(c.id.toString),
        "parent" -> Json.toJson(c.parent.toString)
      )
    )
  }
  implicit object linkWrites extends Writes[models.Link] {
    def writes(l: models.Link) = Json.toJson(
      Map(
        "id"  -> Json.toJson(l.id.getOrElse("").toString),
        "url" -> Json.toJson(l.url),
        "title" -> Json.toJson(l.title.getOrElse("")),
        "description" -> Json.toJson(l.description.getOrElse("")),
        "tags" -> Json.toJson(l.tags)
        //"comments" -> Json.toJson(l.comments)
      )
    )
  }

  implicit val thumbnailReads: Reads[Thumbnail] = (
    (__ \ "url").read[String] and
      (__ \ "width").read[Int] and
      (__ \ "height").read[Int] and
      (__ \ "size").read[Int]
    )(Thumbnail.apply _)

  implicit val commentReads: Reads[models.Comment] = (
    (__ \ "msg").read[String] and
      (__ \ "id").read[ObjectId] and
      (__ \ "parent").read[ObjectId]
  )(models.Comment.apply _)

  implicit val linkReads: Reads[models.Link] = (
    (__ \ "id").readNullable[ObjectId] and
      (__ \ "url").read[String] and
      (__ \ "title").readNullable[String] and
      (__ \ "description").readNullable[String] and
      (__ \ "thumbnail").readNullable[Thumbnail] and
      (__ \ "tags").read[List[String]]
    )(models.Link.apply _)

  val links = MongoConnection()("PlayersNext")("links")

  def all = links.map(grater[Link].asObject(_)).toList

  def ensureIndexes() = {
    links.ensureIndex(
      MongoDBObject("url" -> 1), "url_index", true
    )
  }

  def create(link: Link) {
    links += grater[Link].asDBObject(link)
    link.tags.map(t => Tags.increment(t))
  }

  def findById(id: String): Option[Link] = {
    val o : DBObject = MongoDBObject("_id" -> new ObjectId(id))
    links.findOne(o).map(grater[Link].asObject(_))
  }

  def delete(link: Link) {
    links -= grater[Link].asDBObject(link)
    link.tags.map(t => Tags.decrement(t))
  }

  def update(link: Link) {
    val q: DBObject = MongoDBObject("_id" -> link.id)
    links.update(q, grater[Link].asDBObject(link))
  }

  def addTag(id: String, tag: String) = {
    links.update(MongoDBObject("_id" -> new ObjectId(id)), $addToSet("tags" -> tag))
    Tags.increment(tag)
  }

  def deleteTag(id: String, tag: String) = {
    links.update(MongoDBObject("_id" -> new ObjectId(id)), $pull("tags" -> tag))
    Tags.decrement(tag)
  }

  def addComment = ()
  def deleteComment = ()
  def updateComment = ()

}
