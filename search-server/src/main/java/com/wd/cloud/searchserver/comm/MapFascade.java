package com.wd.cloud.searchserver.comm;

import java.util.HashMap;

public class MapFascade<V> extends HashMap<String, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MapFascade() {
		super(15);
	}

	@Override
	public V put(String key, V value) {
		String[] keyArr = key.split(";");
		if (keyArr.length > 1) {
			for (String k : keyArr) {
				super.put(k, value);
			}
			return value;
		} else {
            return super.put(key, value);
        }
	}
}
