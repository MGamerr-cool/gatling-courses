package perf

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Actions {

  val contacts = http("/contacts.php")
    .get("/contacts.php")
    .check(status is 200)

  val news = http("/news.php")
    .get("/news.php")
    .check(status is 200)

}
