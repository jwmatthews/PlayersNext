import play.api._
import play.api.mvc._
import play.api.mvc.Results._

import models._

object Global extends GlobalSettings {
  override def onStart(app: Application) {
    Logger.info("Application has started")
    Links.ensureIndexes()
    Tags.ensureIndexes()
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }

  override def onError(request: RequestHeader, ex: Throwable) = {
    Logger.error("ISE: from " + request.path + " with error:  " + ex)
    super.onError(request, ex)
  }

  override def onHandlerNotFound(request: RequestHeader): Result = {
    Logger.error("Handler Not Found: " + request.path)
    super.onHandlerNotFound(request)
  }

  override def onBadRequest(request: RequestHeader, error: String) = {
    Logger.error("Bad Request: from \"" + request.path  + "\" resulted in error: " + error)
    super.onBadRequest(request, error)
  }

}