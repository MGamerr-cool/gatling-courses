package perf

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import perf.Actions._

object CommonScenario{
  def apply(): ScenarioBuilder = new CommonScenario().mainScenario
}

class CommonScenario {

  val open = group("open"){
    exec(webtours)
      .exec(welcomePl)
      .exec(navPl)
  }

  val login = group("login"){
    exec(loginPl)
  }

  val mainScenario = scenario("mainScenario")
    .feed(Feeders.users)
    .exec(open)
    .exec(login)

}
