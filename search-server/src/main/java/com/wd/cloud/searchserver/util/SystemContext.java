package com.wd.cloud.searchserver.util;

import java.io.Serializable;

public class SystemContext implements Serializable {
	private static final long serialVersionUID = 6601174286299001599L;
	private static ThreadLocal<Integer> OFFSET = new ThreadLocal<Integer>();
	private static ThreadLocal<Integer> PAGE_SIZE = new ThreadLocal<Integer>();
	private static ThreadLocal<Integer> FACAT_SIZE=new ThreadLocal<Integer>();

	public static void setPageSize(int pageSize) {
		PAGE_SIZE.set(pageSize);
	}
	
	public static void setFacatSize(int facatSize){
		FACAT_SIZE.set(facatSize);
	}
	
	public static Integer getFacatSize(){
		return FACAT_SIZE.get();
	}
	
	public static void removeFacatSize(){
		FACAT_SIZE.remove();
	}

	/**
	 * 获取每页可显示记录数,如果当前ThreadLocal中不存在,则返回Integer.MAX_VALUE
	 * 
	 * @return
	 */
	public static int getPageSize() {
		Integer ps = PAGE_SIZE.get();
		if (ps == null || ps < 0) {
			return 20;
		}
		return ps;
	}

	public static void removePageSize() {
		PAGE_SIZE.remove();
	}

	public static void setOffset(int offset) {
		OFFSET.set(offset);
	}

	/**
	 * 获取当前所处页数,如果当前ThreadLocal中不存在,则返回0
	 * 
	 * @return
	 */
	public static int getOffset() {
		Integer offset = OFFSET.get();
		if (offset == null || offset < 0) {
			return 0;
		}
		return offset;
	}

	public static void removeOffset() {
		OFFSET.remove();
	}
}
