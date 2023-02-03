package perf

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Actions {

  //--------------------open------------------
  val webtours = http("/webtours/")
    .get("/webtours/")
    .check(status is 200)

  val welcomePl = http("/cgi-bin/welcome.pl?signOff=true")
    .get("/cgi-bin/welcome.pl")
    .queryParam("signOff","true")
    .check(status is 200)

  val navPl = http("/cgi-bin/nav.pl?in=home")
    .get("/cgi-bin/nav.pl")
    .queryParam("in","home")
    .check(status is 200)
    .check(regex("""name="userSession" value="(.+)"""").saveAs("userSession"))

  //--------------------login------------------
  val loginPl = http("/cgi-bin/login.pl")
    .post("/cgi-bin/login.pl")
    .formParam("userSession","${userSession}")
    .formParam("username","${login}")
    .formParam("password","${password}")
    .formParam("login.x","77")
    .formParam("login.y","14")
    .formParam("JSFormSubmit","off")
    .check(status is 200)
}
