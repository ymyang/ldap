package com.qycloud.oatos.ldap;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 对象名称：取配置文件内容
 * 
 * 作者： 秦利军
 * 
 * 完成日期：
 * 
 * 对象内容：根据节点取配置文件内容
 */
public class ConfigUtil {

	// 日志
	private final static Properties props = new Properties();

	static {
		InputStream is = null;
		try {
			File file = new File("/opt/oatos/ldap", "config.properties");
			if (file.exists() == true) {
				is = new FileInputStream(file);
			} else {
				is = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties");
			}
			props.load(is);
			is.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
			}
		}
	}

	public static String getValue(String key) {
		String value = props.getProperty(key);
		return value;
	}

}
