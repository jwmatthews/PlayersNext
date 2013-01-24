package controllers.api

import play.api.mvc._
import models._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.Logger

object Link extends Controller {

  implicit object linkWrites extends Writes[models.Link] {
    def writes(l: models.Link) = Json.toJson(
      Map(
        "url" -> Json.toJson(l.url),
        "description" -> Json.toJson(l.description)
      )
    )
  }
  implicit val linkReads: Reads[models.Link] = (
    (__ \ "url").read[String] and
      (__ \ "description").read[String]
    )(models.Link.apply _)

  def index = Action{ implicit request =>
    Logger.info("index() invoked with request.body = \n%s\n".format(request.body))
    val links = Links.all
    Ok(Json.toJson(links))
  }

  def create() = Action(parse.json) { request =>
    Logger.info("create() invoked with request.body = \n%s\n".format(request.body))
    // TODO: Feels like we should be doing something with a json error on parsing bad link data here
    val link = request.body.as[models.Link]

    try {
      Links.create(link)
      Ok("Saved")
    }
    catch {
      case e:IllegalArgumentException => BadRequest("Unable to create Link with data: " + request.body)
    }
  }

  def details(id: String) = Action { request =>
    Logger.info("details() invoked with request.body = \n%s\n".format(request.body))
    Links.findById(id).map { link: models.Link =>
      Ok(Json.toJson(link))
    }.getOrElse(NotFound)
  }

  def update(id: String) = TODO

  def delete(id: String) = TODO

}
