

case class InfluxDBSettings(
							   writeProto: String,
							   writeUrlPath: String,
							   writeHostPort: String,
							   writeBucketName: String,
							   writeBatchSize: Int = 10
						   )

object InfluxDbPluginConf
