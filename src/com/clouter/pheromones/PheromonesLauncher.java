package com.clouter.pheromones;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.clouter.clouterutil.file.FileUtil;
import com.clouter.clouterutil.velocity.ConverterParam;
import com.clouter.clouterutil.velocity.VelocityConverter;
import com.clouter.pheromones.node.PheroGlobalData;
import com.clouter.pheromones.node.PheroNode;
import com.clouter.pheromones.node.PheromonesInputData;

public class PheromonesLauncher {
	private static final Log log = LogFactory.getLog(PheromonesLauncher.class);
	
	public void launch(String configFile){
		PheroGlobalData.clean();
		PheroGlobalData.getInstance().loadConfig(configFile);
		
		String encode = PheroGlobalData.getInstance().getProperty("encode");
		String defaultVmFile = PheroGlobalData.getInstance().getVmfile();
		String vmFileKey = PheroGlobalData.getInstance().getProperty("vmfile_key");
		if(vmFileKey == null){
			vmFileKey = "vm_file";
		}
		output(encode, defaultVmFile, vmFileKey);
	}
	
	public void output(String encode, String defaultVmFile, String vmFileKey){
		VelocityConverter converter = new VelocityConverter();
		for(PheroNode node : PheroGlobalData.getInstance().getAllOutputPheroNodes()){
			
			ConverterParam param = new ConverterParam();
			param.setInputData(new PheromonesInputData(node));
			param.setEncodeStr(encode);
			String vmFile = node.getProperty(vmFileKey);
			if(vmFile == null){
				vmFile = defaultVmFile;
			}
			param.setVmFile(vmFile);
			
			converter.convert(param);
			param.getFileContent().setFilePath(node.getOutputPath());
			param.getFileContent().setFileName(node.getFileName());
			log.info("[output]" + param.getFileContent());
			FileUtil.saveFileContent(param.getFileContent());
		}
	}
	
	public static void main(String[] args) throws Exception{
		new PheromonesLauncher().launch("/Users/flynn/Documents/clouter/workspace/Pheromones/test/config.xml");
	}
}
