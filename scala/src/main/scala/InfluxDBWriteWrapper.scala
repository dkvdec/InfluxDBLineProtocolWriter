import akka.actor.{Actor, ActorRef, Props}
import scala.collection.mutable.ListBuffer

class InfluxDBWriteWrapper {
	private val pending: ListBuffer[InfluxDBRecord] = ListBuffer[InfluxDBRecord]()
	private var recordsSinceLastFlushMsg: Int = 0

	def addRecord(lst: List[InfluxDBRecord]): Unit = {
		pending ++= lst
		if (pending.size >= InfluxDBSettings.getBatchSize){
			flushRecords()
		}
	}

	private def flushRecords(): Unit = {
		val toWrite = pending.toList
		recordsSinceLastFlushMsg += toWrite.size
		if (toWrite.nonEmpty) {
			if (InfluxDBLineProtocol.writeBatchAsync(toWrite)) {
				pending.clear()
			}
		}
	}
}

//object InfluxDBWriteWrapper {
////	def props(mesurement: String, simulation: String, request: String,
////			  status: String, fields: (String, Int)*): Props =
////		Props(new InfluxDBWriteWrapper(log))
//
//
////	case class WriteRecords(lst: List[InfluxDBRecord])
////	case class FlushRecords()
////
////	def addRecords(recs: List[InfluxDBRecord])
////	def flushRecords()
//}
