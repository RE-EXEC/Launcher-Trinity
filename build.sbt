val Version: String = "1.0.0-openbeta1"

ThisBuild / version := Version

ThisBuild / scalaVersion := "2.13.12"
resolvers += "Sonatype OSS Snapshots" at "https://s01.oss.sonatype.org/service/local/repositories/releases/content/"

libraryDependencies += "io.github.k3nder" % "mclc" % "0.4.4"
// https://mvnrepository.com/artifact/io.github.k3nder/log5k
libraryDependencies += "io.github.k3nder" % "log5k" % "2.0.0"
// https://mvnrepository.com/artifact/io.github.k3nder/kjson
libraryDependencies += "io.github.k3nder" % "kjson" % "0.2.6"
// https://mvnrepository.com/artifact/commons-io/commons-io
libraryDependencies += "commons-io" % "commons-io" % "2.15.1"


scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8", "-Ymacro-annotations")

Compile / resourceDirectory := (Compile / scalaSource).value
libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "21.0.0-R32",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.5"
)

resolvers ++= Opts.resolver.sonatypeOssSnapshots

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
scalacOptions += "-Ytasty-reader"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "versions", "9", xs @ _*) => MergeStrategy.discard
  case PathList("META-INF", "substrate", "config", xs @ _*) => MergeStrategy.discard
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

lazy val root = (project in file("."))
  .settings(
    name := "laucher.trinity",
    idePackagePrefix := Some("net.k3nder.lt"),
    assemblyOutputPath in assembly := file("artifacts/launcher-trinity-universal-" + Version + ".jar")
  )
