package perf

import io.gatling.http.Predef._
import io.gatling.core.Predef._


class TestSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("https://test.k6.io")
    .acceptHeader("test/html,application/xhtml+xml,appplication/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .disableFollowRedirect

  setUp(
    CommonScenario().inject(
      rampUsersPerSec(0) to 3 during (10),
      constantUsersPerSec(3) during (60)
    )
  ).protocols(httpProtocol)
}
