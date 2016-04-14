package com.prize.entity;


/**
 * 学期信息
 * @author ahuang
 *
 */
public class TermInfo {
	public String term_id;//学期id
	public String term_name;//学期名称
	public String term_begin_time;//学期开始时间
	public String term_end_time;//学期结束时间
	@Override
	public String toString() {
		return "TermInfo [term_id=" + term_id + ", term_name=" + term_name
				+ ", term_begin_time=" + term_begin_time + ", term_end_time="
				+ term_end_time + "]";
	}
	public String getTerm_id() {
		return term_id;
	}
	public void setTerm_id(String term_id) {
		this.term_id = term_id;
	}
	public String getTerm_name() {
		return term_name;
	}
	public void setTerm_name(String term_name) {
		this.term_name = term_name;
	}
	public String getTerm_begin_time() {
		return term_begin_time;
	}
	public void setTerm_begin_time(String term_begin_time) {
		this.term_begin_time = term_begin_time;
	}
	public String getTerm_end_time() {
		return term_end_time;
	}
	public void setTerm_end_time(String term_end_time) {
		this.term_end_time = term_end_time;
	}
	
}
