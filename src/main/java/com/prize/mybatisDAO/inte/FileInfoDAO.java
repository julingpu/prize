package com.prize.mybatisDAO.inte;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.prize.entity.FileInfo;

public interface FileInfoDAO {
	@Insert("insert into file_info(file_id,file_name,file_path,file_prize_id) values(#{file_id},#{file_name},#{file_path},#{file_prize_id})")
	public int addFileInfo(FileInfo fileInfo);
	@Select("select file_path from file_info where file_prize_id = #{_parameter}")
	public List<String> getFilesByPrizeId(String prize_id);
}
