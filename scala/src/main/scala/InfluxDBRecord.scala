case class InfluxDBRecord(
							 ts: Long,
							 measurement: String,
							 simulation: String,
							 request: String,
							 status: String,
							 fields: Seq[(String, Int)]
						 ) {

//	private lazy val tagsStr = if (tags.isEmpty) "" else {
//		"," + tags.sortBy(_._1).map { case (k,v) =>
//			s"$k=${v.replaceAll("[\\s,]", "_")}"
//		}.mkString(",")
//	}

	private lazy val fieldStr = if (fields.isEmpty) "" else {
		fields.sortBy(_._1).map { case (k,v) =>
			s"$k=$v"
		}.mkString(",")
	}

	lazy val line: String = s"$measurement,simulation=$simulation,request=$request,status=$status ${fieldStr} $ts"
}
