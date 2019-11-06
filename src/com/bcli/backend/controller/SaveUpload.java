package com.bcli.backend.controller;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSON;
import com.bcli.backend.vo.ErrorInfo;

/**
 * @author bcli
 * @time 2019年10月31日 下午5:23:58
 * @version 1.0
 * 保存文件到服务器
 */
 @WebServlet(urlPatterns = "/upload_deal")
public class SaveUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

	//异常信息的封装
	 ErrorInfo error = new ErrorInfo();
	// 解决转换json后中文乱码
     request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	response.setContentType("application/json; charset=utf-8");
	   try{
           //1.得到解析器工厂
           DiskFileItemFactory factory = new DiskFileItemFactory();
           //2.得到解析器
           ServletFileUpload upload = new ServletFileUpload(factory);
           //3.判断上传表单的类型
           if(!upload.isMultipartContent(request)){
               //上传表单为普通表单，则按照传统方式获取数据即可
               return;
           }
           //为上传表单，则调用解析器解析上传数据
           List<FileItem> list = upload.parseRequest(request);  //FileItem
           String value =null;
           //遍历list，得到用于封装第一个上传输入项数据fileItem对象
           for(FileItem item : list){
               if(item.isFormField()){
                   //得到的是普通输入项
                   String name = item.getFieldName();  //得到输入项的名称
                    value = item.getString();
                   //转码
                    value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
                   System.out.println(name + "=" + value);
               }else{
                   //得到上传输入项
                   String filename = item.getName();  //得到上传文件名  C:\Documents and Settings\ThinkPad\桌面\1.txt
                   System.out.println("filename:"+filename);
                   filename = filename.substring(filename.lastIndexOf("\\")+1);
                   //为了防止提交空数据
                   if (!"".equals(filename)){
                	   FileOutputStream out=null;
                       InputStream in = item.getInputStream();   //得到上传数据
                       int len = 0;
                       byte buffer[]= new byte[1024];
                       if(!"".equals(value)){
                    	   //System.out.println(value);
                    	   if(value.equals("首页图片")){
                    		   String savepath = this.getServletContext().getRealPath("/首页图片");
                                out = new FileOutputStream(savepath + "\\" + filename);  //向首页图片目录中写入文件
                                error.setMsg("文件已经上传到："+savepath+"&nbsp;&nbsp;目录下");
                    	   } else if(value.equals("安全管理体系手册")){
                    		   String savepath = this.getServletContext().getRealPath("/安全管理体系手册");
                                out = new FileOutputStream(savepath + "\\" + filename);  //向安全管理体系手册目录中写入文件
                                error.setMsg("文件已经上传到："+savepath+"&nbsp;&nbsp;目录下");
                    	   }else if(value.equals("安全作业指引")){
                    		   String savepath = this.getServletContext().getRealPath("/安全作业指引");
                                out = new FileOutputStream(savepath + "\\" + filename);  //向安全作业指引目录中写入文件
                                error.setMsg("文件已经上传到："+savepath+"&nbsp;&nbsp;目录下");
                    	   }else if(value.equals("健康安全活动图片")){
                    		   String savepath = this.getServletContext().getRealPath("/健康安全活动图片");
                                out = new FileOutputStream(savepath + "\\" + filename);  //向健康安全活动图片目录中写入文件
                                error.setMsg("文件已经上传到："+savepath+"&nbsp;&nbsp;目录下");
                    	   }else if(value.equals("健康及安全政策")){
                    		   String savepath = this.getServletContext().getRealPath("/健康及安全政策");
                                out = new FileOutputStream(savepath + "\\" + filename);  //向健康及安全政策目录中写入文件
                                error.setMsg("文件已经上传到："+savepath+"&nbsp;&nbsp;目录下");
                    	   }else if(value.equals("相关检测报告")){
                    		   String savepath = this.getServletContext().getRealPath("/相关检测报告");
                                out = new FileOutputStream(savepath + "\\" + filename);  //向相关检测报告目录中写入文件
                                error.setMsg("文件已经上传到："+savepath+"&nbsp;&nbsp;目录下");
                    	   }else{
                    		   String savepath = this.getServletContext().getRealPath("/upload");
                                out = new FileOutputStream(savepath + "\\" + filename);  //向upload目录中写入文件
                                error.setMsg("文件已经上传到："+savepath+"&nbsp;&nbsp;目录下");
                    	   }
                       }
                      
                       while((len=in.read(buffer))>0){
                           out.write(buffer, 0, len);
                       }

                       in.close();
                       out.close();
                   }
                  
                   //这个是后缀的判断
                 /*  filename = filename.substring(filename.lastIndexOf("\\")+1).toLowerCase();
                   if (filename.endsWith(".stl") || filename.endsWith(".obj") || filename.endsWith(".3ds")) {

                   }*/
               }
           }
       	error.setCode(1);
		
          
       }catch (Exception e) {
           e.printStackTrace();
       	error.setCode(-1);
		error.setMsg("文件上传失败请检查后重新上传");
       }
	 /*  System.out.println(error);
	   request.setAttribute("error",error);
*/
      PrintWriter out = null;
       try {
           out = response.getWriter(); 
           out.write(JSON.toJSON(error).toString());    
       }catch (IOException e) {
           e.printStackTrace(); 
       } finally {
           if (out != null) { 
               out.close(); 
           }
       }
         
	}
	
	
}
