package com.prize.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.ibatis.session.SqlSession;

import com.prize.entity.PrizeDetail;
import com.prize.entity.TermInfo;
import com.prize.entity.TypeInfo;
import com.prize.mybatisDAO.util.DBUtil;

public class ExcelUtil {
	//设置标题字体
	 static jxl.write.WritableFont title_font = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 12,WritableFont.BOLD); 
	
	 //设置内容字体
	 static jxl.write.WritableFont content_font = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 9); 
	 
	//设置标题单元格样式
     static WritableCellFormat title_wc = new WritableCellFormat(title_font);
     
     //设置内容单元格样式
     static WritableCellFormat content_wc = new WritableCellFormat(content_font);
     
     
     static{
    	  
 		try {
 			// 设置水平方向居中
			title_wc.setAlignment(Alignment.CENTRE);
			//设置垂直方向居中
	 		title_wc.setVerticalAlignment(VerticalAlignment.CENTRE); 
	 		// 设置边框线
	 		title_wc.setBorder(Border.ALL, BorderLineStyle.THIN);
	 		
	 		// 设置水平方向居中
	 		content_wc.setAlignment(Alignment.CENTRE);
	 		//设置垂直方向居中
	 		content_wc.setVerticalAlignment(VerticalAlignment.CENTRE); 
	 		// 设置边框线
	 		content_wc.setBorder(Border.ALL, BorderLineStyle.THIN);
	 		//设置自动换行
	 		content_wc.setWrap(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
 		
 		
     }
   
   
	
	static final int columns_count = 8;
	//设置单元格宽度
	static final int width = 14;
	//设置标题单元格高度
	static final int title_height = 600;
	//设置二级标题单元格高度
	static final int small_title_height = 400;
	//设置属性单元格高度
	static final int column_height = 550;
	//操作到该excel的当前行数
	public static int current_row = 0;
	
	public static SqlSession sqlSession = DBUtil.getSqlSession();
	
	//设置列名
	public static String[] columns = {"序号","学院","班级","作品名称","获奖项目、等级","盖章单位或主办单位","证书上传作者姓名","奖励金额"};
	public static void createExcel(String year) {
		
		try {
			String path = System.getProperty("user.dir")+File.separator+"test.xls";
			System.out.println(path);
			File file = new File(path);
			WritableWorkbook workbook;
			workbook = Workbook.createWorkbook(file);
			WritableSheet sheet1 = workbook.createSheet("sheet1",0);
			
			Map<String,Object> map = new HashMap<String,Object>();
			PrizeDetail prizeDetail = new PrizeDetail();
		    
		     //设置单元格宽度
		     for(int i = 0 ; i < columns_count ; i++)
		    	 sheet1.setColumnView(i, width);
		     
			//1.设置标题
			String title = year+"年大学生课外科技获奖统计（2013年11月20日—2014年11月20日）";
			setTitle(sheet1,title);
			//设置第一行单元格高度
			 sheet1.setRowView(current_row++, title_height);
			 
			 //根据需要打印的年份获取相应的学期信息   因为一年对应多个学期 所以需要模糊查询 而模糊查询只能手动拼接 不能动态插入
			List<TermInfo> terms = sqlSession.selectList("com.prize.mybatisDAO.inte.TermInfoDAO.selectByYear",year);
			StringBuilder term_name = new StringBuilder();
			term_name.append("(");
			for (TermInfo termInfo : terms) {
				term_name.append("'");
				term_name.append(termInfo.getTerm_name());
				term_name.append("'");
				term_name.append(",");
			}
			term_name = new StringBuilder(term_name.substring(0, term_name.length()-1));
			term_name.append(")");
			
			//获取所有的获奖类型  根据获奖类型来生成excel
			List<TypeInfo> types = sqlSession.selectList("com.prize.mybatisDAO.inte.TypeInfoDAO.getAllTypeInfo");
			int i = 0;
			for (i = 0 ;i < types.size() ;i++) {
				
				//首先获取数据
				System.out.println("typeInfo:"+types.get(i));
				//查询    审核通过    并且是该获奖类型    并且是属于year的学期的    记录
				prizeDetail.setHandle_result("passed");
				prizeDetail.setPrize_type(types.get(i).getType_name());
				prizeDetail.setTerm_name(term_name.toString());
				map.put("prizeDetail", prizeDetail);
				List<PrizeDetail> prizeDetails = sqlSession.selectList("com.prize.mybatisDAO.inte.PrizeDetailDAO.getPrizeDetailByMap1",map);
				
				//2.设置二级标题     注意这里操作完一行之后要current_row++
				setSmallTitle(sheet1,(i+1)+"."+types.get(i).getType_name());
				sheet1.setRowView(current_row++, small_title_height);
				
				//3.设置列名   同样要current_row++
				setColumn(sheet1);
				sheet1.setRowView(current_row++, column_height);
				
				for (PrizeDetail prizeDetail2 : prizeDetails) {
					System.out.println("prizeDetail:"+prizeDetail2);
				}
				//4.设置列值
				setValue(sheet1, prizeDetails);
				
			}
			
			
			//设置发明专利情况
			setSmallTitle(sheet1,(++i)+"."+"本学院学生获得发明专利情况");
			sheet1.setRowView(current_row++, small_title_height);
			columns = new String[]{"序号","学院","班级","作品名称","专利号","申请日期","专利权人"};						
			setColumn(sheet1);
			sheet1.setRowView(current_row++, column_height);
			map.clear();
			prizeDetail = new PrizeDetail();
			prizeDetail.setHandle_result("passed");
			prizeDetail.setPrize_type("发明专利获奖申请");
			prizeDetail.setTerm_name(term_name.toString());
			map.put("prizeDetail", prizeDetail);
			List<PrizeDetail> prizeDetails = sqlSession.selectList("com.prize.mybatisDAO.inte.PrizeDetailDAO.getPrizeDetailByMap1",map);
			setValue(sheet1, prizeDetails);
			//设置本学院学生公开发表的论文或文学作品
			setSmallTitle(sheet1,(++i)+"."+"本学院学生公开发表的论文或文学作品");
			sheet1.setRowView(current_row++, small_title_height);
			columns = new String[]{"序号","学院","班级","论文题目","刊物名称（级别）","发表时间","证书上作者姓名"};						
			setColumn(sheet1);
			sheet1.setRowView(current_row++, column_height);
			map.clear();
			prizeDetail = new PrizeDetail();
			prizeDetail.setHandle_result("passed");
			prizeDetail.setPrize_type("公开发表的论文或文学作品");
			prizeDetail.setTerm_name(term_name.toString());
			map.put("prizeDetail", prizeDetail);
			prizeDetails = sqlSession.selectList("com.prize.mybatisDAO.inte.PrizeDetailDAO.getPrizeDetailByMap1",map);
			setValue(sheet1, prizeDetails);
			
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		
	}
	
	
	/**
	 * 设置sheet标题
	 * @param sheet
	 * @param title
	 */
	public static void setTitle(WritableSheet sheet,String title){
		try {
//			 // 设置字体为宋体  大小为12    加粗
//	        jxl.write.WritableFont wfont = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 12,WritableFont.BOLD); 
//	        
//	        //设置单元格样式
//	        WritableCellFormat title_wc = new WritableCellFormat(wfont);
//	        
//	        //设置 自动换行
//	        title_wc.setWrap(true);
//	        
//	        // 设置水平方向居中
//			title_wc.setAlignment(Alignment.CENTRE);   //这个是靠下对齐
//			
//			//设置垂直方向居中
//			title_wc.setVerticalAlignment(VerticalAlignment.CENTRE); 
//			
//	        // 设置边框线
//			title_wc.setBorder(Border.ALL, BorderLineStyle.THIN);
//			
//	        // 设置单元格的背景颜色
//			 title_wc.setBackground(jxl.format.Colour.RED); 
			
			//合并单元格  四个参数分别代表开始列数   开始行数  结束列数  结束行数
			sheet.mergeCells(0,current_row,columns_count-1, current_row);
			
			//新建单元格   四个参数分别代表单元格列数  单元格行数  单元格内容 单元格样式
			Label label = new Label(0,current_row,title,title_wc);
			
			sheet.addCell(label);	
				 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置二级标题
	 * @param sheet
	 * @param title
	 */
	public static void setSmallTitle(WritableSheet sheet,String title){
		try {
			 // 设置字体为宋体  大小为12    加粗
	        jxl.write.WritableFont small_title_font = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 9,WritableFont.BOLD); 
	        
	        //设置单元格样式
	        WritableCellFormat small_title_wc = new WritableCellFormat(small_title_font);
	        
	        // 设置居左
	        small_title_wc.setVerticalAlignment(VerticalAlignment.CENTRE); //设置居左
	        // 设置边框线
	        small_title_wc.setBorder(Border.ALL, BorderLineStyle.THIN);
	        // 设置单元格的背景颜色
			// title_wc.setBackground(jxl.format.Colour.RED); 
			sheet.mergeCells(0,current_row,columns_count-1, current_row);
			Label label = new Label(0,current_row,title,small_title_wc);
			sheet.addCell(label);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	
	/**
	 * 设置列名
	 * @param sheet
	 */
	public static void setColumn(WritableSheet sheet){
		try {
			 // 设置字体为宋体  大小为12    加粗
	       // jxl.write.WritableFont wfont = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 12,WritableFont.BOLD); 
	        
	        //设置单元格样式
	        WritableCellFormat column_wc = new WritableCellFormat();
	        
	        //设置 自动换行
	        column_wc.setWrap(true);
	        
	        // 设置水平方向居中
	        column_wc.setAlignment(Alignment.CENTRE);   //这个是靠下对齐
			
			//设置垂直方向居中
	        column_wc.setVerticalAlignment(VerticalAlignment.CENTRE); 
			
	        // 设置边框线
	        column_wc.setBorder(Border.ALL, BorderLineStyle.THIN);
			
			for (int i = 0; i < columns.length; i++) {
				Label label = new Label(i,current_row,columns[i],column_wc);
				sheet.addCell(label);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 设置列值
	 * @param sheet
	 * @param prizeDetails
	 */
	public static void setValue(WritableSheet sheet,List<PrizeDetail> prizeDetails){
		try {
			for (int i = 0;i < prizeDetails.size();i++) {
				Label label = new Label(0,current_row,(i+1)+"",content_wc);
				sheet.addCell(label);
				label = new Label(1,current_row,prizeDetails.get(i).getAcademy(),content_wc);
				sheet.addCell(label);
				label = new Label(2,current_row,prizeDetails.get(i).getClazz(),content_wc);
				sheet.addCell(label);
				label = new Label(3,current_row,prizeDetails.get(i).getWorks_name(),content_wc);
				sheet.addCell(label);
				label = new Label(4,current_row,prizeDetails.get(i).getPrize_name(),content_wc);
				sheet.addCell(label);
				label = new Label(5,current_row,prizeDetails.get(i).getHost_name(),content_wc);
				sheet.addCell(label);
				label = new Label(6,current_row,prizeDetails.get(i).getStudent_name(),content_wc);
				sheet.addCell(label);
				label = new Label(7,current_row,prizeDetails.get(i).getPrize_price(),content_wc);
				sheet.addCell(label);
				current_row++;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
}
