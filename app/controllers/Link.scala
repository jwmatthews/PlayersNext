package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._


object Link extends Controller {
  def linkForm = Form(
    mapping(
      "url" -> nonEmptyText,
      "description" -> nonEmptyText
    )(models.Link.apply)(models.Link.unapply)
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
