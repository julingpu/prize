package com.prize.entity;

/**
 * 获奖类型
 * @author ahuang
 *
 */
public class TypeInfo {

	public String type_id;
	public String type_name;  //类型名称
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	@Override
	public String toString() {
		return "TypeInfo [type_id=" + type_id + ", type_name=" + type_name
				+ "]";
	}
	
	
}
