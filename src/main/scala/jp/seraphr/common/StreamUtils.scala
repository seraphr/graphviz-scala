package jp.seraphr.common

import java.io.Reader
import java.io.BufferedReader
import java.io.Writer

trait StreamUtilsConf {
  val lineSeparator: String
}

trait StreamUtils { self: StreamUtilsConf =>
  implicit class ReaderUtil(val aReader: Reader) {
    def toIterator: Iterator[String] = {
      val tBufferedReader = new BufferedReader(aReader)
      Iterator.continually(tBufferedReader.readLine()).takeWhile(_ != null)
    }
  }

  implicit class WriterUtil[_W <: Writer](val aWriter: _W) {
    def writeLine(aString: String): _W = {
      aWriter.write(aString)
      aWriter.write(lineSeparator)

      aWriter
    }
  }
}

object LfStreamUtils extends StreamUtils with StreamUtilsConf {
  override val lineSeparator = "\n"
}

object CrStreamUtils extends StreamUtils with StreamUtilsConf {
  override val lineSeparator = "\r"
}

object CrLfStreamUtils extends StreamUtils with StreamUtilsConf {
  override val lineSeparator = "\r\n"
}