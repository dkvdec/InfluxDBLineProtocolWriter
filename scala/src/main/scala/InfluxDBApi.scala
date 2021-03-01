import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.{Call, Callback, MediaType, OkHttpClient, Request, RequestBody, Response}

class InfluxDBApi(log: Logger) {
	private def conf = confParser.conf
	def dbConf: InfluxDBSettings

	private def influxDBUrl =
		dbConf.writeProto + "://" + dbConf.writeHostPort + ":" + dbConf.writeUrlPath + "/write?db=" + dbConf.writeBucketName

	def writeBatchAsync(batch: List[InfluxDBRecord]): Unit = {
		if (!conf.writesEnabled){
			if (batch.nonEmpty) {
				log.warn(s"InfluxDbApi.writeBatchAsync: write request received with disabled config: " +
					s"batch.size=${batch.size} batch.head=${batch.head}")
			}
			return
		}
		val myUrl = influxDbUrl
		val myClient = httpClient.newBuilder().
			callTimeout(dbConf.writeTimeoutMs, TimeUnit.MILLISECONDS).build()
		val body = RequestBody.create(MediaType.get("text/plain"), batch.map(_.line).mkString("\n"))
		val request = new Request.Builder().url(myUrl).post(body).build()
		myClient.newCall(request).enqueue(new Callback {
			override def onFailure(call: Call, e: IOException): Unit = {
				log.ex(e, s"InfluxDbApi: Failed to write batch to InfluxDb ($myUrl) batchSize=${batch.size}")
			}
			override def onResponse(call: Call, response: Response): Unit = {
				log.debug(s"InfluxDbApi: Successfully written ${batch.size} records to $myUrl")
				try {
					response.body().close()
				} catch { case t: Throwable =>
					log.warn(s"InfluxDbApi: Failed to close response body from $myUrl : ${t.getMessage}")
				}
			}
		})
	}
}
