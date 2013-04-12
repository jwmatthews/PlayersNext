package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
class IntegrationSpec extends Specification {
  
  "Application" should {
    
    "work from within a browser" in {
    /*  
     *	UNSURE why, but "running(TestServer(3333, HTMLUNIT)" causes unit tests to hang
     *  Leaving commented out until we know cause
     *
    	running(TestServer(3333), HTMLUNIT) { browser =>

        browser.goTo("http://localhost:3333/")

        browser.pageSource must contain("PlayersNext! Info")
       
      }
      */
    }
    
  }
  
}