package prizeServer.test;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.prize.dao.PrizeDetailDAO;
import com.prize.entity.PrizeDetail;
import com.prize.entity.TermInfo;
import com.prize.mybatisDAO.util.DBUtil;
import com.prize.util.TimeUtil;
import com.prize.util.UUIDUtil;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mybatis.xml")
public class PrizeDetailDAOTest {

	//@Test
	public void testAddPrizeDetail() {
		
		String id = UUIDUtil.getUUIDString();
		String submit_time = TimeUtil.getStringTime();
		System.out.println("submit_time"+submit_time);
		System.out.println("生成的uuid:"+id);
		PrizeDetail prizeDetail = new PrizeDetail();
		prizeDetail.prize_id = id;
		prizeDetail.academy = "1";
		prizeDetail.clazz = "1";
		prizeDetail.host_name = "1";
		prizeDetail.prize_name = "1";
		prizeDetail.student_bank_card = "1";
		prizeDetail.student_name = "1";
		prizeDetail.term_name = "1";
		prizeDetail.works_name = "1";
		prizeDetail.submit_time = submit_time;
		prizeDetail.handle_result = "未审核";
		DBUtil.getSqlSession().insert("com.prize.mybatisDAO.inte.PrizeDetailDAO.addPrizeDetail",prizeDetail);
		int count = (Integer)DBUtil.getSqlSession().selectOne("com.prize.mybatisDAO.inte.PrizeDetailDAO.getPrizeDetailCount","2");
		System.out.println(id);
		System.out.println("count:"+count);
		
	}

	
	//@Test
	public void testInsert(){
		TermInfo termInfo = new TermInfo();
		termInfo.term_begin_time = "1";
		termInfo.term_end_time = "2";
		//termInfo.term_id = "111";
		termInfo.term_name = "1";
		DBUtil.getSqlSession().insert("com.prize.mybatisDAO.inte.TermInfoDAO.insert",termInfo);
		System.out.println(termInfo);
		 DBUtil.getSqlSession().commit();
	}
	
	//@Test
	public void testInsertSelective(){
		TermInfo termInfo = new TermInfo();
		DBUtil.getSqlSession().insert("com.prize.mybatisDAO.inte.TermInfoDAO.insertSelective",termInfo);
		System.out.println(termInfo);
		 DBUtil.getSqlSession().commit();
	}
	
	
	//@Test
	public void testAddTermInfo() {
		TermInfo termInfo = new TermInfo();
		termInfo.term_begin_time = "1";
		termInfo.term_end_time = "1";
		termInfo.term_id = "1111";
		termInfo.term_name = "1";
		DBUtil.getSqlSession().insert("com.prize.mybatisDAO.inte.TermInfoDAO.addTermInfo;",termInfo);
	}
    //@Test
	public void testUpdate(){
		String id = "0a424e0522ed48ccb8a162b0a80726e5";
		TermInfo termInfo = DBUtil.getSqlSession().selectOne("com.prize.mybatisDAO.inte.TermInfoDAO.getTermInfoById",id);
		System.out.println("update前："+termInfo);
		termInfo.term_name = "123";
		DBUtil.getSqlSession().update("com.prize.mybatisDAO.inte.TermInfoDAO.update",termInfo);
		 DBUtil.getSqlSession().commit();
		termInfo = DBUtil.getSqlSession().selectOne("com.prize.mybatisDAO.inte.TermInfoDAO.getTermInfoById",id);
		
		System.out.println("update后："+termInfo);
		
	}
    //@Test
    public void testUpdateSelective(){
    	
    	TermInfo termInfo = new TermInfo();
    	termInfo.term_id = "9413d7bd01684e6893404870501d3f3f";
    	termInfo.term_begin_time = "2";
    	termInfo.term_end_time = "2";
		DBUtil.getSqlSession().update("com.prize.mybatisDAO.inte.TermInfoDAO.updateSelective",termInfo);
		 DBUtil.getSqlSession().commit();
		//termInfo = DBUtil.getSqlSession().selectOne("com.prize.mybatisDAO.inte.TermInfoDAO.getTermInfoById",id);
    }
    
    
    @Test
    public void testDeleteOne(){
    	String id = "2d1b4d634b584d4a8d6f97d57fa547fe";
    	int result = DBUtil.getSqlSession().delete("com.prize.mybatisDAO.inte.TermInfoDAO.deleteOne",id);

		DBUtil.getSqlSession().commit();
		System.out.println(result);
    }
    
   // @Test
    public void testDeleteBatch(){
    	List<String> ids = new ArrayList<String>();
    	ids.add("f32703d4c7124d94aaca322891b389c4");
    	ids.add("f267202ee2fd103395445afeeb9d53c5");
    	ids.add("c385ec77c80b4b1ca72e1d811a63c998");
    	ids.add("bc4a16f4af9f4fa7b93f8b4e96b69bdd");
    	
    	int count  = DBUtil.getSqlSession().delete("com.prize.mybatisDAO.inte.TermInfoDAO.deleteBatch",ids);
		 DBUtil.getSqlSession().commit();
		 System.out.println("count:"+count);
    }
    
    //@Test
    public void testSelectOne(){
    	String id = "9413d7bd01684e6893404870501d3f3f";
    	TermInfo termInfo = DBUtil.getSqlSession().selectOne("com.prize.mybatisDAO.inte.TermInfoDAO.selectOne",id);
    	System.out.println(termInfo);
    	
    }
    
    
    
    
    //@Test
    public void testSelect(){
    	Map<String,Object> map = new HashMap<String,Object>();
    	TermInfo term_info = new TermInfo();
    	//term_info.term_id = "9413d7bd01684e6893404870501d3f3f";
    	term_info.term_name = "2";
    	map.put("term_info", term_info);
    	List<TermInfo> list = DBUtil.getSqlSession().selectList("com.prize.mybatisDAO.inte.TermInfoDAO.select",map);
    	for (TermInfo termInfo : list) {
			System.out.println(termInfo);
		}
    }
    
    //@Test
    public void testTerm() throws ParseException{
    	Map map =new HashMap<String,Object>();
    	
    	List<TermInfo> terms = DBUtil.getSqlSession().selectList("com.prize.mybatisDAO.inte.TermInfoDAO.select",map);
    	for (TermInfo termInfo : terms) {
			if(TimeUtil.checkTerm(termInfo.term_begin_time,termInfo.term_end_time)){
				System.out.println("当前学期为:"+termInfo.term_name);
				break;
			}
			System.out.println("1");
		}
    	
    }
    
}
