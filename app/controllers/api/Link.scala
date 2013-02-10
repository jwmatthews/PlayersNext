package controllers.api

import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId
import play.api.mvc._
import play.api.data.validation.ValidationError
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.Logger
import models._


object Link extends Controller {

  implicit object linkWrites extends Writes[models.Link] {
    def writes(l: models.Link) = Json.toJson(
      Map(
        "url" -> Json.toJson(l.url),
        "description" -> Json.toJson(l.description),
        "id" -> Json.toJson(l.id.toString)
      )
    )
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

  implicit val linkReads: Reads[models.Link] = (
    (__ \ "url").read[String] and
      (__ \ "description").read[String] and
      (__ \ "id").read[ObjectId]
    )(models.Link.apply _)

  def index = Action{ implicit request =>
    Logger.info("index() invoked with request.body = \n%s\n".format(request.body))
    val links = Links.all
    Ok(Json.toJson(links)).withHeaders(
        "Access-Control-Allow-Origin" -> "*" 
      )
  }

  def create() = Action(parse.json) { request =>
    Logger.info("create() invoked with request.body = \n%s\n".format(request.body))
    // TODO: Feels like we should be doing something with a json error on parsing bad link data here
    val link = request.body.as[models.Link]

    try {
      Links.create(link)
      Ok("Saved").withHeaders(
        "Access-Control-Allow-Origin" -> "*" 
      )
    }
    catch {
      case e:IllegalArgumentException => BadRequest("Unable to create Link with data: " + request.body)
    }
  }

  def details(id: String) = Action { request =>
    Logger.info("details() invoked with request.body = \n%s\n".format(request.body))
    Links.findById(id).map { link: models.Link =>
      Ok(Json.toJson(link)).withHeaders(
        "Access-Control-Allow-Origin" -> "*" 
      )
    }.getOrElse(NotFound)
  }

  def update(id: String) = TODO

  def delete(id: String) = Action { request =>
    Logger.info("delete() invoked with request.body = \n%s\n".format(request.body))
    Links.findById(id).map { link: models.Link =>
      Links.delete(link)
      Ok(Json.toJson("")).withHeaders(
        "Access-Control-Allow-Origin" -> "*" 
      )
    }.getOrElse(NotFound)
  }

}
