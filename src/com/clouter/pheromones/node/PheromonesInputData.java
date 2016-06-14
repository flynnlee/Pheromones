package com.clouter.pheromones.node;

import java.util.HashMap;
import java.util.Map;

public class PheromonesInputData {
	private Object data;
	private PheroGlobalData globalData;
	private Map<String, Object> others;
	public PheromonesInputData(Object data){
		this.data = data;
		this.globalData = PheroGlobalData.getInstance();
		this.others = new HashMap<String, Object>();
	}
	public PheroGlobalData getGlobalData() {
		return globalData;
	}
	public Object getData() {
		return data;
	}
	public Object getOther(String key){
		return others.get(key);
	}
	public void addOther(String key, Object other){
		others.put(key, other);
	}
}
