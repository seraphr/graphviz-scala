name := "graphviz-scala"

version := "0.0.2"

scalaVersion := "2.10.2"

organization := "jp.seraphr"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.10.0" % "test"

publishMavenStyle := true

publishArtifact in Test := false

publishTo := Some(Resolver.file("file",  new File("""K:\git\seraphr.github.com\maven""")))

EclipseKeys.withSource := true

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

pomExtra := (
  <url>https://github.com/seraphr/graphviz-scala</url>
  <licenses>
    <license>
      <name>BSD-style</name>
      <url>http://www.opensource.org/licenses/bsd-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:seraphr/graphviz-scala.git</url>
    <connection>scm:git:git@github.com:seraphr/graphviz-scala.git</connection>
  </scm>
  <developers>
    <developer>
      <id>seraph</id>
      <name>seraph</name>
      <url>https://github.com/seraphr/</url>
    </developer>
  </developers>)