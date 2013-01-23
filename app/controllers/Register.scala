package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._


object Register extends Controller {
  def registrationForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "confirm" -> nonEmptyText,
      "realName" -> text
    )(Registration.apply)(Registration.unapply)
    verifying("Passwords must match", fields => fields match {
        case Registration(_, password, confirmation, _) => password.equals(confirmation)
      })
  )

  def index = Action{ implicit request =>
    Ok(views.html.register(registrationForm))
  }

  def register = Action{ implicit request =>
    registrationForm.bindFromRequest.fold(
      form => BadRequest(views.html.register(form)),
      registration => {
        Registrations.create(registration)
        Redirect(routes.Application.index()).flashing("message" -> "User Registered!")
      }
    )
  }
}
