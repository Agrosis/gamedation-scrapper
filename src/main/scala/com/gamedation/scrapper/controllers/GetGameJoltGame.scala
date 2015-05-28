package com.gamedation.scrapper.controllers

import com.gamedation.scrapper.Scraper
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.edge.http.Ok
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.{JsValue, JsLong, JsNull, JsObject}

object GetGameJoltGame extends Controller {

  private def gameUrl(id: Int): String = s"http://gamejolt.com/games/category/name/$id/"

  override def action(implicit request: HttpRequest): Box[Throwable, HttpResponse] = {
    val result: Option[JsValue] = for (
      id <- request.pathVars.get("id").map(_.toInt);
      scraper <- Scraper(gameUrl(id));
      title <- scraper.text("div.game-title > h2");
      rating <- scraper.text("div.game-rating-overall")
    ) yield {
      JsObject(
        "name" -> title,
        "rating" -> rating,
        "windows" -> scraper.exists("i.jolticon-windows"),
        "mac" -> scraper.exists("i.jolticon-mac"),
        "linux" -> scraper.exists("i.jolticon-linux"),
        "browser" -> scraper.exists("div.online-game-container")
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
