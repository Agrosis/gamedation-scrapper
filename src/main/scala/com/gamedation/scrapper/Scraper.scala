package com.gamedation.scrapper

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

final case class Scraper(doc: Document) {

  def text(selectors: String): Option[String] = {
    Option(doc.select(selectors).text())
  }

  def exists(selectors: String): Boolean = {
    doc.select(selectors).size() > 0
  }

}

object Scraper {

  def apply(url: String, timeout: Int = 5000): Option[Scraper] = {
    Option(Jsoup.connect(url).timeout(timeout).get()).map(doc => Scraper(doc))
  }

}
