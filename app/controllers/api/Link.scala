package controllers.api


import play.api.mvc._
import play.api.libs.json._
import play.Logger

//TODO:  Revisit if we should create a new execution context for these lookups
import play.api.libs.concurrent.Execution.Implicits._

import helper.Embedly
import models._
import models.Links.linkWrites
import models.Links.linkReads

import models.Links.thumbnailWrites
import models.Links.thumbnailReads


object Link extends Controller {

  def index(tags: Option[String]) = Action{ implicit request =>
    Logger.info("index() invoked with request.body = \n%s\n".format(request.body))

    val links = Links.all
    val data = Map("links" -> links)
    
    tags match {
      case None => Ok(Json.toJson(data))
      case Some(x) => BadRequest("NOT IMPLEMENTED")
    }
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
        val msg = "Unable to create Link with data: " + request.body + ", yielded errors" + errors
        Logger.error(msg)
        BadRequest(msg)
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

  def metadata(url: String) = Action {
    Async {
      Logger.info("Embedly lookup for: '" + url + "'")
      val responsePromise = Embedly.lookup(url)
      responsePromise.map { response =>
        if (response.status == 200) {
          Logger.info("Received response from lookup on: " + url + "\nBody: " + response.body)
          Embedly.extractReads.reads(Json.parse(response.body)).fold(
            valid = { metadata =>
              Ok(Json.toJson(metadata))
            },
            invalid = { errors =>
              val msg = "Unable to decode JSON data for embedly extract query on: '" + url +
                "' received errors: " + errors
              Logger.error(msg)
              Logger.error("Response was: \n" + response.body)
              BadRequest(msg)
            }
          )
        }
        else {
          val msg = "Bad answer from embed.ly:" + response.statusText
          Logger.error(msg)
          BadRequest(msg)
        }
      }
    }
  }
}
