package test.java.com.apitests.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestPropertiesLoader {
	
	Properties props;
	
	public TestPropertiesLoader ()
	{
		File f1 = new File("test.properties");   
		props = new Properties();
		FileInputStream propertiesFile = null;
		try {
			propertiesFile = new FileInputStream(f1.getCanonicalPath().toString());
			props.load(propertiesFile);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public String GetProperty(String property){
		 return props.getProperty(property);
	}
}
