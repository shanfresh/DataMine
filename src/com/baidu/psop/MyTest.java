package com.baidu.psop;
import java.util.*;
public class MyTest {

	/**
	 * @param args
	 */
	static class t{
		String m;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector<String> t1=null;
		t m=null;
		int mytest=0;
		result(mytest);
		System.out.println(mytest);
		SetVector(t1,m);
		if(t1==null){
			System.out.println("ERROR");
			
		}
		if(m==null){
			System.out.println("ERROR2");
		}
		
	}
	public static void SetVector(Vector<String> t1,t m){
		t1=new Vector<String>();
		m=new t();
	}
	public static void result(int t){
		t=-1;
	}

}
