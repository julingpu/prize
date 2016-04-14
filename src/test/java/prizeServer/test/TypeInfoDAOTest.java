package prizeServer.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.prize.entity.TypeInfo;
import com.prize.mybatisDAO.inte.TypeInfoDAO;
import com.prize.mybatisDAO.util.DBUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mybatis.xml")
public class TypeInfoDAOTest extends TestCase{
	@Autowired
	TypeInfoDAO typeInfoDAO;
	
	
	List<TypeInfo> typeInfo_list = new ArrayList<TypeInfo>();
	
	@Test
	public void testAddTypeInfoByMybatis(){
		TypeInfo typeInfo = new TypeInfo();
		//typeInfo.type_id = "111";
		typeInfo.type_name = "bbb";
		//int count = DBUtil.getSqlSession().insert("com.prize.mybatisDAO.inte.TypeInfoDAO.addTypeInfo",typeInfo);
		//System.out.println("count:"+count);
		//DBUtil.getSqlSession().commit();
		TypeInfoDAO typeInfoDAO = DBUtil.getSqlSession().getMapper(TypeInfoDAO.class);
		System.out.println(typeInfoDAO.getClass().getName());
		Field[] f = typeInfoDAO.getClass().getDeclaredFields();
		for (Field field : f) {
			System.out.println(field.getName());
		}
		Method[] m = typeInfoDAO.getClass().getMethods();
		for (Method method : m) {
			System.out.println(method.getName());
		}
		//count = typeInfoDAO.addTypeInfo(typeInfo);
	}
	
	//@Test
	public void testSelectAllTypeInfo(){
		
		typeInfo_list = DBUtil.getSqlSession().selectList("com.prize.mybatisDAO.inte.TypeInfoDAO.getAllTypeInfo");
		for (TypeInfo typeInfo : typeInfo_list) {
			System.out.println("typeInfo:"+typeInfo);
		}
	}
	//@Test
	public void testSelectAllTypeInfo1(){
		System.out.println(DBUtil.getSqlSession().getClass());
		//org.apache.ibatis.session.defaults.DefaultSqlSession
		typeInfo_list = DBUtil.getSqlSession().selectList("com.prize.mybatisDAO.inte.TypeInfoDAO.getTypeInfo");
		for (TypeInfo typeInfo : typeInfo_list) {
			System.out.println("typeInfo:"+typeInfo);
		}
	}
	
	//@Test
	public void getTypeInfo(){
		typeInfoDAO.getTypeInfo();
	}
}
