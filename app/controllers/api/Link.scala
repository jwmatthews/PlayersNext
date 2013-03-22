package controllers.api

//import com.mongodb.casbah.Imports._
//import org.bson.types.ObjectId
import play.api.mvc._
//import play.api.data.validation.ValidationError
import play.api.libs.json._
//import play.api.libs.functional.syntax._
import play.Logger
import models._
import models.Links.linkWrites
import models.Links.linkReads



object Link extends Controller {

  def index = Action{ implicit request =>
    Logger.info("index() invoked with request.body = \n%s\n".format(request.body))
    val links = Links.all
    val data = Map("links" -> links)
    Ok(Json.toJson(data))
  }

  def create() = Action(parse.json) { request =>
    Logger.info("create() invoked with request.body = \n%s\n".format(request.body))
    val customReads: Reads[models.Link] =
      (JsPath \ "link").read[models.Link]
    customReads.reads(request.body).fold(
      valid = { link =>
        Links.create(link)
        Ok(Json.toJson(true))
      },
      invalid = { errors =>
        BadRequest("Unable to create Link with data: " + request.body +
          ", yielded errors" + errors)

      }
    )
  }

  def details(id: String) = Action { request =>
    Logger.info("details() invoked with request.body = \n%s\n".format(request.body))
    Links.findById(id).map { link: models.Link =>
      Ok(Json.toJson(Map("link" -> link)))
    }.getOrElse(NotFound)
  }

  def update(id: String) = TODO

  def delete(id: String) = Action { request =>
    Logger.info("delete() invoked with request.body = \n%s\n".format(request.body))
    Links.findById(id).map { link: models.Link =>
      Links.delete(link)
      Ok(Json.toJson(""))
    }.getOrElse(NotFound)
  }

}
