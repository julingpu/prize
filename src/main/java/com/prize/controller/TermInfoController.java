package com.prize.controller;

import com.prize.entity.TermInfo;
import com.prize.mybatisDAO.inte.TermInfoDAO;
import com.prize.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理学期信息
 * @author ahuang
 *
 */
public class TermInfoController {
	@Autowired
	TermInfoDAO termInfoDAO;
	
	/**
	 * 添加学期信息 成功返回true 失败返回false
	 * @param termInfo
	 * @return
	 */
	@RequestMapping("addTermInfo")
	@ResponseBody
	public boolean addTermInfo(TermInfo termInfo){
		Resource r = null;
		Resource r1 = null;
		Resource[] re = new Resource[]{r,r1};
		termInfo.term_begin_time = "1";
		termInfo.term_end_time = "1";
		String id  = UUIDUtil.getUUIDString();
		termInfo.term_id = id ;
		termInfo.term_name = "1";
		termInfoDAO.addTermInfo(termInfo);
		//判断学期信息是否添加成功  
		if(termInfoDAO.getTermInfoById(id)!=null)
			return true;
		else
			return false;
	}
	
	
	/**
	 * 获取所有的学期信息和奖项信息
	 * @return
	 */
	@RequestMapping("getAllTermInfo")
	@ResponseBody
	public Map getAllTermInfo(){
		List<TermInfo> termInfoList = termInfoDAO.getAllTermInfo();
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("termInfoList",termInfoList);
		return returnMap;
	}
	@RequestMapping("delete")
	@ResponseBody
	public int delete(){
		String id = "f9347f30c7a547ce9a0f254569ef4177";
		int count = termInfoDAO.deleteOne(id);
		return count;
		
	}
	
}
