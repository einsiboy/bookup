name := """bookup"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

herokuAppName in Compile := "mysterious-reaches-8898"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  javaCore,
  javaWs,
  cache,
      "org.hibernate" % "hibernate-entitymanager" % "4.3.1.Final",
      "org.json"%"org.json"%"chargebee-1.0",
	"junit" % "junit" % "4.11" % Test,
  	"com.novocode" % "junit-interface" % "0.11" % Test
        exclude("junit", "junit-dep"),
      "org.xerial" % "sqlite-jdbc" % "3.8.0-SNAPSHOT"
)

resolvers += "SQLite-JDBC Repository" at "https://oss.sonatype.org/content/repositories/snapshots" 
