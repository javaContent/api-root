package com.wd.cloud.reportanalysis.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="school")
public class School {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="province")
	private String province;
	
	@Column(name="city")
	private String city;
	
	@Column(name="level")
	private String level;
	
	@Column(name="dept")
	private String dept;
	
	@Column(name="remark")
	private String remark;
	
	/**学校的应为名称*/
	@Column(name="index_name")
	private String indexName;
	
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="code")
	private String code;
	
	@Column(name="scid")
	private Integer scid;
	
	/**对应的ESI机构*/
	@Column(name="esi_inst")
	private String esiInst;
	
	/**对应的Incites机构*/
	@Column(name="incites_inst")
	private String incitesInst;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getScid() {
		return scid;
	}

	public void setScid(Integer scid) {
		this.scid = scid;
	}

	public String getEsiInst() {
		return esiInst;
	}

	public void setEsiInst(String esiInst) {
		this.esiInst = esiInst;
	}

	public String getIncitesInst() {
		return incitesInst;
	}

	public void setIncitesInst(String incitesInst) {
		this.incitesInst = incitesInst;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
