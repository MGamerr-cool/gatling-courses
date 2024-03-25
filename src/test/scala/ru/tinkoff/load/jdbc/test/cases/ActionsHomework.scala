package ru.tinkoff.load.jdbc.test.cases

import io.gatling.core.Predef.{find2Final, stringToExpression, value2Expression}
import ru.tinkoff.load.jdbc.Predef._
import ru.tinkoff.load.jdbc.actions.actions._

import java.time.LocalDateTime
import java.util.UUID

object ActionsHomework {

  def createTable(): RawSqlActionBuilder =
    jdbc("Create Table")
      .rawSql(
        "CREATE TABLE Departments (" +
          "    id SERIAL PRIMARY KEY," +
          "    name VARCHAR(255) NOT NULL," +
          "    description TEXT" +
          ");" +
          "CREATE TABLE Doctors (" +
          "    id SERIAL PRIMARY KEY," +
          "    first_name VARCHAR(255) NOT NULL," +
          "    last_name VARCHAR(255) NOT NULL," +
          "    specialization VARCHAR(255) NOT NULL," +
          "    department_id INT" +
          ");" +
          "CREATE TABLE Patients (" +
          "    id SERIAL PRIMARY KEY," +
          "    first_name VARCHAR(255) NOT NULL," +
          "    last_name VARCHAR(255) NOT NULL," +
          "    age INT," +
          "    gender VARCHAR(10)," +
          "    diagnosis TEXT," +
          "    doctor_id INT" +
          ");" +
          "INSERT INTO Departments (name, description)" +
          "VALUES" +
          "    ('Cardiology', 'Department specializing in heart diseases')," +
          "    ('Orthopedics', 'Department specializing in musculoskeletal conditions')," +
          "    ('Pediatrics', 'Department for child healthcare');" +
          "INSERT INTO Doctors (first_name, last_name, specialization, department_id)" +
          "VALUES" +
          "    ('Dr. Smith', 'Johnson', 'Cardiologist', 1)," +
          "    ('Dr. Emily', 'Williams', 'Orthopedic Surgeon', 2)," +
          "    ('Dr. Mia', 'Davis', 'Pediatrician', 3)," +
          "    ('Dr. James', 'Brown', 'Cardiologist', 1)," +
          "    ('Dr. Olivia', 'Wilson', 'Orthopedic Surgeon', 2);" +
          "INSERT INTO Patients (first_name, last_name, age, gender, diagnosis, doctor_id)" +
          "VALUES" +
          "    ('John', 'Smith', 45, 'Male', 'Hypertension', 1)," +
          "    ('Emma', 'Johnson', 30, 'Female', 'Fracture', 2)," +
          "    ('Sophia', 'Anderson', 12, 'Female', 'Flu', 3)," +
          "    ('Liam', 'Harris', 35, 'Male', 'Asthma', 1)," +
          "    ('Oliver', 'Clark', 28, 'Male', 'Appendicitis', 2)," +
          "    ('Ava', 'White', 8, 'Female', 'Common Cold', 3)," +
          "    ('Sophia', 'Martin', 28, 'Female', 'Asthma', 1)," +
          "    ('Ethan', 'Garcia', 25, 'Male', 'Bronchitis', 2)," +
          "    ('Olivia', 'Jones', 10, 'Female', 'Strep Throat', 3)," +
          "    ('Mia', 'Taylor', 35, 'Female', 'Diabetes', 1)," +
          "    ('Noah', 'Miller', 32, 'Male', 'Migraine', 2)," +
          "    ('Liam', 'Thomas', 5, 'Male', 'Fever', 3)," +
          "    ('Isabella', 'Walker', 60, 'Female', 'Arthritis', 1)," +
          "    ('Lucas', 'Young', 42, 'Male', 'Pneumonia', 2)," +
          "    ('Emma', 'Hall', 15, 'Female', 'Asthma', 3);"
      )

  val selectPatients: QueryActionBuilder =
    jdbc("selectPatients").query("SELECT * FROM Patients").check(simpleCheck(_.nonEmpty))

  val selectPatientsAsthma: QueryActionBuilder =
    jdbc("selectPatientsAsthma").query("SELECT * FROM Patients WHERE diagnosis = 'Asthma'").check(simpleCheck(_.nonEmpty))

  val insertPatientsSophie: DBInsertActionBuilder =
    jdbc("insertPatientsSophie")
      .insertInto("Patients", Columns("first_name", "last_name", "age", "gender", "diagnosis", "doctor_id"))
      .values("first_name" -> "Sophie", "last_name" -> "Smith", "age" -> 40, "gender" -> "Female", "diagnosis" -> "Hypertension", "doctor_id" -> 1)

  val insertPatientsJackEmma: BatchActionBuilder = jdbc("insertPatientsJackEmma").batch(
    insertInto("Patients", Columns("first_name", "last_name", "age", "gender", "diagnosis", "doctor_id"))
      .values("first_name" -> "Jack", "last_name" -> "Johnson", "age" -> 55, "gender" -> "Male", "diagnosis" -> "Diabetes", "doctor_id" -> 2),
    insertInto("Patients", Columns("first_name", "last_name", "age", "gender", "diagnosis", "doctor_id"))
      .values("first_name" -> "Emma", "last_name" -> "Williams", "age" -> 25, "gender" -> "Female", "diagnosis" -> "Asthma", "doctor_id" -> 3),
  )

  val batchPatientsAge: BatchActionBuilder = jdbc("batchPatientsAge").batch(
    update("Patients").set("age" -> 25).where("ID = 2")
  )

  val batchPatientsDiagnosis: BatchActionBuilder = jdbc("batchPatientsDiagnosis").batch(
    update("Patients").set("diagnosis" -> "Appendicitis").where("last_name = 'White'")
  )

  val getNewPatients: QueryActionBuilder =
    jdbc("getNewPatients")
      .query("SELECT id " +
      "FROM Patients " +
      "WHERE (first_name, last_name, age, gender, diagnosis, doctor_id) " +
        "IN (('Sophie', 'Smith', 40, 'Female', 'Hypertension', 1)," +
        "    ('Jack', 'Johnson', 55, 'Male', 'Diabetes', 2)," +
        "    ('Emma', 'Williams', 25, 'Female', 'Asthma', 3))").check(simpleCheck(_.nonEmpty), allResults.saveAs("patients"))

  val deleteNewPatient: RawSqlActionBuilder =
    jdbc("deleteNewPatient")
      .rawSql("DELETE FROM Patients WHERE id = 25")

  val deleteNewPatients: RawSqlActionBuilder =
    jdbc("deleteNewPatients")
      .rawSql("DELETE FROM Patients WHERE id IN (27,28,29,30)")

  //TODO сделать удалени сразу нескольких строк p.s. Почему не работает saveAs?
  val deleteNewPatients2: RawSqlActionBuilder =
    jdbc("deleteNewPatients")
      .rawSql("DELETE FROM Patients WHERE id IN (patients)")

  val deleteNewPatients3: QueryActionBuilder =
    jdbc("deleteNewPatients")
      .queryP("DELETE FROM Patients WHERE id IN ({patients})")
      .params("patients" -> "patients")

}
