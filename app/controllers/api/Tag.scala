package controllers.api

import play.api.mvc._
import play.api.libs.json._
import models._
import models.Tag.tagWrites
import models.Tag.tagReads

object Tag extends Controller {
  def all = Action{ implicit request =>
    val tags = models.Tag.all
    Ok(Json.toJson(tags))
  }

  def create() = Action(parse.json) { request =>
    val tag = request.body.as[models.Tag]
    try {
      models.Tag.create(tag)
      Ok(Json.toJson(true))
    }
    catch {
      case e:IllegalArgumentException => BadRequest("Unable to create Tag with data: " + request.body)
    }
  }

  def details(value: String) = Action { request =>
    models.Tag.findByValue(value).map { tag: models.Tag =>
      Ok(Json.toJson(tag))
    }.getOrElse(NotFound)
  }

  def update(id: String) = TODO

  def delete(value: String) = Action { request =>
    models.Tag.findByValue(value).map { tag: models.Tag =>
      models.Tag.delete(tag)
      Ok(Json.toJson(""))
    }.getOrElse(NotFound)
  }
}
