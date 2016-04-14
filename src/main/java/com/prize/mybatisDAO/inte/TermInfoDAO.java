package com.prize.mybatisDAO.inte;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.prize.entity.TermInfo;

public interface TermInfoDAO {
	@Insert("insert into term_info values(#{term_id},#{term_name},#{term_begin_time},#{term_end_time})")
	public int addTermInfo(TermInfo termInfo);
	@Select("select term_id,term_name,term_begin_time,term_end_time from term_info")
	public List<TermInfo> getAllTermInfo();
	@Select("select count(*) from term_info")
	public int getTermCount();
	@Select("select term_id,term_name,term_begin_time,term_end_time from term_info where term_id = #{_parameter}")
	public TermInfo getTermInfoById(String id);
	
	public int insert(TermInfo termInfo);
	
	public int insertSelective(TermInfo termInfo);
	
	public int update(TermInfo termInfo);
	
	public int updateSelective(TermInfo termInfo);
	
	/**
	 * 删除单条记录
	 * @param term_id
	 */
	public int deleteOne(String term_id);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public int deleteBatch(List<String> ids);
	
	
	/**
	 * 查询单条记录
	 * @param id
	 * @return
	 */
	public TermInfo selectOne(String id);
	
	/**
	 * 批量查询
	 * @param map
	 * @return
	 */
	public List<TermInfo> select(Map map);
	
	@Select("select term_id,term_name,term_begin_time,term_end_time from term_info where term_name like '%${_parameter}%'")
	public List<TermInfo> selectByYear(String year);
}
