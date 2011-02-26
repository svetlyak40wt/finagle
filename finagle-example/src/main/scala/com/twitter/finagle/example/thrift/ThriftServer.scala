package com.twitter.finagle.example.thrift

import com.twitter.finagle.thrift.ThriftServerFramedCodec
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.builder.{ServerBuilder, Server}
import java.net.InetSocketAddress
import com.twitter.util.Future

object ThriftServer {
  def main(args: Array[String]) {
    // Implement the Thrift Interface
    val processor = new Hello.ServiceIface {
      def hi() = Future.value("hi")
    }

    // Convert the Thrift Processor to a Finagle Service
    val service = new Hello.Service(processor, new TBinaryProtocol.Factory())

    val server: Server = ServerBuilder()
      .bindTo(new InetSocketAddress(8080))
      .codec(ThriftServerFramedCodec())
      .build(service)
  }
}