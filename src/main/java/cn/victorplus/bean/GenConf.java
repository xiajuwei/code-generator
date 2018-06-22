package cn.victorplus.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GenConf extends BaseEntity {
    @Value("${output.dir}")
    private String outputDir;
    @Value("${table.include}")
    private String tableInclude;
    @Value("${table.ignored}")
    private String tableIgnored;
    @Value("${common.bean.package}")
    private String commonBeanPackage;
    @Value("${common.mapper.package}")
    private String commonMapperPackage;
    @Value("${base.db.bean.package}")
    private String baseDbBeanPackage;
    @Value("${base.mapper.package}")
    private String baseMapperPackage;
    @Value("${bean.mapper.package}")
    private String beanMapperPackage;
    @Value("${schema}")
    private String schema;

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getTableInclude() {
        return tableInclude;
    }

    public void setTableInclude(String tableInclude) {
        this.tableInclude = tableInclude;
    }

    public String getTableIgnored() {
        return tableIgnored;
    }

    public void setTableIgnored(String tableIgnored) {
        this.tableIgnored = tableIgnored;
    }

    public String getCommonBeanPackage() {
        return commonBeanPackage;
    }

    public String getCommonMapperPackage() {
        return commonMapperPackage;
    }

    public void setCommonMapperPackage(String commonMapperPackage) {
        this.commonMapperPackage = commonMapperPackage;
    }

    public void setCommonBeanPackage(String commonBeanPackage) {
        this.commonBeanPackage = commonBeanPackage;
    }

    public String getBaseDbBeanPackage() {
        return baseDbBeanPackage;
    }

    public void setBaseDbBeanPackage(String baseDbBeanPackage) {
        this.baseDbBeanPackage = baseDbBeanPackage;
    }

    public String getBaseMapperPackage() {
        return baseMapperPackage;
    }

    public void setBaseMapperPackage(String baseMapperPackage) {
        this.baseMapperPackage = baseMapperPackage;
    }

    public String getBeanMapperPackage() {
        return beanMapperPackage;
    }

    public void setBeanMapperPackage(String beanMapperPackage) {
        this.beanMapperPackage = beanMapperPackage;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }


}
