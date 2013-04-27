import sbt._
import Keys._
import play.Project._

import com.ketalo.EmberJsKeys


object ApplicationBuild extends Build with EmberJsKeys {

  val appName         = "PlayersNext"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "org.mongodb" %% "casbah-core" % "2.5.0",
    "com.novus" %% "salat-core" % "1.9.2-SNAPSHOT"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    emberJsVersion := "1.0.0-rc.3",
    resolvers ++= Seq(
      "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases",
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
    )
  )

}

