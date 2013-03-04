package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._


object Link extends Controller {

  def tagsToForm(tags: List[String]): String = {
    tags.foldLeft("")( (a, b) => a + b + ", ")
  }

  def tagsFromForm(tags: String): List[String] = {
    tags.split(",").toList.map(_.trim).filter(!_.isEmpty)
  }

  def linkForm = Form(
    mapping(
      "url" -> nonEmptyText,
      "title" -> nonEmptyText,
      "description" -> nonEmptyText,
      "tags" -> text
    )
      ((url, title, description, tags) =>
        models.Link(url=url, title=title, description=description, tags=tagsFromForm(tags)))
      ((l:models.Link) => Some(l.url, l.title, l.description, tagsToForm(l.tags)))
  )

  def index = Action{ implicit request =>
    Ok(views.html.link(linkForm))
  }

  def create = Action{ implicit request =>
    linkForm.bindFromRequest.fold(
      form => BadRequest(views.html.link(form)),
      link => {
        Links.create(link)
        Redirect(routes.Application.index()).flashing("message" -> "Link Added!")
      }
    )
  }

  def update = TODO

}
