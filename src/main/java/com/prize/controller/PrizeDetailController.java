package com.prize.controller;

import com.prize.entity.FileInfo;
import com.prize.entity.PrizeDetail;
import com.prize.entity.TermInfo;
import com.prize.entity.TypeInfo;
import com.prize.mybatisDAO.inte.FileInfoDAO;
import com.prize.mybatisDAO.inte.PrizeDetailDAO;
import com.prize.mybatisDAO.inte.TermInfoDAO;
import com.prize.mybatisDAO.inte.TypeInfoDAO;
import com.prize.util.ExcelUtil;
import com.prize.util.TimeUtil;
import com.prize.util.UUIDUtil;
import mvc.fileUpload.MultipartFile;
import mvc.view.HtmlView;
import mvc.view.JsonView;
import mvc.view.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import mvc.annotation.*;

//如果需要把controller层中的bean交给spring管理  必须添加controller获取其他相关的注解
@Controller
public class PrizeDetailController {
	//使用spring的自动注入 从配置文件（config.properties）中注入pageSize的值
	@Value("${pageSize}")
	int pageSize;
	//使用autowired自动注入dao层组件
	@Autowired
	PrizeDetailDAO prizeDetailDAO ;
	//dao层中学期数据组件
	@Autowired
	TermInfoDAO termInfoDAO;
	//dao层中文件数据组件
	@Autowired 
	FileInfoDAO fileInfoDAO;
	//dao层中奖项类型数据组件
	@Autowired 
	TypeInfoDAO typeInfoDAO;

	@RequestMapping(type = "get", url = "/getAll")
	public ModelAndView getAllPrizeDetail(HttpServletRequest request){
		//Object controllerInstance = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("prizeDetailController1");
		//System.out.print("controllerInstance:"+controllerInstance);
		HashMap<String,Object> map = new HashMap<String,Object>();//pageSize
		System.out.println("pageSize:"+pageSize);
		System.out.println("prizeDetailDAO:"+prizeDetailDAO);
		map.put("value", prizeDetailDAO.getPrizeDetailByMap(map));
		JsonView jsonView = new JsonView(map);
		return jsonView;
	}
	//deleteById
	@RequestMapping(type = "get", url = "/deleteById")
	public ModelAndView deleteById(String id){
		HashMap<String,Object> map = new HashMap<String,Object>();
		//result代表更新的记录条数
		int result = prizeDetailDAO.deleteById(id);
		if(result==1)
			return new JsonView(true);
		else
			return new JsonView(false);
	}
	/**
	 * 学生提交获奖信息
	 * @param prizeDetail
	 * @param file
	 * @param request
	 * @return
	 */
	//addPrizeDetail
	@RequestMapping(type = "post", url = "/PrizeServer/addPrizeDetail")
	public ModelAndView addPrizeDetail(PrizeDetail prizeDetail, MultipartFile[] file, HttpServletRequest request){
		System.out.println("request.getParameter(academy)"+request.getParameter("academy"));
		String id = UUIDUtil.getUUIDString();
		String submit_time = TimeUtil.getStringTime();
		System.out.println("submit_time"+submit_time);
		System.out.println("生成的uuid:"+id);

		prizeDetail.prize_id = id;
		
		System.out.println("prizedetail:"+prizeDetail);
		
		List<TermInfo> terms = termInfoDAO.getAllTermInfo();
    	for (TermInfo termInfo : terms) {
			try {
				if(TimeUtil.checkTerm(termInfo.term_begin_time,termInfo.term_end_time)){
					System.out.println("当前学期为:"+termInfo.term_name);
					prizeDetail.term_name =termInfo.term_name;
					break;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("数据库学期时间错误");
				return new HtmlView("redirect:/files/app/index.html?result=failed");
			}
		}
		if(prizeDetail.term_name==null||"".equals(prizeDetail.term_name))
			return new HtmlView("redirect:/files/app/index.html?result=failed");
		//prizeDetail.works_name = "1";
		prizeDetail.submit_time = submit_time;
		//设置处理状态为审核中
		prizeDetail.handle_result = "waiting";
		prizeDetailDAO.addPrizeDetail(prizeDetail);
		
		
		for (MultipartFile multipartFile : file) {
			if(multipartFile.getFileSize()>0){
				try {
					Integer random_number = new Random().nextInt(1000);
					System.out.println("random_number:"+random_number);
					//如果文件夹不存在 新建images文件夹
					File directory =new File(request.getSession().getServletContext().getRealPath("/")+"images");
					if  (!directory .exists()  && !directory .isDirectory())
					{
						directory .mkdir();
					}
					//文件命名采用随机数加时间的方式  不使用源文件的名称是因为可能含有中文
					String time = TimeUtil.getStringTime1();
					String path = request.getSession().getServletContext().getRealPath("/")+"images"+File.separator+random_number+"_"+time+".jpg";
					FileOutputStream out = new FileOutputStream(new File(path));
					out.write(multipartFile.getBytes());
					FileInfo info = new FileInfo();
					info.file_id = UUIDUtil.getUUIDString();
					info.file_name = multipartFile.getFileItem().getName();
					info.file_path = random_number+"_"+time+".jpg";
					info.file_prize_id = id;
					System.out.println("info:"+info);
					if(fileInfoDAO.addFileInfo(info)!=1){
						System.out.println("文件记录插入错误");
					}
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(prizeDetailDAO.getPrizeDetailById(id)!=null){
			return new HtmlView("/files/app/checkInfo.html?result=success");
		}
		else
			return new HtmlView("redirect:/files/app/index.html?result=failed");
	}
	
	/**
	 * 根据当前时间所在学期获取获奖信息  针对学生页面 根据当前时间获取学期
	 * @param prizeDetail
	 * @param currentPage
	 * @return
	 */
	//getPrizeByTerm
	@RequestMapping(type = "get", url = "/PrizeServer/getPrizeByTerm")
	public ModelAndView getPrizeByTerm(PrizeDetail prizeDetail,String currentPage){
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<TermInfo> termInfos = termInfoDAO.getAllTermInfo();
		for (TermInfo termInfo : termInfos) {
			try {
				if(TimeUtil.checkTerm(termInfo.term_begin_time, termInfo.term_end_time)){
					prizeDetail.term_name = termInfo.term_name;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println("数据库学期时间错误");
				e.printStackTrace();
			}
		}
		prizeDetail.prize_type = null;
		//prizeDetail.term_name = TimeUtil.checkTerm(begin, end);
		map.put("prizeDetail", prizeDetail);
		map.put("begin", new Integer(pageSize)*(new Integer(currentPage)-1));
		map.put("length",pageSize);
		List<PrizeDetail> prizeList = prizeDetailDAO.getPrizeDetailByMap(map);
		for (PrizeDetail prizeDetail2 : prizeList) {
			prizeDetail2.file_path = fileInfoDAO.getFilesByPrizeId(prizeDetail2.prize_id);
		}
		int prizeCount = prizeDetailDAO.getPrizeDetailCount(prizeDetail.term_name);
		int totalPage = 0;
		if(prizeCount%pageSize==0)
			totalPage = prizeCount/pageSize;
		else
			totalPage = prizeCount/pageSize+1;
		map.clear();
		map.put("prizeList", prizeList);
		map.put("totalPage", totalPage);
		map.put("currentPage",currentPage);
		return new JsonView(map);
	}
	
	
	/**
	 * 获取所有的学期信息和奖项类型信息
	 * @return
	 */
	//getAllTermInfoAndPrizeInfo
	@RequestMapping(type = "get", url = "/PrizeServer/getAllTermInfoAndPrizeInfo")
	public ModelAndView getAllTermInfo(){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		List<TermInfo> termInfoList = termInfoDAO.getAllTermInfo();
		List<TypeInfo> typeInfoList = typeInfoDAO.getAllTypeInfo();
		returnMap.put("termInfoList",termInfoList);
		returnMap.put("typeInfoList", typeInfoList);
		return new JsonView(returnMap);
	}
	
	
	/**
	 * 根据审核状态获取获奖信息列表
	 * @param currentPage
	 * @return
	 */
	//getUnCheckedPrize
	@RequestMapping(type = "post", url = "/PrizeServer/getUnCheckedPrize")
	public ModelAndView getUnCheckedPrize(String currentPage, PrizeDetail prizeDetail, HttpServletRequest request){
		System.out.println("prizeDetail1:"+prizeDetail);
		HashMap<String,Object> map = new HashMap<String,Object>();
		prizeDetail.handle_result="waiting";
		//prizeDetail.handle_result="审核通过";
		List<TermInfo> termInfos = termInfoDAO.getAllTermInfo();
		for (TermInfo termInfo : termInfos) {
			try {
				if(TimeUtil.checkTerm(termInfo.term_begin_time, termInfo.term_end_time)){
					prizeDetail.term_name = termInfo.term_name;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println("数据库学期时间错误");
				e.printStackTrace();
			}
		}
		System.out.println("ccccccccccccccccccccccc");
		System.out.println("prizeDetail:"+prizeDetail);
		//prizeDetail.term_name = TimeUtil.checkTerm(begin, end);
		map.put("prizeDetail", prizeDetail);
		map.put("begin", new Integer(pageSize)*(new Integer(currentPage)-1));
		map.put("length",pageSize);
		List<PrizeDetail> prizeList = prizeDetailDAO.getPrizeDetailByMap(map);
		for (PrizeDetail prizeDetail2 : prizeList) {
			prizeDetail2.file_path = fileInfoDAO.getFilesByPrizeId(prizeDetail2.prize_id);
		}
		int prizeCount = prizeDetailDAO.getPrizeDetailCountByTermAndType(prizeDetail);
		int totalPage = 0;
		if(prizeCount%pageSize==0)
			totalPage = prizeCount/pageSize;
		else
			totalPage = prizeCount/pageSize+1;
		map.clear();
		map.put("prizeList", prizeList);
		map.put("totalPage", totalPage);
		map.put("currentPage",currentPage);
		/*request.setAttribute("prizeList", prizeList);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("currentPage", currentPage);
		HttpServletResponse response ;*/
		return new JsonView(map);
		//return "forward:/files/app/uncheck.html";
	}
	
	
	
	/**
	 * 根据获奖id获取获奖详情
	 * @param prize_id
	 * @return
	 */
	//getPrizeDetailById
	@RequestMapping(type = "post", url = "/PrizeServer/getPrizeDetailById")
	public ModelAndView getPrizeDetailById(String prize_id){
		PrizeDetail prizeDetail =  prizeDetailDAO.getPrizeDetailById(prize_id);
		prizeDetail.file_path = fileInfoDAO.getFilesByPrizeId(prizeDetail.prize_id);
		return new JsonView(prizeDetail);
	}
	
	/**
	 * 审核获奖信息
	 * @param prizeDetail
	 * @return
	 */
	//passPrize
	@RequestMapping(type = "post", url = "/PrizeServer/passPrize")
	public ModelAndView passPrize(PrizeDetail prizeDetail){
		//prizeDetail.handle_result = "审核通过";
		prizeDetail.handle_time = TimeUtil.getStringTime();
		if(prizeDetail.prize_price==null)
			prizeDetail.prize_price = "0";
		if(prizeDetailDAO.updatePrizeDetail(prizeDetail)==1){
			System.out.println("获奖信息更新成功");
			return new HtmlView("/files/app/checkInfo.html?result=success");
		}
		else{
			System.out.println("获奖信息更新失败");
			return new HtmlView("/files/app/checkInfo.html?result=failed");
		}
	}
	
	
	/**
	 * 添加学期信息 成功返回true 失败返回false
	 * @param termInfo
	 * @return
	 */
	//addTermInfo
	@RequestMapping(type = "post", url = "/PrizeServer/addTermInfo")
	public HtmlView addTermInfo(TermInfo termInfo){
		String id  = UUIDUtil.getUUIDString();
		termInfo.term_id = id ;
		termInfoDAO.addTermInfo(termInfo);
		//判断学期信息是否添加成功  
		if(termInfoDAO.getTermInfoById(id)!=null)
			return new HtmlView("/files/app/settings.html?result=success");
		else
			return new HtmlView("/files/app/settings.html?result=failed");
	}
	
	/**
	 * 添加类型信息
	 * @param typeInfo
	 * @return
	 */
	//addTypeInfo
	@RequestMapping(type = "post", url = "/PrizeServer/addTypeInfo")
	public ModelAndView addTypeInfo(TypeInfo typeInfo){
		typeInfo.type_id = UUIDUtil.getUUIDString();
		if(typeInfoDAO.addTypeInfo(typeInfo)==1)
			return new HtmlView("/files/app/settings.html?result=success");
		else
			return new HtmlView("/files/app/settings.html?result=failed");
		
	}
	
	/**
	 * 删除学期信息
	 * @param term_id
	 * @return
	 */
	//deleteTermInfo
	@RequestMapping(type = "get", url = "/PrizeServer/deleteTermInfo")
	public ModelAndView deleteTermInfo(String term_id){
		if(termInfoDAO.deleteOne(term_id)==1)
			return new HtmlView("/files/app/settings.html?result=success");
		else
			return new HtmlView("/files/app/settings.html?result=failed");
	}
	
	/**
	 * 删除获奖类型信息
	 * @param type_id
	 * @return
	 */
	//deleteTypeInfo
	@RequestMapping(type = "post", url = "/PrizeServer/deleteTypeInfo")
	public ModelAndView deleteTypeInfo(String type_id){
		if(typeInfoDAO.deleteTypeInfo(type_id)==1)
			return new HtmlView("/files/app/settings.html?result=success");
		else
			return new HtmlView("/files/app/settings.html?result=failed");
	}
	
	/**
	 * 下载打印文件
	 * @param response
	 */
	@RequestMapping(type = "get", url = "/PrizeServer/download")
	public void download(String year,HttpServletResponse response) throws FileNotFoundException, IOException{
		System.out.println("生成excel文件中...");
		ExcelUtil.createExcel(year);
		System.out.println("excel文件生成完毕");
		String filePath =  System.getProperty("user.dir")+File.separator+"test.xls";
		String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
		//不能解决中文字体问题
		response.setCharacterEncoding("utf-8");
		//设置媒体类型   这样当客户端接收到数据时会自动启动下载器下载
		response.setContentType("multipart/form-data");
		 //这里的fileName是在浏览器下载文件时显示的文件名
		response.setHeader("Content-Disposition", "attachment;fileName="
                +fileName );
		System.out.println("fileName:"+fileName);
		System.out.println("filePath:"+filePath);
		//根据给定的文件名创建输入流  然后输入到response的输出流中去   同时要设定response的媒体类型   然后生成响应报文传给浏览器
		FileCopyUtils.copy(new FileInputStream(new File(filePath)),response.getOutputStream());
		
	}
}
