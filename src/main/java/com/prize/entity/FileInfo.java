package com.prize.entity;

/**
 * 文件信息
 * @author ahuang
 *
 */
public class FileInfo {
	public String file_id;//文件id
	public String file_name;//文件名称
	public String file_path;//文件路径
	public String file_prize_id;//文件所属的奖励
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getFile_prize_id() {
		return file_prize_id;
	}
	public void setFile_prize_id(String file_prize_id) {
		this.file_prize_id = file_prize_id;
	}
	@Override
	public String toString() {
		return "FileInfo [file_id=" + file_id + ", file_name=" + file_name
				+ ", file_path=" + file_path + ", file_prize_id="
				+ file_prize_id + "]";
	}
	
	
}
