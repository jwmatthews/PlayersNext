package models

import com.novus.salat._
import com.mongodb.casbah.Imports._
import com.novus.salat.{TypeHintFrequency, StringTypeHintStrategy, Context}
import play.api.Play
import play.api.Play.current

/**
 *  Creating a Salat 'context' from:
     https://github.com/leon/play-salat/blob/master/sample/app/models/mongoContext.scala
*/
package object mongoContext {
  implicit val context = {
    val context = new Context {
      val name = "global"
      override val typeHintStrategy = StringTypeHintStrategy(when = TypeHintFrequency.WhenNecessary, typeHint = "_t")
    }
    context.registerGlobalKeyOverride(remapThis = "id", toThisInstead = "_id")
    context.registerClassLoader(Play.classloader)
    context
  }
}
import mongoContext._

case class Registration(
  username: String,
  password: String,
  confirm: String,
  realName: String
  )

object Registrations {
  val registrations = MongoConnection()("scala_play_mongo_example")("registrations")

  def all = registrations.map(grater[Registration].asObject(_)).toList
  def create(registration: Registration) {
    registrations += grater[Registration].asDBObject(registration)
  }

}
