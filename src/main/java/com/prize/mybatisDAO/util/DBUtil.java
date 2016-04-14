package com.prize.mybatisDAO.util;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.prize.entity.PrizeDetail;

public class DBUtil {
	
	
	private static SqlSession sqlSession = null;
	static{
		
		//通过配置文件获取数据库连接信息
				Reader reader = null;
				try {
					reader = Resources.getResourceAsReader("Configuration.xml");
				} catch (IOException e) {
					e.printStackTrace();
				}
				//通过配置文件构建一个SqlSessionFactory
				SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
				//通过SqlSessionFactory打开一个数据库会话
				sqlSession = sessionFactory.openSession();
	}
	public static SqlSession getSqlSession() {
		return sqlSession;
	}
	
}
