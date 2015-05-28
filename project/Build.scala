import sbt._
import sbt.Keys._

import spray.revolver.RevolverPlugin._

object Build extends Build {

  lazy val root = (project in file("."))
    .settings(Revolver.settings: _*)
    .settings(
      name                  := "scrapper",
      organization          := "com.gamedation",
      version               := "0.1.0",
      scalaVersion          := "2.11.4",
      licenses              += ("MIT", url("http://opensource.org/licenses/MIT")),
      scalacOptions         += "-feature",
      scalacOptions         += "-deprecation",
      scalacOptions         += "-unchecked",
      scalacOptions         += "-language:implicitConversions",
      resolvers             ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo),
      resolvers             += "Plasma Conduit Repository" at "http://dl.bintray.com/plasmaconduit/releases",
      libraryDependencies   += "com.plasmaconduit" %% "plasmaconduit-framework" % "0.50.0",
      libraryDependencies   += "org.jsoup"          % "jsoup"                   % "1.7.2"
    )

}
