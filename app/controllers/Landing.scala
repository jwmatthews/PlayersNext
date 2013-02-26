package controllers

import play.api.mvc._

object Landing extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.landing())
  }

}

