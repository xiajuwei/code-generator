package cn.victorplus.service;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import cn.victorplus.bean.Column;
import cn.victorplus.bean.GenConf;
import cn.victorplus.bean.Table;
import cn.victorplus.dao.TableDao;
import cn.victorplus.util.ListUtil;
import cn.victorplus.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Service
public class TableService {

	private static final Logger logger = LoggerFactory.getLogger(TableService.class);

	@Resource
	private TableDao tableDao;
	@Resource
	private GenConf genConf;

	private static final String BASE_ENTITY = "BaseEntity";
	private static final String ID_ENTITY = "IdEntity";
	private static final String DB_ENTITY = "DbEntity";
	private static final String U_ENTITY = "UEntity";

	public List<Table> getTableInfoList() {
		List<Table> dataList = tableDao.getTableList(genConf.getSchema(),genConf.getTableInclude());
		for (Table data : dataList) {
			data.setColumnList(tableDao.getColumnList(genConf.getSchema(), data.getTableName()));
			data.setPkColumnList(tableDao.getPKList(genConf.getSchema(), data.getTableName()));
			if (ListUtil.isEmpty(data.getPkColumnList())) {
				logger.error("table has not PK, table = {}", data.getTableName());
			}

			List<String> pkPropertyList = Lists.newArrayList();
			for (String pk : data.getPkColumnList()) {
				pkPropertyList.add(StrUtil.toCamelStyle(pk));
			}

			Set<String> propertySet = Sets.newHashSet();
			Set<String> propertyTypeSet = Sets.newHashSet();
			for (Column column : data.getColumnList()) {
				propertySet.add(column.getPropertyName());
				propertyTypeSet.add(column.getPropertyType());
			}
			data.setPkPropertyList(pkPropertyList);

			data.setBaseBeanName(BASE_ENTITY);
			if (propertySet.contains("id")) {
				data.setBaseBeanName(ID_ENTITY);
				data.getImportBeanList().add(genConf.getCommonBeanPackage() + "." + ID_ENTITY);
			}
			if (propertySet.contains("id") && propertySet.contains("created") && propertySet.contains("modified")) {
				data.setBaseBeanName(DB_ENTITY);
				data.getImportBeanList().add(genConf.getCommonBeanPackage() + "." + DB_ENTITY);

				data.getImportBeanList().remove(genConf.getCommonBeanPackage() + "." + ID_ENTITY);
			}
			if (propertySet.contains("id") && propertySet.contains("cuid") && propertySet.contains("muid") && propertySet.contains("created") && propertySet.contains("modified")) {
				data.setBaseBeanName(U_ENTITY);
				data.getImportBeanList().add(genConf.getCommonBeanPackage() + "." + U_ENTITY);

				data.getImportBeanList().remove(genConf.getCommonBeanPackage() + "." + ID_ENTITY);
				data.getImportBeanList().remove(genConf.getCommonBeanPackage() + "." + DB_ENTITY);
			}

			if (BASE_ENTITY.equals(data.getBaseBeanName())) {
				data.getImportBeanList().add(genConf.getCommonBeanPackage() + "." + BASE_ENTITY);
			}

			if (propertyTypeSet.contains("BigDecimal")) {
				data.getImportBeanList().add("java.math.BigDecimal");
			}
			if (propertyTypeSet.contains("Date")) {
				data.getImportBeanList().add("java.util.Date");
			}

			if (DB_ENTITY.equals(data.getBaseBeanName())) {
				data.getImportBeanList().remove("java.util.Date");
				for (Column column : data.getColumnList()) {
					if (!"created".equals(column.getPropertyName()) && !"modified".equals(column.getPropertyName()) && column.getPropertyType().endsWith("Date")) {
						data.getImportBeanList().add("java.util.Date");
					}
				}
			}

			List<Column> propertyList = Lists.newArrayList();
			if (ID_ENTITY.equals(data.getBaseBeanName())) {
				for (Column column : data.getColumnList()) {
					if ("id".equals(column.getPropertyName())) {
						continue;
					}
					propertyList.add(column);
				}
			} else if (DB_ENTITY.equals(data.getBaseBeanName())) {
				for (Column column : data.getColumnList()) {
					String propertyName = column.getPropertyName();
					if ("id".equals(propertyName) || "created".equals(propertyName) || "modified".equals(propertyName)) {
						continue;
					}
					propertyList.add(column);
				}
			} else if (U_ENTITY.equals(data.getBaseBeanName())) {
				for (Column column : data.getColumnList()) {
					String propertyName = column.getPropertyName();
					if ("id".equals(propertyName) || "created".equals(propertyName) || "modified".equals(propertyName) || "cuid".equals(propertyName) || "muid".equals(propertyName)) {
						continue;
					}
					propertyList.add(column);
				}
			} else {
				propertyList = data.getColumnList();
			}

			data.setPropertyList(propertyList);

		}
		return dataList;
	}

}
