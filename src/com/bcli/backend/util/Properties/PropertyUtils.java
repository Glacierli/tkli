package com.bcli.backend.util.Properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

public class PropertyUtils {
	
	public static final String FILENAME ="ehs.properties";
    
    /**
     * 根据KEY，读取文件对应的值 *
     * filePath 文件路径，即文件所在包的路径，例如：java/util/config.properties *
     */
    public static String getProp(String filename, String key) {
        try {
            SafeProperties safeProperty = new SafeProperties();
            String filepath = PropertyUtils.class.getClassLoader().getResource("/").getPath() + filename;
            System.out.println("路径"+filepath);
            File file = new File(filepath);
            InputStream in = new FileInputStream(file);
            safeProperty.load(in);
            in.close();
            String value = safeProperty.getProperty(key);
            return value;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * @param filename  文件名称
     * @param key    键
     * @param value    对应键需要修改的值
     */
    public static void setProp(String filename, String key, String value) {
        try {
            SafeProperties safeProperty = new SafeProperties();
            String filepath = PropertyUtils.class.getClassLoader().getResource("/").getPath() + filename;
            File file = new File(filepath);
            InputStream in = new FileInputStream(file);
            safeProperty.load(in);     // 一定要在修改值之前关闭fis
            in.close();
            OutputStream fos = new FileOutputStream(file);
            safeProperty.setProperty(key, value); // 保存，并加入注释
            safeProperty.store(fos, null);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 根据KEY，读取文件对应的值 *
     * filePath 文件路径，即文件所在包的路径，例如：java/util/config.properties *
     */
    public static String getProp(String key) {
        try {
            SafeProperties safeProperty = new SafeProperties();
            String filepath = PropertyUtils.class.getClassLoader().getResource("/").getPath() + FILENAME;
            filepath = filepath.replace("classes/", "");
            Reader inStream = new InputStreamReader(new FileInputStream(filepath), "UTF-8");
            safeProperty.load(inStream);
            
           /* File file = new File(filepath);
            InputStream in = new FileInputStream(file);
            safeProperty.load(in);*/
            inStream.close();
            String value = safeProperty.getProperty(key);
            return value;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * @param filename  文件名称
     * @param key    键
     * @param value    对应键需要修改的值
     */
    public static void setProp( String key, String value) {
        try {
            SafeProperties safeProperty = new SafeProperties();
            String filepath = PropertyUtils.class.getClassLoader().getResource("/").getPath() + FILENAME;
            filepath = filepath.replace("classes/", "");
            File file = new File(filepath);
            InputStream in = new FileInputStream(file);
            safeProperty.load(in);     // 一定要在修改值之前关闭fis
            in.close();
            OutputStream fos = new FileOutputStream(file);
            safeProperty.setProperty(key, value); // 保存，并加入注释
            safeProperty.store(fos, null);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
