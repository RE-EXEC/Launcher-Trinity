ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies += "io.github.k3nder" % "mclc" % "0.3.0"
// https://mvnrepository.com/artifact/io.github.k3nder/log5k
libraryDependencies += "io.github.k3nder" % "log5k" % "1.2.2"
// https://mvnrepository.com/artifact/io.github.k3nder/kjson
libraryDependencies += "io.github.k3nder" % "kjson" % "0.2.7"


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


lazy val root = (project in file("."))
  .settings(
    name := "laucher.trinity",
    idePackagePrefix := Some("net.k3nder.launchertrinity")
  )
