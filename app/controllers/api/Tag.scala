package controllers.api

import play.api.mvc._
import play.api.libs.json._
import models._
import models.Tags.tagWrites
import models.Tags.tagReads

object Tag extends Controller {
  def all = Action{ implicit request =>
    val tags = Tags.all
    val result = Map("tags" -> tags)
    Ok(Json.toJson(result))
  }

  def create(value: String) = Action { request =>
    try {
      val tag = models.Tag(tag=value)
      Tags.create(tag)
      Ok(Json.toJson(true))
    }
    catch {
      case e:IllegalArgumentException => BadRequest("Unable to create Tag with data: " + request.body)
    }
  }

  def details(value: String) = Action { request =>
    Tags.findByValue(value).map { tag: Tag =>
      Ok(Json.toJson(tag))
    }.getOrElse(NotFound)
  }

  def update(id: String) = TODO

  def delete(value: String) = Action { request =>
    Tags.findByValue(value).map { tag: Tag =>
      Tags.delete(tag)
      Ok(Json.toJson(""))
    }.getOrElse(NotFound)
  }
}
