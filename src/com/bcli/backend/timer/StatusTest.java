package com.bcli.backend.timer;
/**
 * 定时任务，每00:00:00执行一次+1
 */
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.alibaba.fastjson.JSONObject;
import com.bcli.backend.constant.Constants;
import com.bcli.backend.util.HttpUtils;
import com.bcli.backend.util.Properties.PropertyUtils;
import com.bcli.backend.vo.ErrorInfo;


public class StatusTest {
    public static void main(String[] args) {
    	//进入定时任务 
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    	System.out.println("进入定时任务，当前时间为"+df.format(new Date()));
    	/**1，获取输入的安全天数，获取当前本地存的最高安全天数
    	 * 2,24小时出发一次定时任务对输入安全天数进行更新+1的操作，并给本地的当前安全天数赋值为最新值
    	 * 3,更新完成后，拿出本地最新的安全天数和本地历史最高安全天数进行比较
    	 * 如果最高纪录大不修改，如果当前安全天数(+1更新后)大于历史天数将该值赋成最高历史安全天数
    	 * 到此本地数据存的数据为最新的当前记录数，最高安全记录数
    	 */
    	//获取用户输入的安全天数
    	String anquts=PropertyUtils.getProp("SecuNum");
    	//获取历史最高安全记录数
        String zganquts=PropertyUtils.getProp("MaxSecuNum");
        //中间变量
        int num=0;
        int intanquts=0;
        int intzganquts=0;
       //对用户输入的安全天数进行+1;
        if(!"".equals(anquts)){
        	intanquts=Integer.valueOf(anquts);
        	System.out.println("定时任务执行前的天数"+intanquts);
        	intanquts++;//天数更新完成
        	PropertyUtils.setProp("SecuNum",String.valueOf(intanquts));
        	System.out.println("定时任务执行后的天数"+intanquts);
        }
        //进行转int
        if(!"".equals(zganquts)){
        	intzganquts=Integer.valueOf(zganquts);
        }
        //进行比较
        if(intanquts>intzganquts){
        	num=intanquts;
        }else{
        	num=intzganquts;
        }
        String numvalue=String.valueOf(num);
        System.out.println("之前最高安全记录数："+zganquts);
        System.out.println("最终最高安全记录数："+numvalue);
        //更新比较结束进行赋值操作
        PropertyUtils.setProp("MaxSecuNum", numvalue);
        
        //更新一次进行一次持久化操作
   /*     FileWriter fileWriter = null;
        try {
			fileWriter = new FileWriter("D:/a.txt");//创建文本文件
			int i=0;
			String gsyear=PropertyUtils.getProp("gsyear");
			String gsssgs=PropertyUtils.getProp("gsssgs");
			String cbsyear=PropertyUtils.getProp("cbsyear");
			String cbsssgs=PropertyUtils.getProp("cbsssgs");
			String SecuNum=PropertyUtils.getProp("SecuNum");
			String MaxSecuNum=PropertyUtils.getProp("MaxSecuNum");
			String DataTime=PropertyUtils.getProp("DataTime");
				
				fileWriter.write(gsyear+"\r\n");//写入 \r\n换行
				fileWriter.write(gsssgs+"\r\n");//写入 \r\n换行
				fileWriter.write(cbsyear+"\r\n");//写入 \r\n换行
				fileWriter.write(cbsssgs+"\r\n");//写入 \r\n换行
				fileWriter.write(SecuNum+"\r\n");//写入 \r\n换行
				fileWriter.write(MaxSecuNum+"\r\n");//写入 \r\n换行
				fileWriter.write(DataTime+"\r\n");//写入 \r\n换行
		
			
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

    	
    	
    	
    	
    /*	String gsssgs = PropertyUtils.getProp("gsssgs");
    	int max=Integer.valueOf(gsssgs);
    	max++;
    	System.out.println(max);
    	PropertyUtils.setProp("MaxSecuNum",String.valueOf(max));
    	*/
    	
 /*      String reqXml = Constants.ORGID;
        if("".equals(reqXml) || reqXml==null){
            reqXml = PropertyUtils.getProp("ORGID");
            Constants.ORGID = reqXml;
        }
        String get_conf_type = Constants.GET_CONF_TYPE;
        if(get_conf_type==null ||"".equals(get_conf_type)){
            get_conf_type = PropertyUtils.getProp("GET_CONF_TYPE");
            Constants.GET_CONF_TYPE=get_conf_type;
        }
        //System.out.println(reqXml);
      //连接通讯传值结束
        String url = PropertyUtils.getProp("CENTRAL_ADDRESS") + PropertyUtils.getProp("TESTCONNECT");
        if("http".equals(get_conf_type)){
            String resXml = HttpUtils.checkPostMethod(url, reqXml);
            JSONObject  jsonObject = JSONObject.parseObject(resXml);
            ErrorInfo error = new ErrorInfo();
            if(null !=jsonObject.get("code") && jsonObject.get("code").toString().equals("1")) {
                error.setCode(1);
                error.setMsg("远程服务连接正常");
            }else {
                error.setCode(-1);
                error.setMsg(jsonObject.get("msg").toString());
            }
            
        }*/

        }
    }

