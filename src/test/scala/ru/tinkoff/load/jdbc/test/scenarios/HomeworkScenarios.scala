package ru.tinkoff.load.jdbc.test.scenarios

import io.gatling.core.Predef.scenario
import io.gatling.core.structure.ScenarioBuilder
import ru.tinkoff.load.jdbc.test.cases.ActionsHomework

object HomeworkScenarios {
  def apply(): ScenarioBuilder = new HomeworkScenarios().scn

}
class HomeworkScenarios {
  val scn: ScenarioBuilder = scenario("Homework")
    .exec(ActionsHomework.select)
    .exec(ActionsHomework.selectNameDepartments)
}
