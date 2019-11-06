package com.bcli.backend.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.bcli.backend.util.Properties.PropertyUtils;

/**
 * @author bcli
 * @time 2019年7月23日 下午3:35:40
 * @version 1.0
 * 返回基础数据
 */
@WebServlet(urlPatterns = "/Echo")
public class EchoController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 解决转换json后中文乱码
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		String gsyear=PropertyUtils.getProp("gsyear");
		String gsssgs=PropertyUtils.getProp("gsssgs");
		String cbsyear=PropertyUtils.getProp("cbsyear");
		String cbsssgs=PropertyUtils.getProp("cbsssgs");
		String SecuNum=PropertyUtils.getProp("SecuNum");
		String MaxSecuNum=PropertyUtils.getProp("MaxSecuNum");
		String DataTime=PropertyUtils.getProp("DataTime");
		Map<String,Object>map=new HashMap<>();
		map.put("gsyear", gsyear);
		map.put("gsssgs", gsssgs);
		map.put("cbsyear", cbsyear);
		map.put("cbsssgs", cbsssgs);
		map.put("SecuNum", SecuNum);
		map.put("MaxSecuNum", MaxSecuNum);
		map.put("DataTime", DataTime);
		String json=JSON.toJSONString(map);
		System.out.println("json:"+json);
		 response.getWriter().println(json);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
