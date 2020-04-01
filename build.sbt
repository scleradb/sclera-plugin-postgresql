name := "sclera-plugin-postgresql"

description := "Add-on that enables Sclera to work with your data stored in PostgreSQL"

homepage := Some(url(s"https://github.com/scleradb/${name.value}"))

scmInfo := Some(
    ScmInfo(
        url(s"https://github.com/scleradb/${name.value}"),
        s"scm:git@github.com:scleradb/${name.value}.git"
    )
)

version := "4.0-SNAPSHOT"

startYear := Some(2012)

scalaVersion := "2.13.1"

licenses := Seq("Apache License version 2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.txt"))

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
    "org.postgresql" % "postgresql" % "42.2.8",
    "com.scleradb" %% "sclera-core" % "4.0-SNAPSHOT" % "provided",
    "com.scleradb" %% "sclera-config" % "4.0-SNAPSHOT" % "test",
    "com.scleradb" %% "sclera-jdbc" % "4.0-SNAPSHOT" % "test",
    "com.scleradb" %% "sclera-plugin-csv" % "4.0-SNAPSHOT" % "test",
    "com.scleradb" %% "sclera-sqltests-runner" % "4.0-SNAPSHOT" % "test",
    "org.scalatest" %% "scalatest" % "3.1.1" % "test"
)

scalacOptions ++= Seq(
    "-Werror", "-feature", "-deprecation", "-unchecked"
)

exportJars := true

javaOptions in Test ++= Seq(
    s"-DSCLERA_ROOT=${java.nio.file.Files.createTempDirectory("scleratest")}"
)

fork in Test := true
