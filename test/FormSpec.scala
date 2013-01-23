package test

import org.specs2.mutable._

class FormSpec extends Specification {

  import controllers.Register.registrationForm
  import models.Registration

  "Register form" should {

    "require all fields" in {
      val form = registrationForm.bind(Map.empty[String,String])

      form.hasErrors must beTrue
      form.errors.size must equalTo(4)

      form("username").hasErrors must beTrue
      form("password").hasErrors must beTrue
      form("confirm").hasErrors must beTrue
      form("realName").hasErrors must beTrue
    }
  }

  "submit a registration" in {
    val form = registrationForm.bind(
      Map("username" -> "usera",
        "password" -> "pw1",
        "confirm" -> "pw1",
        "realName" -> "Fred"))

    form.hasErrors must beFalse
    form.errors.size must equalTo(0)

    form("username").hasErrors must beFalse
    form("realName").hasErrors must beFalse
    form("password").hasErrors must beFalse
    form("confirm").hasErrors must beFalse


    form.data must havePair("username" -> "usera")
    form.data must havePair("password" -> "pw1")
    form.data must havePair("confirm" -> "pw1")
    form.data must havePair("realName" -> "Fred")

    form("username").value must beSome.which(_ == "usera")
    form("password").value must beSome.which(_ == "pw1")
    form("confirm").value must beSome.which(_ == "pw1")
    form("realName").value must beSome.which(_ == "Fred")

    form.value must beSome.which( _ == Registration("usera","pw1","pw1","Fred"))
  }
}

