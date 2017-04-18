name := "titan-dynamodb-lambda-seed"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += Resolver.mavenLocal

resolvers += "DynamoDBLocal" at "https://s3-us-west-2.amazonaws.com/dynamodb-local/release"


libraryDependencies ++= Seq(
  "com.michaelpollmeier"      %% "gremlin-scala"                    % "3.0.2-incubating.1"  exclude("org.slf4j", "jcl-over-slf4j"),
  "com.thinkaurelius.titan"   % "titan-core"                        % "1.0.0",
  "com.amazonaws"             % "dynamodb-titan100-storage-backend" % "1.0.3",
  "com.amazonaws"             % "aws-lambda-java-core"              % "1.1.0"
)

assemblyMergeStrategy in assembly := {
  case PathList(ps @ _*) if ps.last == "groovy-release-info.properties" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last == "log4j.properties" => MergeStrategy.concat
  case PathList(ps @ _*) if ps.last == ".gitkeep" => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}