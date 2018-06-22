package cn.victorplus.bean;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import cn.victorplus.util.StrUtil;

public class Table extends BaseEntity {
    private String tableName;
    private String tableComment;
    private String tableRows;
    private String dataLength;

    private String beanName;
    private String baseBeanName;
    private List<Column> columnList;
    private List<Column> propertyList;
    private List<String> pkColumnList;
    private List<String> pkPropertyList;
    private Set<String> importBeanList = Sets.newHashSet();

    public String getBeanName() {
        if (tableName.contains("_")) {
            if (tableName.startsWith("r_") || tableName.startsWith("R_")) {
                beanName = StrUtil.toCamelStyle(StrUtil.substringAfter(tableName.toLowerCase(), "r_"));
            } else {
                beanName = StrUtil.toCamelStyle(tableName);
            }
        } else {
            beanName = tableName;
        }
        beanName = StrUtil.capitalize(beanName);
        return beanName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getTableRows() {
        return tableRows;
    }

    public void setTableRows(String tableRows) {
        this.tableRows = tableRows;
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength;
    }

    public String getBaseBeanName() {
        return baseBeanName;
    }

    public void setBaseBeanName(String baseBeanName) {
        this.baseBeanName = baseBeanName;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public List<Column> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<Column> propertyList) {
        this.propertyList = propertyList;
    }

    public List<String> getPkColumnList() {
        return pkColumnList;
    }

    public void setPkColumnList(List<String> pkColumnList) {
        this.pkColumnList = pkColumnList;
    }

    public List<String> getPkPropertyList() {
        return pkPropertyList;
    }

    public void setPkPropertyList(List<String> pkPropertyList) {
        this.pkPropertyList = pkPropertyList;
    }

    public Set<String> getImportBeanList() {
        return importBeanList;
    }

    public void setImportBeanList(Set<String> importBeanList) {
        this.importBeanList = importBeanList;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

}
