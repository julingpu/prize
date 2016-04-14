package com.prize.entity;

import java.util.List;


/**
 * 获奖详细
 * @author ahuang
 *
 */
public class PrizeDetail {
	 public String prize_id;  	//信息id
	 public String prize_type;	//获奖类型
	 public String academy;	//学院
	 public String clazz;		//班级
	 public String works_name;	//作品名称  如果是专利 对应专利名称  	  如果是论文 对应论文题目
	 public String prize_name;  //获奖项目  如果是专利  对应专利号      	  如果是论文 对应刊物名称
	 public String host_name;	//主办单位   如果是专利  对应申请年月日	  如果是论文 对应发表日期
	 public String student_name;//学生名称
	 public String student_bank_card;//银行卡号
	 public String prize_price; //奖励金额
	 public String submit_time; //提交时间
	 public String term_name; 	//所属学期
	 public String handle_time;	//处理时间
	 public String handle_result;//处理结果
	 
	 public  List<String> file_path ;
	@Override
	public String toString() {
		return "PrizeDetail [prize_id=" + prize_id + ", prize_type="
				+ prize_type + ", academy=" + academy + ", clazz=" + clazz
				+ ", works_name=" + works_name + ", prize_name=" + prize_name
				+ ", host_name=" + host_name + ", student_name=" + student_name
				+ ", student_bank_card=" + student_bank_card + ", prize_price="
				+ prize_price + ", submit_time=" + submit_time + ", term_name="
				+ term_name + ", handle_time=" + handle_time
				+ ", handle_result=" + handle_result + "]";
	}
	public void setPrize_id(String prize_id) {
		this.prize_id = prize_id;
	}
	public void setPrize_type(String prize_type) {
		this.prize_type = prize_type;
	}
	public void setAcademy(String academy) {
		this.academy = academy;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public void setWorks_name(String works_name) {
		this.works_name = works_name;
	}
	public void setPrize_name(String prize_name) {
		this.prize_name = prize_name;
	}
	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public void setStudent_bank_card(String student_bank_card) {
		this.student_bank_card = student_bank_card;
	}
	public void setPrize_price(String prize_price) {
		this.prize_price = prize_price;
	}
	public void setSubmit_time(String submit_time) {
		this.submit_time = submit_time;
	}
	public void setTerm_name(String term_name) {
		this.term_name = term_name;
	}
	public void setHandle_time(String handle_time) {
		this.handle_time = handle_time;
	}
	public void setHandle_result(String handle_result) {
		this.handle_result = handle_result;
	}
	public String getPrize_id() {
		return prize_id;
	}
	public String getPrize_type() {
		return prize_type;
	}
	public String getAcademy() {
		return academy;
	}
	public String getClazz() {
		return clazz;
	}
	public String getWorks_name() {
		return works_name;
	}
	public String getPrize_name() {
		return prize_name;
	}
	public String getHost_name() {
		return host_name;
	}
	public String getStudent_name() {
		return student_name;
	}
	public String getStudent_bank_card() {
		return student_bank_card;
	}
	public String getPrize_price() {
		return prize_price;
	}
	public String getSubmit_time() {
		return submit_time;
	}
	public String getTerm_name() {
		return term_name;
	}
	public String getHandle_time() {
		return handle_time;
	}
	public String getHandle_result() {
		return handle_result;
	}
	
	 
	
}
