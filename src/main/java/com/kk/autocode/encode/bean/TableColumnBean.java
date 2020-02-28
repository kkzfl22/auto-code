package com.kk.autocode.encode.bean;

/**
 * java的对应数据库的字段信息 
 * @author liujun
 * @version 1.0.0
 * @since 2017年8月8日 下午5:04:10
 */
public class TableColumnBean {

    /**
     * 列名
    * @字段说明 columnName
    */
    private String columnName;

    /**
     * 注释信息
    * @字段说明 columnMsg
    */
    private String columnMsg;

    /**
     * 列的类型信息
    * @字段说明 dataType
    */
    private String dataType;

    /**
     * 是否为主键
    * @字段说明 primaryKey
    */
    private boolean primaryKey;

    /**
     * 长度信息
    * @字段说明 dataLength
    */
    private int dataLength;

    /**
     * 精度
    * @字段说明 dataScale
    */
    private int dataScale;
    
    /**
     * 是否自增长
     */
    private boolean isAutoIncrement;

    public TableColumnBean(String columnName, String columnMsg, String dataType, boolean primaryKey) {
        super();
        this.columnName = columnName;
        this.columnMsg = columnMsg;
        this.dataType = dataType;
        this.primaryKey = primaryKey;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnMsg() {
        return columnMsg;
    }

    public void setColumnMsg(String columnMsg) {
        this.columnMsg = columnMsg;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public int getDataScale() {
        return dataScale;
    }

    public void setDataScale(int dataScale) {
        this.dataScale = dataScale;
    }
    

    public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TableBean [columnName=");
		builder.append(columnName);
		builder.append(", columnMsg=");
		builder.append(columnMsg);
		builder.append(", dataType=");
		builder.append(dataType);
		builder.append(", primaryKey=");
		builder.append(primaryKey);
		builder.append(", dataLength=");
		builder.append(dataLength);
		builder.append(", dataScale=");
		builder.append(dataScale);
		builder.append(", isAutoIncrement=");
		builder.append(isAutoIncrement);
		builder.append("]");
		return builder.toString();
	}

	
}
