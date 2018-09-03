package com.wd.cloud.resourcesserver.model;


/**
 * @author He Zhigang
 * @date 2018/8/23
 * @Description:
 */
public class HbaseObjModel {
    private String tableName;
    private byte[] rowKey;
    private byte[] family = "cf".getBytes();
    private byte[] qualifier = "fileByte".getBytes();
    private byte[] value;

    public HbaseObjModel() {}

    public HbaseObjModel(String tableName, byte[] rowKey, byte[] value) {
        this.tableName = tableName;
        this.rowKey = rowKey;
        this.value = value;
    }


    public HbaseObjModel(String tableName, byte[] rowKey, byte[] family, byte[] qualifier, byte[] value) {
        this.tableName = tableName;
        this.rowKey = rowKey;
        this.family = family;
        this.qualifier = qualifier;
        this.value = value;
    }

    public String getTableName() {
        return tableName;
    }

    public HbaseObjModel setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public byte[] getRowKey() {
        return rowKey;
    }

    public HbaseObjModel setRowKey(byte[] rowKey) {
        this.rowKey = rowKey;
        return this;
    }

    public byte[] getFamily() {
        return family;
    }

    public HbaseObjModel setFamily(byte[] family) {
        this.family = family;
        return this;
    }

    public byte[] getQualifier() {
        return qualifier;
    }

    public HbaseObjModel setQualifier(byte[] qualifier) {
        this.qualifier = qualifier;
        return this;
    }

    public byte[] getValue() {
        return value;
    }

    public HbaseObjModel setValue(byte[] value) {
        this.value = value;
        return this;
    }
}
