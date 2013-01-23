package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._

import models.Registration

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends Specification {
  
  "Application" should {
    
    "send 404 on a bad request" in {
      running(FakeApplication()) {
        route(FakeRequest(GET, "/boum")) must beNone        
      }
    }
    
    "render the index page" in {
      running(FakeApplication()) {
        val home = route(FakeRequest(GET, "/")).get
        
        status(home) must equalTo(OK)
        contentType(home) must beSome.which(_ == "text/html")
        contentAsString(home) must contain ("Super Simple Sample")
      }
    }

    "convert a Registration case class to a DBObject" in {
      val registration = Registration("usera", "pw", "pw", "Real Name")
      val dbo = grater[Registration].asDBObject(registration)
      dbo must not be None

    }
  }
}