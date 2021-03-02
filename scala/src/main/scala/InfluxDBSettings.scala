case class InfluxDBSettings(
							   writeProto: String,
							   writeUrlPath: String,
							   writeHostPort: String,
							   writeBucketName: String,
							   writeBatchSize: Int
						   )

object InfluxDBSettings{
	val writeProto = "http"
	val writeUrlPath = "localhost"
	val writeHostPort = "8086"
	val writeBucketName = "hamlet"
	val writeBatchSize= 10

//	val defaultDBConf = InfluxDBSettings(
//		DEFAULT_PROTOCOL,
//		DEFAULT_HOST_PORT,
//		DEFAULT_URL_PATH,
//		DEFAULT_PROTO,
//		DEFAULT_BATCH_SIZE
//	)
}
