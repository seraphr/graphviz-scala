package jp.seraphr.main

import scala.language.postfixOps

object TestRun {

  import scala.sys.process._
  def main(args: Array[String]): Unit = {
    val tBuilder = "cmd /c dir" #> System.out

    val tLogger = ProcessLogger(l => System.out.println("hoge" + l), e => System.err.println("fuga" + e))

    val tExitCode = tBuilder ! tLogger

    println(s"tExitCode is ${tExitCode}")
  }
}