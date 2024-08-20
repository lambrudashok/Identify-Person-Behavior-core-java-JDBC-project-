package org.behaviourprediction.config;

import java.io.*;

public class PathHelper {

	public static FileInputStream fin=null;
	public static File f=null;
	public static String pathFile=null;
	static {
		f = new File("");
		pathFile = (f.getAbsolutePath());
		String path = pathFile+"\\src\\db.properties";
		
		try {
			fin = new FileInputStream(path);
		}catch(Exception e) {
			System.out.println("path :"+e);
		}
	}
	
	
}
