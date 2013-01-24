package models

import com.novus.salat._
import com.mongodb.casbah.Imports._
import play.api.Play
import play.api.Play.current
import mongoContext._

case class Link(
  url: String,
  description: String
)

object Links {
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
