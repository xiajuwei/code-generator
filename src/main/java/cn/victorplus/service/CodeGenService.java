package cn.victorplus.service;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.victorplus.bean.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.google.common.collect.Maps;
import cn.victorplus.bean.GenConf;
import cn.victorplus.util.FreemarkerUtil;
import cn.victorplus.util.StrUtil;

@Service
public class CodeGenService {
	private static final Logger logger = LoggerFactory.getLogger(CodeGenService.class);
	@Resource
	private TableService tableService;
	@Resource
	private GenConf genConf;

	public void genCode() throws Exception {
		logger.info("gen code,genConf={}", genConf);

		if (StrUtil.isBlank(genConf.getOutputDir())) {

			logger.error("output dir can NOT be null");
			return;
		}
		File outputDir = new File(genConf.getOutputDir());
		if (!outputDir.exists()) {
			outputDir.mkdirs();
		} else {
			outputDir.delete();
			outputDir.mkdirs();
		}

		File baseDbBeanPackage = new File(genConf.getOutputDir() + "/code/bean/db/");
//		File baseMapperPackage = new File(genConf.getOutputDir() + "/code/mapper/base/");
		File beanMapperPackage = new File(genConf.getOutputDir() + "/code/mapper/");
//		File baseMapperXmlPacakge = new File(genConf.getOutputDir() + "/res/mapper/base/");
		File beanMapperXmlPackage = new File(genConf.getOutputDir() + "/res/mapper");

		baseDbBeanPackage.mkdirs();
//		baseMapperPackage.mkdirs();
		beanMapperPackage.mkdirs();
//		baseMapperXmlPacakge.mkdirs();
		beanMapperXmlPackage.mkdirs();

		List<Table> tableList = tableService.getTableInfoList();
		for (Table table : tableList) {
			logger.info("gen code for table,tableName={}", table.getTableName());
			Map<String, Object> rootMap = Maps.newHashMap();
			rootMap.put("table", table);
			rootMap.put("cfg", genConf);

			File baseBeanTemplate = ResourceUtils.getFile("classpath:templates/ssi/bean.ftl");
			File baseMapperTemplate = ResourceUtils.getFile("classpath:templates/ssi/base_mapper.ftl");
			File beanMapperTemplate = ResourceUtils.getFile("classpath:templates/ssi/bean_mapper.ftl");
			File baseMapperXmlTemplate = ResourceUtils.getFile("classpath:templates/ssi/base_mapper_xml.ftl");
			File beanMapperXmlTemplate = ResourceUtils.getFile("classpath:templates/ssi/bean_mapper_xml.ftl");

			FileWriter baseBeanWriter = new FileWriter(baseDbBeanPackage.getAbsolutePath() + "/" + table.getBeanName() + ".java");
//			FileWriter baseMapperWriter = new FileWriter(baseMapperPackage.getAbsolutePath() + "/" + table.getBeanName() + "BaseMapper.java");
			FileWriter beanMapperWriter = new FileWriter(beanMapperPackage.getAbsolutePath() + "/" + table.getBeanName() + "Mapper.java");
//			FileWriter baseMapperXmlWriter = new FileWriter(baseMapperXmlPacakge.getAbsolutePath() + "/" + table.getBeanName() + "BaseMapper.xml");
			FileWriter beanMapperXmlWriter = new FileWriter(beanMapperXmlPackage.getAbsolutePath() + "/" + table.getBeanName() + "Mapper.xml");

			FreemarkerUtil.flushData(baseBeanTemplate.getAbsolutePath(), baseBeanWriter, rootMap);
//			FreemarkerUtil.flushData(baseMapperTemplate.getAbsolutePath(), baseMapperWriter, rootMap);
			FreemarkerUtil.flushData(beanMapperTemplate.getAbsolutePath(), beanMapperWriter, rootMap);
//			FreemarkerUtil.flushData(baseMapperXmlTemplate.getAbsolutePath(), baseMapperXmlWriter, rootMap);
			FreemarkerUtil.flushData(beanMapperXmlTemplate.getAbsolutePath(), beanMapperXmlWriter, rootMap);
		}
		
		dumpTableExcel(tableList);
		
	}
	
	
	
	private void dumpTableExcel(List<Table> tableList){
		
	}

}
