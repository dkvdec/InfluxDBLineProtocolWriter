import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.{Call, Callback, MediaType, OkHttpClient, Request, RequestBody, Response}
import scalaj.http.{Http, HttpOptions}

import scala.collection.mutable.ListBuffer

class InfluxDBLineProtocol {

}

object InfluxDBLineProtocol {
	val influxDBUrl = InfluxDBSettings.getUrl

	def writeBatchAsync(batch: List[InfluxDBRecord]) : Boolean = {
		val response = Http(influxDBUrl).postData(batch.map(_.line).mkString("\n"))
			.header("Content-Type", "x-www-form-urlencoded")
			.header("Charset", "UTF-8")
			.option(HttpOptions.readTimeout(10000)).asString.code
		if (response > 299)
		{
			println(s"InfluxDbApi: Failed to write batch to InfluxDB $influxDBUrl batchSize=${batch.size}")
			false
		} else {
			println(s"InfluxDbApi: Successfully written ${batch.size} records to $influxDBUrl")
			true
		}
	}
}
