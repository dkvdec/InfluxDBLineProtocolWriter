import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.{Call, Callback, MediaType, OkHttpClient, Request, RequestBody, Response}
import scalaj.http.{Http, HttpOptions}

abstract class InfluxDBLineProtocol() {
	def dbConf: InfluxDBSettings

	private def influxDBUrl =
		dbConf.writeProto + "://" + dbConf.writeHostPort + ":" + dbConf.writeUrlPath + "/write?db=" + dbConf.writeBucketName

	def writeBatchAsync(batch: List[InfluxDBRecord]) : Boolean = {
		if (Http(influxDBUrl).postData(batch.mkString("\n"))
			.header("Content-Type", "x-www-form-urlencoded")
			.header("Charset", "UTF-8")
			.option(HttpOptions.readTimeout(500)).asString.code > 299)
		{
			println(s"InfluxDbApi: Failed to write batch to InfluxDB $influxDBUrl batchSize=${batch.size}")
			false
		} else {
			println(s"InfluxDbApi: Successfully written ${batch.size} records to $influxDBUrl")
			true
		}
	}
}
