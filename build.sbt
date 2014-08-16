name := "ironhtml"

version := "0.4.0"

scalaVersion := "2.10.2"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.1.0"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:implicitConversions", "-language:higherKinds", "-language:existentials", "-language:postfixOps")

