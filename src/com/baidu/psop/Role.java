package com.baidu.psop;

import java.util.Vector;

import com.baidu.psop.Apriori.Attr;

public class Role implements Comparable<Role>{
	int support;
	float confidence;
	int targetValue;
	Vector<Attr> attrs;
	

	
	public float getConfidence() {
		return confidence;
	}
	public void setConfidence(float confidence) {
		this.confidence = confidence;
	}

	public int getSupport() {
		return support;
	}
	public void setSupport(int support) {
		this.support = support;
	}
	
	public Vector<Attr> getAttrs() {
		return attrs;
	}
	public void setAttrs(Vector<Attr> attrs) {
		this.attrs = attrs;
	}

	@Override
	public int compareTo(Role o) {
		if(this.targetValue>o.targetValue){
			return -1;
		}else if(this.targetValue==o.targetValue){
			return 0;
		}else
			return 1;
	}
	

}
