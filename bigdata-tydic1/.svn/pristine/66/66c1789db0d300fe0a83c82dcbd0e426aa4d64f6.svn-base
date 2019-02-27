package com.tydic.bigdata.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.util.TreeMap;

public class DataSourceFactory {
	private static TreeMap<String,ComboPooledDataSource> DATASOURCE_MAP = new TreeMap<String,ComboPooledDataSource>();
	
	private DataSource dataSource;
	
	public static ComboPooledDataSource getDataSource(DataSource d) throws Exception {
		if(DATASOURCE_MAP.get(d.getDataSourceName())==null){
			DataSourceFactory dsf = new DataSourceFactory();
			dsf.setDataSource(d);
			ComboPooledDataSource ds = dsf.createDataSource(d);
			if(ds!=null){
				DATASOURCE_MAP.put(d.getDataSourceName(),ds);
			}else{
				throw new Exception("build datasource fail,please check the jar file");
			}
		}
		return DATASOURCE_MAP.get(d.getDataSourceName());
	}
	
	private ComboPooledDataSource createDataSource(DataSource d){
		try {
			ComboPooledDataSource db = new ComboPooledDataSource();
			db.setJdbcUrl(this.getDataSource().getUrl());
			db.setUser(this.getDataSource().getUsername());
			db.setPassword(this.getDataSource().getPwd());
			db.setDriverClass(this.getDataSource().getDriver());
			db.setAcquireIncrement(5);
			db.setIdleConnectionTestPeriod(60);
			db.setCheckoutTimeout(60000);
			db.setInitialPoolSize(d.getMinIdle());
			db.setMaxPoolSize(d.getMaxIdle());
			db.setMaxStatements(100);
			db.setMaxIdleTime(20);
			db.setNumHelperThreads(5);
			return db;
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static TreeMap<String, ComboPooledDataSource> getDATASOURCE_MAP() {
		return DATASOURCE_MAP;
	}

	public static void setDATASOURCE_MAP(
			TreeMap<String, ComboPooledDataSource> dATASOURCE_MAP) {
		DATASOURCE_MAP = dATASOURCE_MAP;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private DataSourceFactory(){}
}
