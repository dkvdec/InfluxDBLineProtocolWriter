case class InfluxDBSettings(
							   Proto: String,
							   UrlPath: String,
							   HostPort: String,
							   BucketName: String,
							   BatchSize: Int
						   )

object InfluxDBSettings{
	val Proto = "http"
	val UrlPath = "localhost"
	val HostPort = "8086"
	val BucketName = "hamlet"
	val BatchSize = 5

	def getUrl: String = {
		Proto + "://" + UrlPath + ":" + HostPort + "/write?db=" + BucketName
	}

	def getBatchSize: Int = {
		BatchSize
	}
}
