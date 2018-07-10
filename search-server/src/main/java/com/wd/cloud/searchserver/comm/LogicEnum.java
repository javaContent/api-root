package com.wd.cloud.searchserver.comm;


/**
 * 定义检索逻辑，除1，2之外的值都表示must
 * 
 * @author pan
 * 
 */
public enum LogicEnum {

	/**
	 * 逻辑或
	 */
	should {
		@Override
		public String value() {
			return "1";
		}
	},
	/**
	 * 逻辑非
	 */
	must_not {
		@Override
		public String value() {
			return "2";
		}
	},
	/**
	 * 必须满足
	 */
	must{
		@Override
		public String value(){
			return "3";
		}
	},
	/**
	 * 最好满足，并且至少满足一个
	 */
	should_must{
		@Override
		public String value() {
			return "4";
		}
	}
	;
	public abstract String value();
}
