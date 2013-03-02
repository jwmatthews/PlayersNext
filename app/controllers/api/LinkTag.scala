package controllers.api

import play.api.mvc._
import play.api.libs.json._
import models._
import models.Links.linkWrites
import models.Links.linkReads
import models.Links.commentReads
import models.Links.commentWrites


object LinkTag extends Controller {

  // 'all' method may not be needed since we already have this data from a
  // a fetch of the Link
  def all(id: String) = Action{ implicit request =>
    Links.findById(id).map {
      link: models.Link =>
        Ok(Json.toJson(link.tags))
    }.getOrElse(NotFound)
  }

  def add(id: String, tag: String) = Action(parse.json) { request =>
    Links.addTag(id, tag)
    Ok(Json.toJson(true))
  }

  def delete(id: String, tag: String) = Action { request =>
    Links.deleteTag(id, tag)
    Ok(Json.toJson(true))
  }
}
