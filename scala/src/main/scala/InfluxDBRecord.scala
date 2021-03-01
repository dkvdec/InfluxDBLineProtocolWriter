case class InfluxDBRecord(
							 measurement: String,
							 tags: Seq[(String,String)],
							 fields: Seq[(String, Int)],
							 ts: Long
						 ) {

	private lazy val tagsStr = if (tags.isEmpty) "" else {
		"," + tags.sortBy(_._1).map { case (k,v) =>
			s"$k=${v.replaceAll("[\\s,]", "_")}"
		}.mkString(",")
	}

	private lazy val fieldStr = if (fields.isEmpty) "" else {
		tags.sortBy(_._1).map { case (k,v) =>
			s"$k=$v"
		}.mkString(",")
	}

	lazy val line: String = s"${measurement}${tagsStr} ${fieldStr} $ts"
}
