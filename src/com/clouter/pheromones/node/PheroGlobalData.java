package com.clouter.pheromones.node;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Element;

import com.clouter.clouterutil.XmlUtil;
import com.clouter.pheromones.exception.PheroNodeDuplicateAlias;

/**
 * 包含全局配置信息、
 * PheroNode集合、筛选需要输出的PheroNode
 * @author flynn
 *
 */
public class PheroGlobalData {
	private static final Log log = LogFactory.getLog(PheroGlobalData.class);
	private static PheroGlobalData instance;
	public static PheroGlobalData getInstance(){
		if(instance == null){
			instance = new PheroGlobalData();
		}
		return instance;
	}
	
	/**
	 * 清除已加载数据
	 */
	public static void clean(){
		instance = new PheroGlobalData();
	}

	/**类描述集合，key为alias*/
	private Map<String, PheroNode> nodeMapByAlias;
	private PheroGlobalData(){
		nodeMapByAlias = new HashMap<String, PheroNode>();
	}
	
	/**
	 * 添加一个PheroNode
	 * @param node
	 */
	private void addNode(PheroNode node){
		if(nodeMapByAlias.containsKey(node.getAlias())){
			throw new PheroNodeDuplicateAlias(node.getAlias());
		}
		nodeMapByAlias.put(node.getAlias(), node);
	}
	
	/**全局属性*/
	private Map<String, String> properties;
	/**配置文件所在文件夹路径*/
	private String baseDirPath;
	
	/**
	 * 读取配置
	 * @param filePath
	 */
	public void loadConfig(String filePath){
		Element configRoot = XmlUtil.getRoot(filePath);
		properties = new HashMap<String, String>();

		baseDirPath = new File(filePath).getParent();
		log.info("baseDirPath: " + baseDirPath);
		
		for(Element element : configRoot.getChildren()){
			if(element.getName().equals("properties")){
				properties.putAll(XmlUtil.propertiesParse(element));
			}else if(element.getName().equals("nodes")){
				load(element);
			}
		}
		
	}
	
	/**
	 * 读取模板文件
	 * @return
	 */
	public String getVmfile(){
		String rt = getProperty("vm_file");
		return rt == null ? "template.vm" : rt;
	}
	
	/**
	 * 读取属性
	 * @param key
	 * @return
	 */
	public String getProperty(String key){
		return properties.get(key);
	}
	
	/**
	 * 根据名为nodes的Element加载配置信息
	 * 如果Element为引用文件，则读取文件，否则则解析Element为PheroNode
	 * @param element
	 */
	public void load(Element element){
		if(!element.getName().equals("nodes")) return;
		for(Element eleNode : element.getChildren("node")){
			String resource = XmlUtil.getAttributeValue(eleNode, "resource");
			if(resource != null){
				if(new File(resource).exists()){
					load(XmlUtil.getRoot(resource));
				}else{
					load(XmlUtil.getRoot(baseDirPath + "/" + resource));
				}
			}else{
				PheroNode node = new PheroNode(eleNode);
				addNode(node);
			}
		}
	}
	
	/**
	 * 根据alias获取类描述
	 * @param alias
	 * @return
	 */
	public PheroNode getPheroNode(String alias){
		PheroNode rt = nodeMapByAlias.get(alias);
		if(rt == null){
			log.error("PheroNode is not exist:" + alias);
		}
		return rt;
	}
	
	/**
	 * 获取所有可导出的类描述信息
	 * @return
	 */
	public List<PheroNode> getAllOutputPheroNodes(){
		List<PheroNode> list = new ArrayList<PheroNode>();
		for(PheroNode node : nodeMapByAlias.values()){
			if(!node.needOutput()) continue;
			if(node instanceof PheroNode){
				list.add((PheroNode)node);
			}
		}
		return list;
	}
}
