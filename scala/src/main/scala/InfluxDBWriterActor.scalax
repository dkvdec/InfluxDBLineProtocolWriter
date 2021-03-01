import akka.actor.{Actor, ActorRef, Props}

import scala.collection.mutable.ListBuffer
import InfluxDBRecord;

class InfluxDBWriterActor(log: Logger) extends Actor {
	private def conf = confParser.conf
	private def dbConf = confParser.conf.dbConf

	private val dbApi = new InfluxDBApi(confParser, log)
	private val pending: ListBuffer[InfluxDBRecord] = ListBuffer[InfluxDBRecord]()
	private var recordsSinceLastFlushMsg: Int = 0


	private def addRecords(lst: List[InfluxDBRecord]): Unit = {
		pending ++= lst
		if (pending.size >= InfluxDBSettings.writeBatchSize){
			flushRecords()
		}
	}

	private def flushRecords(): Unit = {
		val toWrite = pending.toList
		recordsSinceLastFlushMsg += toWrite.size
		if (toWrite.nonEmpty)
			dbApi.writeBatchAsync(toWrite)
		pending.clear()
	}

	override def receive: Receive = {
		case WriteRecords(lst: List[InfluxDBRecord]) => addRecords(lst)
		case FlushRecords() => {
			flushRecords()
			log.info(s"InfluxDbWriterActor - FlushRecords received - recordsSinceLastFlushMsg=$recordsSinceLastFlushMsg")
			recordsSinceLastFlushMsg = 0
		}
		case x => log.error(s"InfluxDbWriterActor.receive: Unexpected message received: $x")
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
