package com.gamedation.scrapper.controllers

import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.edge.http.Ok
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.JsObject

object Index extends Controller {

  override def action(implicit request: HttpRequest): Box[Throwable, HttpResponse] = {
    Ok(JsObject(
      "name" -> "scrapper",
      "version" -> "1.0.0"
    ))
  }

}
