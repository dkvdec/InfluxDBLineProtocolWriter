case class InfluxDBSettings(
							   writeHostPort: String = "8086",
							   writeUrlPath: String = "localhost",
							   writeTimeoutMs: Long = 5000,
							   writeBatchSize: Int = 10,
							   writeProto: String = "http"
						   )
