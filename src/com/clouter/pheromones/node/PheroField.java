package com.clouter.pheromones.node;

import java.util.HashMap;
import java.util.Map;

import org.jdom2.Attribute;
import org.jdom2.Element;

import com.clouter.clouterutil.XmlUtil;

public class PheroField {
	private String alias;
	private String generic;
	private String name;
	private String desc;
	private Map<String, String> properties;
	
	public PheroField(Element element){
		alias = XmlUtil.getAttributeValue(element, "alias");
		generic = XmlUtil.getAttributeValue(element, "generic");
		name = XmlUtil.getAttributeValue(element, "name");
		desc = XmlUtil.getAttributeValue(element, "desc");
		
		properties = new HashMap<String, String>();
		for(Attribute attribute : element.getAttributes()){
			properties.put(attribute.getName(), attribute.getValue());
		}
		for(Element child : element.getChildren()){
			if(child.getName().equals("properties")){
				properties.putAll(XmlUtil.propertiesParse(child));
			}
		}
	}

	public String getAlias() {
		return alias;
	}

	public PheroNode getAliasPheroNode(){
		return PheroGlobalData.getInstance().getPheroNode(alias);
	}
	
	public String getGeneric() {
		return generic;
	}
	
	public PheroNode getGenericPheroNode(){
		return PheroGlobalData.getInstance().getPheroNode(generic);
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}
	
	public String getProperty(String key){
		return properties.get(key);
	}
}
