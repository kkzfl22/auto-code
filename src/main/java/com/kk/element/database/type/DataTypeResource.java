package com.kk.element.database.type;

import com.kk.autocode.util.IOutils;
import com.kk.element.database.type.constant.DatabaseTypeEnum;
import com.kk.element.database.type.constant.DatabaseTypeSourceEnum;
import com.kk.element.database.type.pojo.bean.DatabaseTypeMsgBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/09/13
 */
public class DataTypeResource {

  /** loggerinfo */
  private Logger logger = LoggerFactory.getLogger(DataTypeResource.class);

  /** instance Object */
  public static final DataTypeResource INSTANCE = new DataTypeResource();

  /** resource path */
  private static final String RESOURCE_PATH_NAME = "classpath*:dbtype/sqltype_*.properties";

  /** db TYPE INFO by properties key */
  private static final Map<DatabaseTypeEnum, Map<DatabaseTypeSourceEnum, DatabaseTypeMsgBO>>
      TARGET_DBTYPE_CACHE_MAP = new HashMap<>(10);

  /** src type info */
  private static final Map<DatabaseTypeEnum, Map<Integer, DatabaseTypeMsgBO>>
      SRCDB_TO_TARGERKEY_MAP = new HashMap<>(10);

  static {
    // load database Column type
    INSTANCE.loadResourceDbColumnType();
  }

  /** load database Column type */
  private void loadResourceDbColumnType() {
    InputStream input = null;
    // load resource
    Properties properties = new Properties();

    ResourcePatternResolver classPattern = new PathMatchingResourcePatternResolver();
    try {

      Resource[] resources = classPattern.getResources(RESOURCE_PATH_NAME);

      Map.Entry<Object, Object> iten = null;
      DatabaseTypeSourceEnum dbTypeItem = null;
      DatabaseTypeMsgBO typeBo = null;

      for (Resource res : resources) {
        DatabaseTypeEnum typeNnum = DatabaseTypeEnum.getPropertiesDbType(res.getFilename());

        Map<DatabaseTypeSourceEnum, DatabaseTypeMsgBO> dbTypeBOKeyMap =
            TARGET_DBTYPE_CACHE_MAP.get(typeNnum);

        if (null == dbTypeBOKeyMap) {
          dbTypeBOKeyMap = new HashMap<>(45);
        }

        Map<Integer, DatabaseTypeMsgBO> srcTargerMap = SRCDB_TO_TARGERKEY_MAP.get(typeNnum);

        if (null == srcTargerMap) {
          srcTargerMap = new HashMap<>(45);
        }

        input = res.getInputStream();
        properties.load(input);
        Iterator<Map.Entry<Object, Object>> iter = properties.entrySet().iterator();

        while (iter.hasNext()) {
          iten = iter.next();
          String dbTypeKeys = String.valueOf(iten.getKey());
          String dbTypeValue = String.valueOf(iten.getValue());

          dbTypeItem = DatabaseTypeSourceEnum.getDataBaseType(dbTypeKeys);

          if (null != dbTypeItem) {
            typeBo = DatabaseTypeMsgBO.Parse(dbTypeKeys, dbTypeValue);
            dbTypeBOKeyMap.put(dbTypeItem, typeBo);

            srcTargerMap.put(typeBo.getJdbcType(), typeBo);
          } else {
            throw new RuntimeException("curr dbtype config exception ,key:" + dbTypeKeys);
          }
        }

        TARGET_DBTYPE_CACHE_MAP.put(typeNnum, dbTypeBOKeyMap);
        SRCDB_TO_TARGERKEY_MAP.put(typeNnum, srcTargerMap);

        properties.clear();
        input.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("loadResourceDbColumnType IOexception", e);
    } finally {
      IOutils.closeStream(input);
    }
  }

  /**
   * get default value
   *
   * @param srcDbType type
   * @param collumnType java.sql.Types
   * @return defaultValue
   */
  public Object getDefaultValue(
      DatabaseTypeEnum srcDbType, int collumnType, DatabaseTypeEnum targetDbType) {

    DatabaseTypeMsgBO srcTypeMsg = this.getSrcTypeinfo(srcDbType, collumnType);

    DatabaseTypeMsgBO targetMsg =
        this.getTargetTypeinfo(targetDbType, srcTypeMsg.getDataTypeEnum());

    return targetMsg.getDefValue();
  }

  /**
   * get default value
   *
   * @param srcColumnType java.sql.Types
   * @return defaultValue
   */
  public DatabaseTypeMsgBO getSrcTypeinfo(DatabaseTypeEnum srcDbType, int srcColumnType) {

    Map<Integer, DatabaseTypeMsgBO> dbcolumn = SRCDB_TO_TARGERKEY_MAP.get(srcDbType);

    // src type info
    return dbcolumn.get(srcColumnType);
  }

  /**
   * get default value
   *
   * @param targetDbType java.sql.Types
   * @return defaultValue
   */
  public DatabaseTypeMsgBO getTargetTypeinfo(
      DatabaseTypeEnum targetDbType, DatabaseTypeSourceEnum targetColumnEnum) {

    Map<DatabaseTypeSourceEnum, DatabaseTypeMsgBO> dbcolumn =
        TARGET_DBTYPE_CACHE_MAP.get(targetDbType);

    // src type info
    return dbcolumn.get(targetColumnEnum);
  }



  /**
   * get target typeinfo
   *
   * @param srcDbType
   * @param collumnType
   * @param targetDbType
   * @return
   */
  public DatabaseTypeMsgBO getTargetType(
      DatabaseTypeEnum srcDbType, int collumnType, DatabaseTypeEnum targetDbType) {

    DatabaseTypeMsgBO srcTypeMsg = this.getSrcTypeinfo(srcDbType, collumnType);

    DatabaseTypeMsgBO targetMsg =
        this.getTargetTypeinfo(targetDbType, srcTypeMsg.getDataTypeEnum());

    return targetMsg;
  }
}
