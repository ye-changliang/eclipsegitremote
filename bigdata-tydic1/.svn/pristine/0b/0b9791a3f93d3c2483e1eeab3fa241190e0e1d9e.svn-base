package com.tydic.bigdata.datasource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.logging.Logger;

public class JdbcTemplateDao {
	private static Logger log = Logger.getLogger("JdbcTemplateDao");
	
	public static BaseDao getBaseDao(com.tydic.bigdata.datasource.DataSource db) throws Exception {
		DataSource dataSource = DataSourceFactory.getDataSource(db);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

		BaseDao baseDao = new BaseDao();
		baseDao.setJdbcTemplate(jdbcTemplate);
		baseDao.setTransactionTemplate(transactionTemplate);
		return baseDao;
	}
}
