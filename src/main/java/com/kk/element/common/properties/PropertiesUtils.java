package com.kk.element.common.properties;

import com.kk.element.common.constant.PropertyEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件信息
 *
 * @author liujun
 * @version 0.0.1
 * @since 2019/03/13
 */
public class PropertiesUtils {

  private static final String DEF_FILENAME = "autocode.properties";

  private Properties prop = new Properties();

  private static final PropertiesUtils PROINSTANCE = new PropertiesUtils();

  public PropertiesUtils() {
    // loader default property fiile application.properties
    loadProperties(DEF_FILENAME);
  }

  public static PropertiesUtils getInstance() {
    return PROINSTANCE;
  }

  public void loadProperties(String fileName) {
    if (prop.isEmpty()) {
      InputStream in = null;

      try {
        in = PropertiesUtils.class.getResourceAsStream(fileName);
        if (in == null) {
          in = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
        }
        if (in == null) {
          in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        }

        if (in != null) {
          prop.load(in);
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      } finally {
        if (null != in) {
          try {
            in.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  public String getValue(PropertyEnum key) {
    return prop.getProperty(key.getKey());
  }

  /**
   * 获取布尔类型的值
   *
   * @param key key信息
   * @return 返回boolean类型的值
   */
  public boolean getBooleanValue(PropertyEnum key) {
    return Boolean.parseBoolean(prop.getProperty(key.getKey()));
  }

  /**
   * 获取int类型的属性值
   *
   * @param key
   * @return
   */
  public int getIntValue(PropertyEnum key) {
    return Integer.parseInt(prop.getProperty(key.getKey()));
  }

  /**
   * 获取int类型的值或者默认值
   *
   * @param key
   * @param defValue
   * @return
   */
  public int getIntegerValueOrDef(PropertyEnum key, int defValue) {
    String value = prop.getProperty(key.getKey());

    if (StringUtils.isNotEmpty(value)) {
      value = value.trim();
      if (StringUtils.isNumeric(value)) {
        return Integer.parseInt(value);
      }
      return defValue;
    } else {
      return defValue;
    }
  }

  /**
   * 获取属性信息
   *
   * @return 属性文件信息
   */
  public Properties getProperties() {
    return prop;
  }
}
