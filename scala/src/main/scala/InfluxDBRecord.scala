case class InfluxDBRecord(
							 measurement: String,
							 simulation: String,
							 request: String,
							 status: String,
							 fields: (String, Int)*
						 ) {

//	private lazy val tagsStr = if (tags.isEmpty) "" else {
//		"," + tags.sortBy(_._1).map { case (k,v) =>
//			s"$k=${v.replaceAll("[\\s,]", "_")}"
//		}.mkString(",")
//	}

//	arrayOfTuples map {case (e1: Int, e2: String) => e1.toString + e2}

	private lazy val fieldStr = if (fields.isEmpty) "" else {
		fields.map { case(k: String, v:Int) =>
			s"$k=${v}i"
		}.mkString(",")
	}

	lazy val line: String = s"$measurement,simulation=$simulation,request=$request,status=$status ${fieldStr} ${System.currentTimeMillis()}"
//	println(line)
}
