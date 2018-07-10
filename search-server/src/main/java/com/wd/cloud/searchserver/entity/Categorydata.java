package com.wd.cloud.searchserver.entity;

/**
 * 云空间期刊接口数据
 * @author 杨帅菲
 *
 */
public class Categorydata {
	public int id;
	
	public String title;
	
	public int rank;
	
	public String categorySystem;
	
	public String category;
	
	public String year;
	
	public float value;
	
	public String order;//排序
	
	public String percent;//百分比

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getCategorySystem() {
		return categorySystem;
	}

	public void setCategorySystem(String categorySystem) {
		this.categorySystem = categorySystem;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}
	

}
