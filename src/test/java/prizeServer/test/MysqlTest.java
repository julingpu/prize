package prizeServer.test;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import com.prize.dao.*;
import com.prize.entity.PrizeDetail;

public class MysqlTest {
	@Test
	public void test(){
		long beginTime = System.currentTimeMillis();
		for(int i = 0 ; i <1000 ; i++)
		{
			try {
				DBUtil.getConnection();
				DBUtil.closeConn();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		long endTime = System.currentTimeMillis();
		System.out.println("花费时间："+(endTime-beginTime));
		
		
		
		
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader("Configuration.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//通过配置文件构建一个SqlSessionFactory
		beginTime = System.currentTimeMillis();
		//for(int i = 0 ; i <100 ; i++)
			new SqlSessionFactoryBuilder().build(reader).openSession();
			

		//通过SqlSessionFactory打开一个数据库会话
		endTime = System.currentTimeMillis();
		System.out.println("mybatis花费时间："+(endTime-beginTime));
	}
	
	
	

	
	
	//@Test
	public void testAddPrizeDetail(){
		PrizeDetail prizeDetail = new PrizeDetail();
		prizeDetail.academy = "11s";
		prizeDetail.clazz = "1";
		prizeDetail.host_name = "1";
		prizeDetail.prize_name = "1";
		prizeDetail.student_bank_card = "1";
		prizeDetail.student_name = "1";
		prizeDetail.term_name = "1";
		prizeDetail.works_name = "1";
		String id = new PrizeDetailDAO().addPrize(prizeDetail);
		System.out.println(id);
		
	}
}
