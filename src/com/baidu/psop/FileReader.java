package com.baidu.psop;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class FileReader {
	public HashMap<String, Integer> Read(String filename){
		if(filename==null){
			filename="E:\\apriori-python-master\\process_data.217";
		}
		HashMap<String, Integer> result=new  HashMap<String,Integer>();
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String t=null;
			int i=0;
			System.out.println("载入数据...");
			while((t=br.readLine())!=null){
				if(result.containsKey(t)){
					Integer oldValue=result.get(t);
					oldValue++;
					result.put(t, oldValue);
				}else{
					Integer one=new Integer(1);
					result.put(t, one);
				}
				if((i+1)%(100000*100)==0){
					System.out.println();
				}
				if(i%100000==0){
					System.out.print(".");
				}
				i++;
			}
			System.out.println("装载完成");
			br.close();
			return result;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
