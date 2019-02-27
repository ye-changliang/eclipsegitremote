package com.tydic.bigdata.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OutFilter {
	private static List<String> filterList;
	
	public static String read(BufferedReader br) {
		if(filterList==null){
			init();
		}
		StringBuffer buffer = new StringBuffer("");
		String s = null;
		try {
			while((s=br.readLine())!=null){
				/*if(valid(s)==false){
					continue;
				}*/
				buffer.append(s);
				buffer.append("\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	private static boolean valid(String line){
		for(String s : filterList){
			if(line.startsWith(s)){
				return false;
			}
		}
		return true;
	}
	
	private static void init(){
		//String filterStr = F.getProperty("print.filter");
		String filterStr = "Filesystem,/";
		filterList = new ArrayList<String>();
		if(filterStr.indexOf(",")>0){
			String[] strs = filterStr.split(",");
			Collections.addAll(filterList, strs);
		}
	}
	
}
