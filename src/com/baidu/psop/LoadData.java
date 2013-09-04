package com.baidu.psop;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class LoadData {
	public int itemsCount=0;
	public HashMap<String, Integer> Load(String fileName){
		String fileNameAndPath;
		if(fileName==null)
			fileNameAndPath="E:\\apriori-python-master\\[new]process_data.217";
		else
			fileNameAndPath=fileName;
		HashMap<String, Integer> result=new HashMap<String, Integer>();
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(fileNameAndPath)));
			String t=null;
			int i=0;
			System.out.print("正在加载数据Loading[");
			while((t=br.readLine())!=null){
				int index=t.lastIndexOf(" ");
				String front=t.substring(0,index);
				String behind=t.substring(index+1);
				if(result.containsKey("index")){
					System.err.println("发现错误哦");
				}else{
					result.put(front, Integer.valueOf(behind));
				}
				if(i%100==0){
					System.out.print(i+" ");
				}
				itemsCount+=Integer.valueOf(behind);
				i++;
			}
			System.out.println("]加载完毕");
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
	public String[] LoadHeader(String fileName){
		try{
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			String t=null;
			int i=0;
			String[] result=null;
			if((t=br.readLine())!=null){
				result=t.split(",");
				
			}
			br.close();
			return result;
		}catch(Exception ex){
			return null;
		}
	}
}
