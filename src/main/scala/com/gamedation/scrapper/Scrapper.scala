package com.gamedation.scrapper

import com.gamedation.scrapper.controllers.{GetSteamGame, GetGameJoltGame, Index}
import com.plasmaconduit.framework.PlasmaConduit
import com.plasmaconduit.framework.routes.containers.{HttpGetMethodRoutes, HttpRoutes}
import com.plasmaconduit.framework.routes.destinations.HttpPathRoute

import scala.util.matching.Regex

object Scrapper {

  val routes = HttpRoutes(
    HttpGetMethodRoutes(
      HttpPathRoute("/", Index),
      HttpPathRoute(new Regex(s"/gamejolt/([0-9]+)", "id"), GetGameJoltGame),
      HttpPathRoute(new Regex(s"/steam/([0-9]+)", "id"), GetSteamGame)
    )
  )

  def main(args: Array[String]) = {
    val server = PlasmaConduit(
      port         = 1340,
      middleware   = List(),
      defaultRoute = Index,
      routes       = routes
    )
    server.run()
  }

}
