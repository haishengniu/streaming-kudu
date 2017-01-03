package cn.udrm.bigdata.streaming;

import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.udrm.bigdata.streaming.util.ConfigUtil;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducerTest {
	private static final Logger log = LoggerFactory.getLogger(KafkaProducerTest.class);
	private Producer<String, String> producer = null;
	private String topicName;
	
	public static void main(String[] args) {
		KafkaProducerTest kafkaProducerTest = new KafkaProducerTest();
		kafkaProducerTest.producer = kafkaProducerTest.createProducer();
		kafkaProducerTest.topicName = "streaming-test";//ConfigUtil.getValue("topicName.source");
		kafkaProducerTest.process();
	}
	
	private Producer<String, String> createProducer(){
  		Properties props = new Properties();
  		props.put("metadata.broker.list", "10.11.10.122:6667,10.11.10.123:6667");  //ConfigUtil.getValue("metadata.broker.list"));
  		props.put("serializer.class", "kafka.serializer.StringEncoder");
  		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
  		props.put("partitioner.class", "cn.udrm.bigdata.streaming.JasonPartitioner");
  		props.put("request.required.acks","0");
  		ProducerConfig configs = new ProducerConfig(props);
  		Producer<String, String> producer = new Producer<String, String>(configs);
  		
  		return producer;
	}
	
	public void process() {
		String string = "d598bba5-418c-4326-a46c-98ec7523e6e0,1482995377783,1037500000,-62!-49!-20!-20!-45!-50!-52!-34!-36!-18!-2!-44!-65!-62!-59!-52!-58!-54!-55!-33!-27!-49!-47!-56!-62!-53!-55!-54!-61!-53!-46!-47!-41!-49!-45!-50!-44!-37!-61!-62!-52!-33!-30!-52!-51!-60!-64!-60!-66!-59!-58!-58!-56!-63!-51!-48!-59!-61!-60!-50!-51!-38!-36!-30!-31!-20!6!-22!-14!-20!-51!-46!-52!-51!-51!-48!-38!-23!27!15!-38!-39!-50!-52!-47!-30!-18!-16!-5!-21!4!-5!-50!-47!-34!6!-9!-55!-55!-56!-46!-46!-55!-53!-57!-55!-55!-59!-52!-47!-35!-37!-7!-10!-44!-54!-59!-57!-49!-36!-26!-27!5!-15!-51!-49!-47!-33!-45!-60!-53!-45!-27!-41!-40!-35!-44!-38!-43!-51!-46!-47!-41!-46!-34!-40!-40!-34!-50!-48!-43!-26!-24!-45!-44!-48!-48!-42!-53!-49!-47!-38!-34!-44!-37!-39!-42!-40!-45!-40!-32!-9!-14!-5!-9!-36!-28!-42!-52!-43!-44!-36!-45!-44!-41!-51!-43!-48!-45!-38!14!47!16!-13!-31!-34!-32!-45!-36!-37!-13!-13!-15!-4!-32!-39!-38!-52!-44!-43!-26!-30!-42!-32!-35!-33!-37!-42!-31!-19!-2!-25!-36!-28!-17!-2!0!30!9!-36!-32!-43!-39!-36!-34!-28!-47!-45!-45!-44!-27!-30!-32!-42!-39!-33!-32!-11!4!30!8!-39!-34!-15!20!-6!-38!-26!18!52!24!41!33!-24!-20!-33!-32!-21!-23!-13!-23!-36!-35!-19!-11!-35!-26!-30!-43!-37!-46!-36!-26!1!0!-30!-33!-29!-25!-27!-31!-20!-34!-34!-31!-24!-16!-35!-22!-30!-35!-24!-36!-37!-39!-33!-18!-27!-36!-23!16!2!-18!-8!-36!-39!-27!2!14!0!5!-10!-12!-8!-20!-12!-13!-11!-7!-20!-12!3!-20!-32!-12!3!-9!10!-3!14!20!-18!-17!-23!3!2!-40!-40!-43!-49!-41!-43!-26!-20!-21!-20!-32!-27!-25!-27!-30!-25!-14!-15!-1!-20!-41!-31!-31!-23!-32!-19!-21!-46!-40!-41!-33!-33!-42!-34!-39!-35!-28!-13!-9!-39!-35!-21!-4!1!-9!-24!-17!2!-6!-25!-13!-33!-47";
		//string = "aaa,bbb,ccc,ddd";
		KeyedMessage<String,String> keyedMessage = new KeyedMessage<String,String>(topicName,new Random().nextInt(2)+"", string); //随机分配到某个partition
		producer.send(keyedMessage);
	}
	
}
