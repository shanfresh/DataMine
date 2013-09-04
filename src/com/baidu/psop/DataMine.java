package com.baidu.psop;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

import com.baidu.psop.Apriori.Attr;
public class DataMine {
	public static String sourcefile="";
	public static String headfile="";
	public static float support,confidence;
	public static String[] head=null;
	public static void process(String[] args){
		FileReader fr=new FileReader();
		HashMap<String, Integer> result=fr.Read(sourcefile);
		System.out.println("Finishi loading data");
		int keySize=result.size();
		System.out.println("Size:"+keySize);
		FileWriter(result,"processed_tmp.txt");
		System.out.println("Finish Write data");
		
	}
	public static void main(String[] args){
		checkArgs(args);
		process(args);
		LoadData ld=new LoadData();
		HashMap<String,Integer> result=ld.Load("processed_tmp.txt");
		head=ld.LoadHeader(headfile);
		if(head==null){
			System.err.println("输入的Head文件："+headfile+"不存在");
			System.exit(-1);
		}else{
			System.out.print("Head.csv:");
			for(int i=0;i<head.length;i++){
				System.out.print(head[i]+" ");
			}
			System.out.println();
		}
		if(result==null){
			System.err.println("输入的文件："+sourcefile+"不存在");
			System.exit(-1);
		}
		System.out.println("原始条目大小:"+ld.itemsCount);
		
		System.out.println("有效条目数:"+result.size());
		Set<String> keys=result.keySet();
		Iterator<String> it=keys.iterator();
		Apriori apriori=new Apriori(result,ld.itemsCount,support,confidence);
		Vector<Attr> L1Result=apriori.GetL1();
		Vector<Vector<Attr>> output=new Vector<Vector<Attr>>();
		apriori.GetLN(L1Result,output);
		
		Vector<Role> confidenceoutput=new Vector<Role>();
		apriori.CheckConfidence(output, 3, confidenceoutput);//只有包含目标属性的才会被计算并加入
		Collections.sort(confidenceoutput);
		System.out.println("满足Confidence的大小是："+confidenceoutput.size());
		for(int i=0;i<confidenceoutput.size();i++){
			System.out.println(apriori.showRole(confidenceoutput.get(i),head));
		}
		
		
		
		
	}
	public static void checkArgs(String[] args){
		if(args.length<8){
			System.out.println("USAGE:");
			System.out.println("*.jar -src source.txt -head head.csv [-support 0.01] [-confidence 0.01]");
			System.exit(-1);
		}
		for(int i=0;i<args.length;i++){
			if(args[i].equalsIgnoreCase("-src")){
				sourcefile=args[++i];
			}else if(args[i].equalsIgnoreCase("-head")){
				headfile=args[++i];
			}else if(args[i].equalsIgnoreCase("-support")){
				String supportStr=args[++i];
				support=Float.parseFloat(supportStr);
			}else if(args[i].equalsIgnoreCase("-confidence")){
				String confidenceStr=args[++i];
				confidence=Float.parseFloat(confidenceStr);
			}
		}
	}
	
	public static void LoadHeader(){
		
	}
	
	public static void FileWriter(HashMap<String,Integer> source,String tempFileName){
		try {
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFileName)));
			Iterator<String> it=source.keySet().iterator();
			while(it.hasNext()){
				String t=it.next();
				Integer count=source.get(t);
				StringBuffer sb=new StringBuffer();
				sb.append(t);
				sb.append(" ");
				sb.append(count);
				bw.write(sb.toString()+"\n");
				
				
			}
			bw.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
