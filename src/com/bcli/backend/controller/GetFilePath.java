/**
 * 
 */
package com.bcli.backend.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.bcli.backend.vo.ErrorInfo;

/**
 * 获取文件的路径 返回前台用于显示
 * @author bcli
 * @time 2019年11月2日 下午3:31:39
 * @version 1.0
 * 
 */

@WebServlet(urlPatterns = "/getfilepath")
public class GetFilePath extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//存数据用
		Map<String,Object>map=new HashMap<>();
		//存数据用是图片还是其他
		List<String> picturelist = new ArrayList<String>();
		List<String> otherlist = new ArrayList<String>();
		
		// 异常信息的封装
		ErrorInfo error = new ErrorInfo();
		// 解决转换json后中文乱码
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		// 1.用流的方式将数据从jsp获取
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				request.getInputStream(), "utf-8")); // 将数据使用流进行传递
		StringBuffer strb = new StringBuffer();
		String line;
		while ((line = reader.readLine()) != null) { // 遍历数据
			strb = strb.append(line.trim()); // 数据暂存StringBuffer
		}
		System.out.println("strb" + strb);
		//加上路径的/
		String	lastpath="/"+strb;
		//通过传过来的值拼成文件夹的路径
		String path = this.getServletContext().getRealPath(lastpath);
		File file = new File(path);
		// 如果这个路径是文件夹
		if (file.isDirectory()) {
			// 获取路径下的所有文件
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				// 如果还是文件夹 递归获取里面的文件 文件夹
				if (files[i].isDirectory()) {
					System.out.println("目录：" + files[i].getPath());
					/* getFiles(files[i].getPath()); */
				} else {
					String pathvalue = files[i].getPath();
					pathvalue = pathvalue.substring(pathvalue.lastIndexOf("\\")+1).toLowerCase();
					//判断如果是图片的话单独放进一个lsit中
	                if (pathvalue.endsWith(".png") || pathvalue.endsWith(".jpg") || pathvalue.endsWith(".jpeg")|| pathvalue.endsWith(".gif")|| pathvalue.endsWith(".bmp")) {
	                	picturelist.add(path+"\\"+pathvalue);
	                   }else{
	                	   otherlist.add(path+"\\"+pathvalue);
	                   }
	                
	               // 将list放进map中;
	                map.put("picturelist", picturelist);
	                map.put("otherlist", otherlist);
					System.out.println("文件1：" + files[i].getPath());
				}

			}
		} else {
			System.out.println("文件：" + file.getPath());
		}
		
		 String json=JSON.toJSONString(map);
         response.getWriter().println(json);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doPost(request, response);
	}

	public void init() throws ServletException {
	}
	
	

}
