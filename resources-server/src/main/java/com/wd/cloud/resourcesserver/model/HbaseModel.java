package com.wd.cloud.resourcesserver.model;

/**
 * @author He Zhigang
 * @date 2018/8/23
 * @Description:
 */
public class HbaseModel {
    private String tableName;
    private byte[] rowKey;
    private byte[] family = "cf".getBytes();
    private byte[] qualifier = "fileByte".getBytes();
    private byte[] value;

    public HbaseModel() {}

    public HbaseModel(String tableName, byte[] rowKey, byte[] value) {
        this.tableName = tableName;
        this.rowKey = rowKey;
        this.value = value;
    }


    public HbaseModel(String tableName, byte[] rowKey, byte[] family, byte[] qualifier, byte[] value) {
        this.tableName = tableName;
        this.rowKey = rowKey;
        this.family = family;
        this.qualifier = qualifier;
        this.value = value;
    }

    public String getTableName() {
        return tableName;
    }

    public HbaseModel setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public byte[] getRowKey() {
        return rowKey;
    }

    public HbaseModel setRowKey(byte[] rowKey) {
        this.rowKey = rowKey;
        return this;
    }

    public byte[] getFamily() {
        return family;
    }

    public HbaseModel setFamily(byte[] family) {
        this.family = family;
        return this;
    }

    public byte[] getQualifier() {
        return qualifier;
    }

    public HbaseModel setQualifier(byte[] qualifier) {
        this.qualifier = qualifier;
        return this;
    }

    public byte[] getValue() {
        return value;
    }

    public HbaseModel setValue(byte[] value) {
        this.value = value;
        return this;
    }
}
