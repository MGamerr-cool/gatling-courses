package ru.tinkoff.load.jdbc.test.cases

import io.gatling.core.Predef.{find2Final, stringToExpression, value2Expression}
import ru.tinkoff.load.jdbc.Predef._
import ru.tinkoff.load.jdbc.actions.actions._

import java.time.LocalDateTime
import java.util.UUID

object ActionsHomework {

  val select: QueryActionBuilder =
    jdbc("select").query("SELECT * FROM departments").check(simpleCheck(_.nonEmpty), allResults.saveAs("departments"))

  val selectNameDepartments: QueryActionBuilder =
    jdbc("selectNameDepartments").query("SELECT \"name\" FROM departments").check(simpleCheck(_.nonEmpty), allResults.saveAs("departmentsName"))

}
