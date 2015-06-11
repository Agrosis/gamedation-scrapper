package com.gamedation.scrapper.controllers

import com.gamedation.scrapper.Scraper
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.edge.http.Ok
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.{JsValue, JsLong, JsNull, JsObject}

object GetSteamGame extends Controller {

  private def gameUrl(id: Int): String = s"http://store.steampowered.com/app/$id/"

  override def action(implicit request: HttpRequest): Box[Throwable, HttpResponse] = {
    val result: Option[JsValue] = for (
      id <- request.pathVars.get("id").map(_.toInt);
      scraper <- Scraper(gameUrl(id));
      title <- scraper.text("div.apphub_AppName")
    ) yield {
      JsObject(
        "name" -> title,
        "windows" -> scraper.exists("span.platform_img.win"),
        "mac" -> scraper.exists("span.platform_img.mac"),
        "linux" -> scraper.exists("span.platform_img.linux"),
        "screenshots" -> scraper.getListAttributes("div.highlight_screenshot.highlight_player_item a", "href")
      )
    }

    result match {
      case Some(data) => {
        Ok(JsObject(
          "status" -> JsLong(200),
          "error" -> JsNull,
          "data" -> data
        ))
      }
      case None => {
        Ok(JsObject(
          "status" -> 404,
          "error" -> "Invalid gamejolt game."
        ))
      }
    }
  }

}
