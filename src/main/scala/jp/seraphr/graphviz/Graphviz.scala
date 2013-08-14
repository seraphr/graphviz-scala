package jp.seraphr.graphviz

import jp.seraphr.common.LoanPatterns
import java.io.InputStream
import java.io.OutputStream
import scala.language.postfixOps
import java.io.StringWriter
import jp.seraphr.common.StreamUtils
import scala.collection.mutable.ListBuffer
import java.io.File
import scala.util.Try
import scala.util.Success

trait Graphviz { self: GraphvizConf with LoanPatterns =>
  import scala.sys.process._

  def generateGraph(aInput: InputStream, aFile: File): Option[Seq[String]] =
    aFile.openOutput(aOutput => generateGraph(aInput, aOutput))

  /**
   *
   * @return コマンド実行に成功したら[[scala.None]] 失敗したら標準エラー出力の内容を格納した、[[scala.Some]]
   */
  def generateGraph(aInput: InputStream, aOutput: OutputStream): Option[Seq[String]] = {
    val tErrorBuffer = new ListBuffer[String]
    val tLogger = ProcessLogger(_ => Unit, e => tErrorBuffer.+=(e))

    val tExitCode = command #< aInput #> aOutput ! tLogger

    if (tExitCode == 0)
      None
    else
      Some(tErrorBuffer.toSeq)
  }
}

trait GraphvizConf {
  lazy val dotPath = "dot"
  lazy val command = {
    val tPrefix = Try { System.getProperty("os.name") }.map(_.toLowerCase().contains("windows")) match {
      case Success(true) => "cmd /c "
      case _             => ""
    }

    tPrefix + s"${dotPath} -K${layout} -T${imageType}"
  }

  lazy val imageType = "png"
  lazy val layout = "dot"
}