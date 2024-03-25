package ru.tinkoff.load.jdbc.test

import io.gatling.core.Predef._
import ru.tinkoff.load.jdbc.Predef._
import ru.tinkoff.load.jdbc.protocol.JdbcProtocolBuilder
import ru.tinkoff.load.jdbc.test.scenarios.HomeworkScenarios

import scala.concurrent.duration.DurationInt

class HomeworkTest extends Simulation{

  val jdbcProtocol: JdbcProtocolBuilder = DB
    .url("jdbc:postgresql://localhost:5432/hospital")
    .username("postgres")
    .password("12345")
    .maximumPoolSize(23)
    .connectionTimeout(1.minute)

  setUp(
    HomeworkScenarios().inject(
      constantConcurrentUsers(1).during(10)
    ),
  ).protocols(jdbcProtocol)

}
