package cn.udrm.bigdata.streaming.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 通过内置propertiesMap，可以处理多个Properties配置文件。
 * 会自动检查Properties文件文件的最后修改时间，如果修改了Properties配置文件，不需要重启。
 * 
 * 缺省配置文件为：bigdata.properties
 * 
 * @author zb99@qq.com
 * @since Feb 28, 2016
 * @version $Revision$
 */
public class ConfigUtil {
	
	private static final Logger log = LoggerFactory.getLogger(ConfigUtil.class);

	public ConfigUtil() {
	}

	private static Map<String, Long> propertiesFileLastModifiedMap = new HashMap<String, Long>();
	
	private static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();
	
	private static Properties getProperties(String propertiesFile) {
		Long lastModified = propertiesFileLastModifiedMap.get(propertiesFile);
		long lastModified2 = new File(propertiesFile).lastModified();
		
		if (lastModified == null || lastModified == 0 || lastModified != lastModified2){
			Properties p = new Properties();
			try {
				//InputStream is = new HdfsUtil().getInputStream("hdfs://hdfs-master:8020/data/config/" + propertiesFile);
				InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream(propertiesFile);
				p.load(is);
				propertiesMap.put(propertiesFile, p);
				is.close();
			} catch (IOException e1) {
				log.error("读取配置文件" + propertiesFile + "错误!" + e1.getMessage(), e1);
			}
			return p;
		}
		else {
			return propertiesMap.get(propertiesFile);
		}
	}
	
	public static String getValue(String propertiesFile, String key){
		Properties properties = getProperties(propertiesFile);
		String value = (String)properties.get(key);
		return value;
	}

	public static String getValue(String key){
		return getValue("bigdata.properties", key);
	}
	
}
