package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._

import models.Link

class ApplicationSpec extends Specification {
  
  "Application" should {
    
    /*"send 404 on a bad request" in {
      running(FakeApplication()) {
        route(FakeRequest(GET, "/boum")) must beNone        
      }
    }
    */
    
    /*"render the index page" in {
      running(FakeApplication()) {
        val home = route(FakeRequest(GET, "/")).get
        
        status(home) must equalTo(OK)
        contentType(home) must beSome.which(_ == "text/html")
        contentAsString(home) must contain ("PlayersNext! Info")
      }
    }*/

    "convert a Link case class to a DBObject" in {
      val link = Link(url="http://example.com", description=Some("A description of a link"), title=None, thumbnail=null, tags=null)
      val dbo = grater[Link].asDBObject(link)
      dbo must not be None
    }
  }
}
