package models

import com.novus.salat.{TypeHintFrequency, StringTypeHintStrategy, Context}
import play.api.Play
import play.api.Play.current
import play.api.libs.json._
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.TypeImports.ObjectId
import play.api.libs.json.JsString
import com.novus.salat.StringTypeHintStrategy
import play.api.data.validation.ValidationError
import play.api.libs.json.JsSuccess
import play.api.libs.functional.syntax._


object mongoHelper {
  /**
  *  Creating a Salat 'context' from:
     https://github.com/leon/play-salat/blob/master/sample/app/models/mongoHelper.scala
  */
  implicit val context = {
    val context = new Context {
      val name = "global"
      override val typeHintStrategy = StringTypeHintStrategy(
        when = TypeHintFrequency.WhenNecessary, typeHint = "_t")
    }
    context.registerGlobalKeyOverride(remapThis = "id", toThisInstead = "_id")
    context.registerClassLoader(Play.classloader)
    context
  }

  implicit object ObjectIdReads extends Reads[ObjectId] {
    def reads(json: JsValue) = json match {
      case JsString(s) =>
        if (ObjectId.isValid(s))
          JsSuccess(new ObjectId(s))
        else
          JsError(ValidationError("validate.error.invalid.objectid"))
      case _ => JsError(Seq(JsPath() -> Seq(ValidationError("validate.error.expected.jsstring"))))
    }
  }
}