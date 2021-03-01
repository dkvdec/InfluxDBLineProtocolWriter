case class InfluxDBSettings(
							   writeProto: String = "http",
							   writeUrlPath: String = "localhost",
							   writeHostPort: String = "8086",
							   writeBucketName: String = "hamlet",
							   writeBatchSize: Int = 10
						   )
