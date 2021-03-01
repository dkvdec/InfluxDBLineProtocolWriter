import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
//import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
//import com.influxdb.client.write.Point;
//import com.influxdb.query.FluxRecord;
//import com.influxdb.query.FluxTable;

public class InfluxSender {

        private static char[] token = "my-token".toCharArray();
        private static String org = "my-org";
        private static String bucket = "my-db";

        public static void main(final String[] args) {

            InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucket);

            try (WriteApi writeApi = influxDBClient.getWriteApi()) {

                writeApi.writeRecord(WritePrecision.S, "gatling,simulation=testSim,request=testRequest1 duration=55i,code=200i");
                writeApi.writeRecords(WritePrecision.S, Arrays.asList("gatling,simulation=testSim,request=testRequest2 duration=35i,code=201i", "gatling,simulation=testSim,request=testRequest3 duration=18i,code=404i"));

                Point point = Point.measurement("gatling")
                        .addTag("simulation", "testSim")
                        .addTag("request", "testRequest2")
                        .addField("duration", 45)
                        .addField("code", 203)
                        .time(Instant.now(), WritePrecision.S);

                writeApi.writePoint(bucket, org, point);
            }

            influxDBClient.close();
        }

}
