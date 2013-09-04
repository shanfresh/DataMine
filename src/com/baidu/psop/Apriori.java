package com.baidu.psop;
import java.util.*;
public class Apriori {
	public static int MINSUPPORT=0;
	public static float CONFIDENCE=0.01f;
	class Attr{
		String name;
		int index;
		int count;
		public Attr(String _name,int _index,int _count){
			this.name=_name;this.index=_index;this.count=_count;
		}
		
		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			if(obj instanceof Attr){
				Attr other=(Attr)obj;
				if(this.name.equals(other.name)&&this.count==other.count){
					return true;
				}
			}
			return false;
		}
	}
	
	HashMap<String,Integer> source;
	int itemsCount=0;
	public Apriori(HashMap<String,Integer> source,int _itemsCount,float support,float confidence){
		this.source=source;
		this.itemsCount=_itemsCount;
		MINSUPPORT=(int) (this.itemsCount*support);
		CONFIDENCE=confidence;
	}
	public Vector<Attr> GetL1(){
		Vector<Attr> result=new Vector<Apriori.Attr>();
		Iterator<String> it=source.keySet().iterator();
		while(it.hasNext()){
			String t=it.next();
			int count=source.get(t);
			
			String[] split=t.split(",");
			for(int i=0;i<split.length;i++){
				String eachitem=split[i];
				boolean find=false;
				int findindex=-1;
				for(int j=0;j<result.size();j++){
					if(result.get(j).name.equals(eachitem)&&result.get(j).index==i){
						find=true;
						findindex=j;
					}					
				}
				if(find){//如果已经找到了怎办
					result.get(findindex).count+=count;
				}else{
					Attr attr=new Attr(eachitem, i,count);
					result.add(attr);
				}
			}
			
			
		}
		System.out.println("生成的L1频繁集合大小为："+result.size());
		for(int i=result.size()-1;i>=0;i--){
			if(result.get(i).count<MINSUPPORT){
				result.remove(i);
			}			
		}
		System.out.println("生成的L1频繁集合过滤后大小为："+result.size());
		return result;
	}
	//传入的L1为频繁项集合，已经过滤
	public void GetLN(Vector<Attr> l1,Vector<Vector<Attr>> output){
		if(l1.size()==0){
			return;
		}
		int weishu=2;
		boolean mask[]=new boolean[l1.size()];
		Vector<Vector<Attr>> newResult=new Vector<Vector<Attr>>(l1.size());
		for(int i=0;i<l1.size();i++){
			Vector<Attr> each=new Vector<Apriori.Attr>();
			each.add(l1.get(i));
			newResult.add(each);
		}
		
		Vector<Vector<Attr>> L2=GenSubSet(newResult,1);
		System.out.println("生成的L2层频繁集合为："+L2.size());
		DoFilter(L2);
		System.out.println("过滤后L2层的频繁集合为："+L2.size());
		output.addAll(L2);

		Vector<Vector<Attr>> L3=GenSubSet(L2,2);
		System.out.println("生成的L3层频繁集合为："+L3.size());
		DoFilter(L3);
		System.out.println("过滤后L3层的频繁集合为："+L3.size());
		output.addAll(L3);

		
		Vector<Vector<Attr>> L4=GenSubSet(L3,2);
		System.out.println("生成的L4层频繁集合为："+L4.size());
		DoFilter(L4);
		System.out.println("过滤后L4层的频繁集合为："+L4.size());

		output.addAll(L4);
		
		
	}
	

	
	public Vector<Vector<Attr>> GenSubSet(Vector<Vector<Attr>> l1,int inputSize){
		Vector<Vector<Attr>> result=new Vector<Vector<Attr>>();
		for(int i=0;i<l1.size()-1;i++){
			for(int j=i+1;j<=l1.size()-1;j++){
				Vector<Attr> left=l1.get(i);
				Vector<Attr> right=l1.get(j);
				Vector<Attr> eachResult=new Vector<Attr>();
				boolean flag=Merge(left, right,eachResult);
				if(flag){
					result.add(eachResult);
				}
				
			}
		}
		return result;
	}
	
	private boolean Merge(Vector<Attr> left,Vector<Attr> oldright,Vector<Attr> output){
		int originLength=left.size();
		if(originLength>=1){
			for(int i=0;i<originLength-1;i++){
				if(left.get(i)!=oldright.get(i)){
					return false;
				}
			}
		}
		if(left.get(originLength-1).index==oldright.get(originLength-1).index){
			return false;
		}
		
		Vector<Attr> right=(Vector<Attr>)oldright.clone();
		boolean isAdded=false;
		int addcount=0;
		int leftindex=0;
		int rightindex=0;
		for(int i=0;i<originLength-1;i++){
			output.add(left.get(i));
		}
		if(left.get(originLength-1).index>right.get(originLength-1).index){
			output.add(right.get(originLength-1));
			output.add(left.get(originLength-1));
		}else{
			output.add(left.get(originLength-1));
			output.add(right.get(originLength-1));
		}
		return true;
		
	}
	
	private void DoFilter(Vector<Vector<Attr>> list){
		boolean[] mask=new boolean[list.size()];
		for(int i=0;i<list.size();i++){
			Vector<Attr> each=list.get(i);
//			if(i%100==0){
//				if(i%1000==0){
//					System.out.println();
//				}
//				System.out.print(i+" ");
//			}
			int result=IsContains(each);
			if(result>MINSUPPORT){
				mask[i]=true;
			}else{
				mask[i]=false;
			}			
		}
		for(int i=(mask.length-1);i>=0;i--){
			if(mask[i]==false){
				list.remove(i);
			}
		}
		System.out.println();
		
	}
	private int IsContains(Vector<Attr> attr){
		Iterator<String> it=source.keySet().iterator();
		int result=0;
		while(it.hasNext()){
			String value=it.next();//每一个HashMap中的数据的值
			if(Check(attr,value)){
				result+=source.get(value);
			}
		}
		return result;
	}
	private boolean Check(Vector<Attr> attr,String value){
		String[] values=value.split(",");
		boolean flag=true;
		for(int i=0;i<attr.size();i++){
			int index=attr.get(i).index;
			if(attr.get(i).name.equals(values[index])){
				
			}else{
				return false;
			}
		}
		return true;
	}
	
	public void CheckConfidence(Vector<Vector<Attr>> allRoles,int targetIndex,Vector<Role> outputroles){
		Iterator<Vector<Attr>> it=allRoles.iterator();
		while(it.hasNext()){
			Vector<Attr> eachRole=it.next();
			boolean hasTarget=false;
			for(int i=0;i<eachRole.size();i++){
				if(eachRole.get(i).index==targetIndex){
					hasTarget=true;
					break;
				}
			}
			if(hasTarget){//如果发现存在目标的Index
				//计算confidence
				int totalCount=IsContains(eachRole);
				Vector<Attr> newRole=new Vector<Attr>();//不包含目标属性的统计值。
				String targetValue=null;
				for(int j=0;j<eachRole.size();j++){
					if(eachRole.get(j).index!=targetIndex){
						newRole.add(eachRole.get(j));
					}else{
						targetValue=eachRole.get(j).name;
					}
				}
				int subCount=IsContains(newRole);
				float conf=0.0f;
				if(	(conf=( ((float)totalCount)/((float)subCount) ))>CONFIDENCE){
					Role role=new Role();
					role.confidence=conf;
					role.attrs=eachRole;
					try{
						role.targetValue=Integer.parseInt(targetValue);
					}catch(NumberFormatException ex){
						role.targetValue=0;
					}
					outputroles.add(role);
				}
				
			}
		}
	}
	
	public String showRole(Role role,String[] head){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<role.attrs.size();i++){
			int index=role.attrs.get(i).index;
			sb.append(head[index]+"=>"+role.attrs.get(i).name+"\t");
		}
		sb.append("\t Confidence:"+role.confidence);
		return sb.toString();
	}
	
	


	
	
}
