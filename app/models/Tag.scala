package models

import com.novus.salat._
import com.mongodb.casbah.Imports.{ MongoConnection, WriteConcern }
import com.mongodb.casbah.query.Imports._
// Note: Was seeing below in ref to usage of $inc when importing only:
//     import com.mongodb.casbah.Imports._
//   could not find implicit value for
//   evidence parameter of type com.mongodb.casbah.query.Imports.ValidNumericType[Int]
// import com.mongodb.casbah.Imports._

import mongoHelper._
import play.api.libs.json._
import com.mongodb.casbah.commons.TypeImports.ObjectId
import play.api.libs.functional.syntax._


case class Tag (
  id: ObjectId = new ObjectId(),
  value: String,
  refs: Int = 0
)

object Tag {
  implicit object tagWrites extends Writes[models.Tag] {
    def writes(t: models.Tag) = Json.toJson(
      Map(
        "id" -> Json.toJson(t.id.toString),
        "value" -> Json.toJson(t.value),
        "refs" -> Json.toJson(t.refs.toString)
      )
    )
  }

  implicit val tagReads: Reads[models.Tag] = (
    (__ \ "id").read[ObjectId] and
      (__ \ "value").read[String] and
      (__ \ "refs").read[Int]
    )(models.Tag.apply _)

  val tags = MongoConnection()("PlayersNext")("tags")

  def all = tags.map(grater[Tag].asObject(_)).toList

  def create(tag: Tag) {
    tags += grater[Tag].asDBObject(tag)
  }

  def findById(id: String): Option[Tag] = {
    val o : DBObject = MongoDBObject("_id" -> new ObjectId(id))
    tags.findOne(o).map(grater[Tag].asObject(_))
  }

  def findByValue(value: String): Option[Tag] = {
    val o: DBObject = MongoDBObject("_id" -> new ObjectId(value))
    tags.findOne(o).map(grater[Tag].asObject(_))
  }

  def delete(tag: Tag) {
    tags -= grater[Tag].asDBObject(tag)
  }

  def update(tag: Tag) {
    val q: DBObject = MongoDBObject("_id" -> tag.id)
    tags.update(q, grater[Tag].asDBObject(tag))
  }

  def increment(value: String) = {
    tags.update(MongoDBObject("value" -> value), $inc("refs" -> 1))
  }

  def decrement(value: String) = {
    tags.update(MongoDBObject("value" -> value), $inc("refs" -> -1))
  }
}
