import java.lang.Character.getName
import com.github.dwickern.macros.NameOf._

import okhttp3.{Call, Callback, MediaType, OkHttpClient, Request, RequestBody, Response}
import scalaj.http._

object TestSender {
	def main(args: Array[String]): Unit = {
//		val result = Http("http://localhost:8086/write?db=hamlet").postData("""gatling,request=testRequest code=128 1614586287438705248
//															   |gatling,request=testRequest code=129 1614586287438705249
//															   |gatling,request=testRequest code=130 1614586287438705250""".stripMargin)
//			.header("Content-Type", "x-www-form-urlencoded")
//			.header("Charset", "UTF-8")
//			.option(HttpOptions.readTimeout(10000)).asString
//
		val min = 35
		val mean = 45
		val max = 55
//
//		val record = InfluxDBRecord("gatling","testSim", "testReq", "OK", (nameOf(min),min), (nameOf(mean),mean), (nameOf(max),max)).line

		val toDB = new InfluxDBWriteWrapper
		toDB.addRecord(List(InfluxDBRecord("gatling", "testSim", "testReq", "OK", (nameOf(min), min), (nameOf(mean), mean), (nameOf(max), max))))
		toDB.addRecord(List(InfluxDBRecord("gatling", "testSim", "testReq1", "OK", (nameOf(min), min), (nameOf(mean), mean), (nameOf(max), max))))
		toDB.addRecord(List(InfluxDBRecord("gatling", "testSim", "testReq2", "OK", (nameOf(min), min), (nameOf(mean), mean), (nameOf(max), max))))
		toDB.addRecord(List(InfluxDBRecord("gatling", "testSim", "testReq3", "OK", (nameOf(min), min), (nameOf(mean), mean), (nameOf(max), max))))
		toDB.addRecord(List(InfluxDBRecord("gatling", "testSim", "testReq4", "OK", (nameOf(min), min), (nameOf(mean), mean), (nameOf(max), max))))
//		println(record)
	}
}
