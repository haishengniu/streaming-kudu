package cn.udrm.bigdata.streaming;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class JasonPartitioner<T> implements Partitioner {
	public JasonPartitioner(VerifiableProperties verifiableProperties) {}
	
	@Override
	public int partition(Object key, int numPartitions) {
		try {
            int partitionNum = Integer.parseInt((String) key);
            return Math.abs(Integer.parseInt((String) key) % numPartitions);  //取余
        } catch (Exception e) {
            return Math.abs(key.hashCode() % numPartitions);
        }
	}
	
}
