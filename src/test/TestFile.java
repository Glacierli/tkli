/**测试文件夹是否存在，不存在就进行创建*/
package test;

import java.io.File;

/**
 * @author bcli
 * @time 2019年11月3日 上午8:50:04
 * @version 1.0
 */
public class TestFile {
public static void main(String[] args) {
	/**测试思路，如果模块有多个的话，还可以继续的添加，向该模块添加数据
	 * 先进行文件夹的创建
	 * 
	 */
	
	
	//获取文件夹的路径
	File file =new File("e:/cc/cc.jpg");    
	//如果文件夹不存在则创建    
	if  (!file .exists()  && !file .isDirectory())      
	{       
	    System.out.println("//不存在");  
	    file .mkdir();    
	} else   
	{  
	    System.out.println("//目录存在");  
	}  
}
}
