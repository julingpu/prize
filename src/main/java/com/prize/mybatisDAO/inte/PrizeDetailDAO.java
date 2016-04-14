package com.prize.mybatisDAO.inte;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.prize.entity.PrizeDetail;

public interface PrizeDetailDAO {

	public void addPrizeDetail(PrizeDetail prizeDetail);
	@Select("select * from prize_detail where prize_id = #{_parameter}")
	public PrizeDetail getPrizeDetailById(String prize_id);
	public List<PrizeDetail> getPrizeDetailByMap(HashMap map);
	public List<PrizeDetail> getPrizeDetailByMap1(HashMap map);
	public int deleteById(String id);
	@Select("select count(*) from prize_detail where term_name = #{_parameter}")
	public int getPrizeDetailCount(String term_name);
	@Select("select count(*) from prize_detail where term_name = #{term_name} and prize_type = #{prize_type}")
	public int getPrizeDetailCountByTermAndType(PrizeDetail PrizeDetail);
	@Update("update prize_detail set prize_price = #{prize_price},handle_time=#{handle_time},handle_result=#{handle_result} where prize_id = #{prize_id}")
	public int updatePrizeDetail(PrizeDetail prizeDetail);
}
