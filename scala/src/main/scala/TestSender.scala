import okhttp3.{Call, Callback, MediaType, OkHttpClient, Request, RequestBody, Response}
import scalaj.http._

object TestSender {
	def main(args: Array[String]): Unit = {
		val result = Http("http://localhost:8086/write?db=hamlet").postData("""gatling,request=testRequest code=128 1614586287438705248
															   |gatling,request=testRequest code=129 1614586287438705249
															   |gatling,request=testRequest code=130 1614586287438705250""".stripMargin)
			.header("Content-Type", "x-www-form-urlencoded")
			.header("Charset", "UTF-8")
			.option(HttpOptions.readTimeout(500)).asString
		result.code
	}
}
