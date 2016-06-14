package com.clouter.pheromones.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Attribute;
import org.jdom2.Element;

import com.clouter.clouterutil.XmlUtil;
import com.clouter.pheromones.exception.PheroNodeSelfBaseException;

/**
 * 用户自定义类描述
 * @author flynn
 *
 */
public class PheroNode{
	private static final Log log = LogFactory.getLog(PheroNode.class);
	/**所有属性*/
	private Map<String, String> properties;
	/**属性列表*/
	private List<PheroField> fieldList;
	
	public PheroNode(Element element){
		properties = new HashMap<String, String>();
		fieldList = new ArrayList<PheroField>();
		
		for(Attribute attribute : element.getAttributes()){
			properties.put(attribute.getName(), attribute.getValue());
		}
		
		for(Element child : element.getChildren()){
			if(child.getName().equals("properties")){
				properties.putAll(XmlUtil.propertiesParse(child));
			}else if(child.getName().equals("field")){
				PheroField field = new PheroField(child);
				fieldList.add(field);
			}
		}
		//检测玩家自定义类不能继承自己的属性
		if(getAlias().equals(getBaseAlias())){
			throw new PheroNodeSelfBaseException(getAlias());
		}
	}

	/**
	 * 返回属性列表
	 * @return
	 */
	public List<PheroField> getFieldList() {
		return fieldList;
	}

	public String getPackageName() {
		return getProperty("package");
	}
	
	public String getAlias(){
		return properties.get("alias");
	}
	
	public boolean needOutput(){
		String outputKey = PheroGlobalData.getInstance().getProperty("output_key");
		if(outputKey == null) outputKey = "visible";
		if(properties.containsKey(outputKey)){
			return !properties.get(outputKey).equalsIgnoreCase("false");
		}
		return true;
	}
	
	/**
	 * 导出完整路径
	 * @return
	 */
	public String getOutputPath(){
		String projectPath = PheroGlobalData.getInstance().getProperty("project_path");
		String relativePathKey = PheroGlobalData.getInstance().getProperty("relative_path_key");
		String packagePath = getProperty(relativePathKey == null ? "package_path" : relativePathKey);
		if(packagePath == null){
			String srcFolder = getProperty("src_folder");
			if(srcFolder == null){
				srcFolder = "";
			}
			packagePath = getProperty("package").replaceAll("[.]", "/");
			return projectPath + "/" + srcFolder + "/" + packagePath;
		}
		return projectPath + "/" + packagePath;
	}
	
	/**
	 * 保存文件名
	 * @return
	 */
	public String getFileName(){
		String fileNameKey = PheroGlobalData.getInstance().getProperty("filename_key");
		if(fileNameKey == null){
			fileNameKey = "file_name";
		}
		String fileName = getProperty(fileNameKey, false);
		if(fileName == null) fileName = getAlias();
		String extension = PheroGlobalData.getInstance().getProperty("extension");
		return fileName + "." + extension;
	}
	
	/**
	 * 获取指定属性
	 * @param key
	 * @return
	 */
	public String getProperty(String key){
		return getProperty(key, true);
	}
	
	public String getProperty(String key, boolean baseValid){
		if(properties.containsKey(key)) return properties.get(key);
		if(baseValid && properties.containsKey("baseAlias")){
			PheroNode node = getBasePheroNode();
			return node.getProperty(key);
		}
		return null;
	}

	/**
	 * 获取继承属性的类
	 * @return
	 */
	public String getBaseAlias(){
		return properties.get("baseAlias");
	}
	
	/**
	 * 获取继承属性的类描述
	 * @return
	 */
	public PheroNode getBasePheroNode(){
		PheroNode baseNode = PheroGlobalData.getInstance().getPheroNode(getBaseAlias());
		if(baseNode == null){
			log.error("BaseNode is not exist in " + getAlias());
		}
		return baseNode;
	}
	
	public PheroField getField(String fieldName){
		for(PheroField field : fieldList){
			if(field.getName().equals(fieldName)){
				return field;
			}
		}
		PheroNode basePheroNode = getBasePheroNode();
		if(basePheroNode == null){
			log.error("field not exist: baseAlias not exist.");
			return null;
		}
		return basePheroNode.getField(fieldName);
	}

	public String getGeneric() {
		return getAlias();
	}
	
	public void getAllUsedAlias(boolean includeSelf, Set<String> set){
		if(includeSelf){
			set.add(getAlias());
		}
		for(PheroField field : fieldList){
			set.add(field.getAlias());
			PheroNode aliasNode = field.getAliasPheroNode();
			if(aliasNode != null){
				aliasNode.getAllUsedAlias(includeSelf, set);
			}
			if(field.getGeneric() != null){
				set.add(field.getGeneric());
				PheroNode genericNode = field.getGenericPheroNode();
				if(genericNode != null){
					genericNode.getAllUsedAlias(includeSelf, set);
				}
			}
		}
	}
}
