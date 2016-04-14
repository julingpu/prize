package com.prize.mybatisDAO.inte;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.prize.entity.TypeInfo;
public interface TypeInfoDAO {
	//@Select("select type_id,type_name from type_info")
	public List<TypeInfo> getAllTypeInfo();
	@Select("select type_id,type_name from type_info limit 0,2")
	public TypeInfo getTypeInfo();
	//@Insert("insert into type_info(type_id,type_name) values(#{type_id},#{type_name})")
	public int addTypeInfo(TypeInfo typeInfo);
	@Delete("delete from type_info where type_id = #{_parameter}")
	public int deleteTypeInfo(String type_id);
	
}
