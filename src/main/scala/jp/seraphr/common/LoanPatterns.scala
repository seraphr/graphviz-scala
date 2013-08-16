package jp.seraphr.common

import scala.util.Try
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset
import java.io.Reader
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.FileInputStream
import language.reflectiveCalls
import java.io.OutputStream
import java.io.FileOutputStream
import java.io.Writer
import java.io.OutputStreamWriter

trait LoanPatternConf {
  val defaultEnc: Charset
}

trait LoanPatterns { self: LoanPatternConf =>
  implicit class PatternUtil(file: File) {
    def openIterator[_Result](f: Iterator[String] => _Result, aCharset: Charset = defaultEnc) = {
      self.openIterator(file, aCharset)(f)
    }

    def openReader[_Result](f: Reader => _Result, aCharset: Charset = defaultEnc) = {
      self.openReader(file, aCharset)(f)
    }

    def openInput[_Result](f: InputStream => _Result) = {
      self.openInput(file)(f)
    }

    def openWriter[_Result](f: Writer => _Result, aCharset: Charset = defaultEnc) = {
      self.openWriter(file, aCharset)(f)
    }

    def openOutput[_Result](f: OutputStream => _Result) = {
      self.openOutput(file)(f)
    }
  }

  type Closable = { def close(): Unit }

  def closeQuietly(aStream: Closable): Unit = {
    try {
      if (aStream != null)
        aStream.close
      else
        ()
    } catch {
      case _: Throwable => ()
    }
  }

  def openOutput[_Result](aFile: File)(f: OutputStream => _Result): _Result = {
    var tStream: OutputStream = null
    try {
      tStream = new FileOutputStream(aFile)
      f(tStream)
    } finally {
      closeQuietly(tStream)
    }
  }

  def openWriter[_Result](aFile: File, aEnc: Charset = defaultEnc)(f: Writer => _Result) = {
    openOutput(aFile) { aStream =>
      val tReader = new OutputStreamWriter(aStream, aEnc)
      f(tReader)
    }
  }

  def openInput[_Result](aFile: File)(f: InputStream => _Result): _Result = {
    var tStream: InputStream = null
    try {
      tStream = new FileInputStream(aFile)

      f(tStream)
    } finally {
      closeQuietly(tStream)
    }
  }

  def openReader[_Result](aFile: File, aEnc: Charset = defaultEnc)(f: Reader => _Result) = {
    openInput(aFile) { aStream =>
      val tReader = new InputStreamReader(aStream, aEnc)
      f(tReader)
    }
  }

  import LfStreamUtils._
  def openIterator[_Result](aFile: File, aEnc: Charset = defaultEnc)(f: Iterator[String] => _Result) = {
    openReader(aFile, aEnc) { aReader =>
      val tIterator = aReader.toIterator

      f(tIterator)
    }
  }
}

trait Utf8LoanPatterns extends LoanPatterns with LoanPatternConf {
  override val defaultEnc = Charset.forName("UTF-8")
}