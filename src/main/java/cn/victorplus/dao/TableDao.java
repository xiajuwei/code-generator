package cn.victorplus.dao;

import java.util.List;

import javax.annotation.Resource;

import cn.victorplus.bean.Table;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import cn.victorplus.bean.Column;

@Repository
public class TableDao {
    private static final Logger logger = LoggerFactory.getLogger(TableDao.class);
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Table> getTableList(String schema, String include) {
        List<Table> dataList = Lists.newArrayList();
        try {
            String sql = "SELECT table_name,table_comment,table_rows,data_length FROM information_schema.tables WHERE table_schema = '" + schema + "'";
            if (StringUtils.isNotBlank(include)) {
                sql += "and table_name like '" + include + "'";
            }
            logger.info("excute sql {} ", sql);
            dataList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Table>(Table.class));
        } catch (Exception e) {
            logger.error("Table list fail schema = {} ", schema, e);
        }
        return dataList;
    }

    public List<Column> getColumnList(String schema, String tableName) {
        List<Column> dataList = Lists.newArrayList();
        try {
            String sql = "select column_name,data_type,column_comment from information_schema.COLUMNS where TABLE_SCHEMA='" + schema + "' and table_name='" + tableName + "'";
            logger.info("excute sql {} ", sql);
            dataList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Column>(Column.class));
        } catch (Exception e) {
            logger.error("Column list fail schema = {} ,table = {} ", schema, tableName, e);
        }
        return dataList;
    }

    public List<String> getPKList(String schema, String tableName) {
        List<String> dataList = null;
        try {
            StringBuffer sb = new StringBuffer("");
            sb.append("SELECT COLUMN_NAME              ");
            sb.append("FROM INFORMATION_SCHEMA.COLUMNS ");
            sb.append("WHERE TABLE_SCHEMA = '" + schema + "'     ");
            sb.append("   AND TABLE_NAME = '" + tableName + "'   ");
            sb.append("   AND COLUMN_KEY = 'PRI';      ");
            dataList = jdbcTemplate.queryForList(sb.toString(), String.class);
        } catch (Exception e) {
            logger.error("Tabel pk list fail db = {} ,table = {} ", schema, tableName, e);
        }
        return dataList;
    }

}
