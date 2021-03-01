import akka.actor.{Actor, ActorRef, Props}
import scala.collection.mutable.ListBuffer

abstract class InfluxDBWriterActor() {

	def dbConf: InfluxDBSettings
	def dbWriter: InfluxDBLineProtocol

	private val pending: ListBuffer[InfluxDBRecord] = ListBuffer[InfluxDBRecord]()
	private var recordsSinceLastFlushMsg: Int = 0


	private def addRecords(lst: List[InfluxDBRecord]): Unit = {
		pending ++= lst
		if (pending.size >= dbConf.writeBatchSize){
			flushRecords()
		}
	}

	private def flushRecords(): Unit = {
		val toWrite = pending.toList
		recordsSinceLastFlushMsg += toWrite.size
		if (toWrite.nonEmpty) {
			if (dbWriter.writeBatchAsync(toWrite)) {
				pending.clear()
			}
		}
	}
}

object InfluxDbWriterActor {
	def props(log: Logger): Props =
		Props(new InfluxDbWriterActor(log))

	case class WriteRecords(lst: List[InfluxDBRecord])
	case class FlushRecords()

	def addRecords(aref: ActorRef, recs: List[InfluxDBRecord]): Unit = aref ! WriteRecords(recs)
	def flushRecords(aref: ActorRef): Unit = aref ! FlushRecords()
}
