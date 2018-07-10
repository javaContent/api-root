package com.wd.cloud.searchserver.entity;


/**
 * 收录详情
 * @author Administrator
 *
 */
public class ShouluDetail implements Comparable<ShouluDetail>{
	
	/**收录数据库*/
	private String db;
	
	/**收录年份*/
	private String year;
	
	/**学科**/
	private String subject;
	
	/**影响因子*/
	private Double imfact;
	
	/**分区*/
	private Integer partition;

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Double getImfact() {
		return imfact;
	}

	public void setImfact(Double imfact) {
		this.imfact = imfact;
	}

	public Integer getPartition() {
		return partition;
	}

	public void setPartition(Integer partition) {
		this.partition = partition;
	}

	@Override
	public int compareTo(ShouluDetail o) {
		if(Integer.parseInt(o.getYear()) > Integer.parseInt(this.getYear())){
			return -1;
		}
		return 1;
	}

}
