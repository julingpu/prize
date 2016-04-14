package com.prize.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.prize.entity.PrizeDetail;
import com.prize.entity.TypeInfo;
import com.prize.util.TimeUtil;
import com.prize.util.UUIDUtil;

public class PrizeDetailDAO {
	/**
	 * 添加奖励详细
	 * @param prizeDetail
	 * @return  如果返回的String不是null代表插入成功   如果是null代表插入失败
	 */
	public String addPrize(PrizeDetail prizeDetail){
		String sql = "insert into prize_detail(prize_id,academy,clazz,works_name,prize_name,host_name,student_name,student_bank_card,submit_time,handle_result) values (?,?,?,?,?,?,?,?,?,?)";
		String id = UUIDUtil.getUUIDString();
		String submit_time = TimeUtil.getStringTime();
		System.out.println("submit_time"+submit_time);
		System.out.println("生成的uuid:"+id);
		
		try {
			Connection conn = DBUtil.getConnection();
			PreparedStatement psst = DBUtil.getPreparedStatement(sql);
			psst.setString(1, id);
			psst.setString(2, prizeDetail.academy);
			psst.setString(3, prizeDetail.clazz);
			psst.setString(4, prizeDetail.works_name);
			psst.setString(5, prizeDetail.prize_name);
			psst.setString(6, prizeDetail.host_name);
			psst.setString(7, prizeDetail.student_name);
			psst.setString(8, prizeDetail.student_bank_card);
			psst.setString(9, submit_time);
			psst.setString(10, "未审核");

			if(psst.executeUpdate()!=0){
				System.out.println("奖励详细插入成功");
				return id;
			}
			else{
				System.out.println("奖励详细插入失败");
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
		return null;
	}
	
	public static List<TypeInfo> getAllTypeInfo(){
		Connection conn;
		List<TypeInfo> lists = new ArrayList<TypeInfo>();
		try {
			conn = DBUtil.getConnection();
			PreparedStatement psst = conn.prepareStatement("select TYPE_ID,type_name from type_info");
			ResultSet rs = psst.executeQuery();
			System.out.println(rs.getMetaData().getColumnName(1));
			while(rs.next()){
				String type_id = rs.getString("type_id");
				String type_name = rs.getString("type_name");
				TypeInfo t = new TypeInfo();
				t.type_id = type_id;
				t.type_name = type_name;
				lists.add(t);
			}
			return lists;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lists;
		
	}
	public static void main(String[] args) {
		List<TypeInfo> lists = getAllTypeInfo();
		for (TypeInfo typeInfo : lists) {
			System.out.println("TypeInfo:"+typeInfo);
		}
	}
}
