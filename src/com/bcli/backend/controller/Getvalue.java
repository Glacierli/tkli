package com.bcli.backend.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bcli.backend.util.Properties.PropertyUtils;
import com.bcli.backend.vo.ErrorInfo;

@WebServlet(urlPatterns = "/getformvalue")
public class Getvalue extends HttpServlet {

	/**
	 * 获取form信息存入配置文件中
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		//异常信息的封装
		 ErrorInfo error = new ErrorInfo();
		// 解决转换json后中文乱码
 		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		// 1.用流的方式将数据从jsp获取
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8")); // 将数据使用流进行传递
		StringBuffer strb = new StringBuffer();
		String line;
		while ((line = reader.readLine()) != null) { // 遍历数据
			strb = strb.append(line.trim()); // 数据暂存StringBuffer
		}
		/*{"300302":[{"日期":0},{"人员":0},{"方式":0},{"金额":0},{"地点":0},{"事由":0}]}*/
	/*	[{"name":"gsyear","value":"1231"},{"name":"MaxSecuNum","value":"31"},{"name":"DataTime","value":"23"}]*/
		System.out.println("strb"+strb);
		JSONObject obj = (JSONObject) JSONObject.parse(strb.toString());
		String cartypes=obj.getString("value");  
		JSONArray jsonArray = JSONArray.parseArray(cartypes);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject job = jsonArray.getJSONObject(i);
			String key = job.getString("name");
			if(key.equals("gsyear")){
				PropertyUtils.setProp("gsyear",job.getString("value"));
			}else if(key.equals("gsssgs")){
				PropertyUtils.setProp("gsssgs",job.getString("value"));
			}else if(key.equals("cbsyear")){
				PropertyUtils.setProp("cbsyear",job.getString("value"));
			}else if(key.equals("cbsssgs")){
				PropertyUtils.setProp("cbsssgs",job.getString("value"));
			}else if(key.equals("SecuNum")){
				PropertyUtils.setProp("SecuNum",job.getString("value"));
			}else if(key.equals("MaxSecuNum")){
				PropertyUtils.setProp("MaxSecuNum",job.getString("value"));
			}else if(key.equals("DataTime")){
				PropertyUtils.setProp("DataTime",job.getString("value"));
			}
		}
		//##此时单项的值已经存入完成，然后进行判断输入的安全天数和历史天数谁大谁小
		//1 定义中间变量
		int num=0;
		//分别获取输入的安全记录数和历史最高数
		int intSecuNum=Integer.valueOf(PropertyUtils.getProp("SecuNum"));
		if("".equals(PropertyUtils.getProp("MaxSecuNum"))||PropertyUtils.getProp("MaxSecuNum")==null){
			PropertyUtils.setProp("MaxSecuNum", "0");
		}
		
		int intMaxSecuNum=Integer.valueOf(PropertyUtils.getProp("MaxSecuNum"));
		//进行判断大小并修改值
		if(intSecuNum>intMaxSecuNum){
			num=intSecuNum;
			System.out.println("新添加的安全天数大于了历史记录"+num);
		}else{
			num=intMaxSecuNum;
			System.out.println("新添加的安全天数不大于历史记录"+num);
		}
		PropertyUtils.setProp("MaxSecuNum", String.valueOf(num));
		
		// 2.JSONObject转String
		String value = obj.toJSONString();
		value = "[" + value + "]";
		// 将格式好的json格式的keyvalue赋值给dpa配置文件
		PropertyUtils.setProp("formvalue", value);
		//判断是否都有值,数据存入是否成功
		if(!"".equals(PropertyUtils.getProp("formvalue"))){
			error.setCode(1);
			error.setMsg("表单数据提交成功");
		}else{
			error.setCode(-1);
			error.setMsg("提交失败，请检查数据格式重新填写");
		}
		   response.setCharacterEncoding("UTF-8"); 
	        response.setContentType("application/json; charset=utf-8"); 
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
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
