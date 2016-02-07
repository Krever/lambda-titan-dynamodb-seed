name := "titan-dynamodb-lambda-seed"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  "com.michaelpollmeier" %% "gremlin-scala" % "3.0.2-incubating.1",
  "com.thinkaurelius.titan" % "titan-core" % "1.0.0",
  "com.amazonaws" % "dynamodb-titan100-storage-backend" % "1.0.0"
)