package test

import org.specs2.mutable._

class FormSpec extends Specification {

  import controllers.Link.linkForm
  import models.Link

  "Link form" should {

    "require all fields" in {
      val form = linkForm.bind(Map.empty[String,String])

      form.hasErrors must beTrue
      form.errors.size must equalTo(2)

      form("url").hasErrors must beTrue
      form("description").hasErrors must beTrue
    }
  }

  "submit a link" in {
    val form = linkForm.bind(
      Map("url" -> "http://example.com",
        "description" -> "A description about the link"))

    form.hasErrors must beFalse
    form.errors.size must equalTo(0)

    form("url").hasErrors must beFalse
    form("description").hasErrors must beFalse

    form.data must havePair("url" -> "http://example.com")
    form.data must havePair("description" -> "A description about the link")

    form("url").value must beSome.which(_ == "http://example.com")
    form("description").value must beSome.which(_ == "A description about the link")

    form.value must beSome.which( _ == Link("http://example.com","A description about the link"))
  }
}

