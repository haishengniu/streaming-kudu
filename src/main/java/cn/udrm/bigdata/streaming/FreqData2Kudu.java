package cn.udrm.bigdata.streaming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import scala.Tuple2;

import org.apache.kudu.client.Insert;
import org.apache.kudu.client.KuduClient;
import org.apache.kudu.client.KuduSession;
import org.apache.kudu.client.KuduTable;
import org.apache.kudu.client.PartialRow;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

public class FreqData2Kudu {

	private static final Pattern SPACE = Pattern.compile(",");
	private static final String KUDU_MASTER = System.getProperty("kuduMaster", "10.11.10.121");

	private FreqData2Kudu() {
	}
	public static void main(String[] args) throws Exception {
	    if (args.length < 4) {
	      System.err.println("Usage: FreqData2Kudu <zkQuorum> <group> <topics> <numThreads>");
	      System.exit(1);
	    }
	    
	    String tableName = "kudu_table_test3";
		KuduClient client = new KuduClient.KuduClientBuilder(KUDU_MASTER).build();
		KuduTable table = client.openTable(tableName);
		
		
	    
	    //StreamingExamples.setStreamingLogLevels();
	    SparkConf sparkConf = new SparkConf().setAppName("JavaKafkaStreamingKudu");
	    // Create the context with 2 seconds batch size
	    JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, new Duration(10000));

	    int numThreads = Integer.parseInt(args[3]);
	    Map<String, Integer> topicMap = new HashMap<>();
	    String[] topics = args[2].split(",");
	    for (String topic: topics) {
	      topicMap.put(topic, numThreads);
	    }

	    JavaPairReceiverInputDStream<String, String> messages =
	            KafkaUtils.createStream(jssc, args[0], args[1], topicMap);

	    JavaDStream<String> lines = messages.map(new Function<Tuple2<String, String>, String>() {
	      @Override
	      public String call(Tuple2<String, String> tuple2) {
	        return tuple2._2();
	      }
	    });
	    
	    JavaDStream<String> line = lines.flatMap(new FlatMapFunction<String, String>() {
		      @Override
		      public Iterable<String> call(String x) {
		    	List<String> arr = new ArrayList<String>();
		    	arr.add(x);
		    	return arr;
		      }
		    });
	    
	    line.foreachRDD(new VoidFunction<JavaRDD<String>>() {

			@Override
			public void call(JavaRDD<String> rdd) throws Exception {
				// TODO Auto-generated method stub
				List<String> listStr = rdd.collect();
				for (String string : listStr) {
					String[] str = SPACE.split(string);
					try {
						//id,name,age,city
						KuduSession session = client.newSession();
						Insert insert = table.newInsert();
						PartialRow raw = insert.getRow();
						if (str[0].isEmpty()) {
							raw.setNull(0);
						}else {
							//raw.addInt(0, Integer.parseInt(str[0]));
							raw.addString(0, str[0]);
						}
						if (str[1].isEmpty()) {
							raw.setNull(1);
						}else {
							raw.addString(1, str[1]);
						}
						if (str[2].isEmpty()) {
							raw.setNull(2);
						}else {
							//raw.addInt(2, Integer.parseInt(str[2]));
							raw.addString(2, str[2]);
						}
						if (str[3].isEmpty()) {
							raw.setNull(3);
						}else {
							raw.addString(3, str[3]);
						}
						session.apply(insert);
						session.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				
				
			}
		});
	  
		
	    
	    /*
	    JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
	      @Override
	      public Iterable<String> call(String x) {
	    	List<String> arr = new ArrayList<String>();
	    	StringTokenizer st=new StringTokenizer(x," ");
	        while(st.hasMoreTokens()){
	        	arr.add(st.nextToken());
	        }
	    	return arr;
	        //return Arrays.asList(SPACE.split(x)).iterator();
	      }
	    });
	    
	    JavaPairDStream<String, Integer> wordCounts = words.mapToPair(
	      new PairFunction<String, String, Integer>() {
	        @Override
	        public Tuple2<String, Integer> call(String s) {
	          return new Tuple2<>(s, 1);
	        }
	      }).reduceByKey(new Function2<Integer, Integer, Integer>() {
	        @Override
	        public Integer call(Integer i1, Integer i2) {
	          return i1 + i2;
	        }
	      });
	      */

	    //line.print();
	    //lines.count();
	    jssc.start();
	    jssc.awaitTermination();
	  }
	
	
}
