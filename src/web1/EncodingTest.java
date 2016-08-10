package web1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Properties;

public class EncodingTest {
	  public static void main(String[] args) {  
	        System.out.println(getJVMEnconding());  
	        getJVMParams();  
	    }  
	    // 获得jvm的默认编码  
	     public static String getJVMEnconding() {  
	        return System.getProperty("file.encoding");  
	      }  
	       
	     //获取JVM属性  
	     public static void  getJVMParams() {  
	        try {  
	            Properties properties=System.getProperties();  
	            PrintWriter out=null;  
	            out = new PrintWriter(new File("a.txt"));  
	            properties.list(out);  
	            out.flush();  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        }  
	    }  
}
