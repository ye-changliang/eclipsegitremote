package com.tydic.bigdata.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

public class BaseDao{
	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionTemplate;

	//获取当前链接状态[连接池中活跃连接数，连接池中空闲连接数，连接池总连接数]
	public int[] getConnectionNum() throws SQLException {
		ComboPooledDataSource ds = (ComboPooledDataSource)this.getJdbcTemplate().getDataSource();
		return new int[]{ds.getNumBusyConnections(),ds.getNumIdleConnections(),ds.getNumConnections()};
	}

	//调整当前连接池大小
	public void setPoolMaxSize(int size){
		ComboPooledDataSource ds = (ComboPooledDataSource)this.getJdbcTemplate().getDataSource();
		ds.setMaxPoolSize(size);
	}

    //insert update delete
	public int execute(String sql) {
		int r = this.jdbcTemplate.update(sql);
		return r;
	}

	public int execute(String sql, Object[] params) {
		int r = this.jdbcTemplate.update(sql, params);
		return r;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public  void executeBatch(List<String> sql) {
		String[] ss = new String[sql.size()];
		for(int is=0 ; is<sql.size() ; is++){
			ss[is] = sql.get(is);
		}
		final String[] sqls = ss;
		this.transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try{
					jdbcTemplate.batchUpdate(sqls);
				}catch(Exception e){
					status.setRollbackOnly();
				}
			}
		});
	}
	
	public  void executeBatch(String sql, final List<Object[]> params) {
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			public int getBatchSize() {
				return params.size();
			}

			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				Object[] o = params.get(i);
				for (int y = 0; y < o.length; y++) {
					ps.setObject(y + 1, o[y]);
				}
			}

		});
	}

	public <V> List<V> getObject(String sql,Class<V> clazz){
		List<Map<String,String>> rows = this.getMap(sql);
		List<V> list = new ArrayList(rows.size());
		for(Map<String,String> m : rows) {
			list.add(convert(clazz, m));
		}
		return list;
	}

	public <V> List<V> getObject(String sql,Object[] params,Class<V> clazz){
		List<Map<String,String>> rows = this.getMap(sql,params);
		List<V> list = new ArrayList(rows.size());
		for(Map<String,String> m : rows) {
			list.add(convert(clazz, m));
		}
		return list;
	}

	public String getString(String sql,Object[] params){
		List<Map<String,String>> result = getMap(sql,params);
		if(result==null || result.size()==0){
			return null;
		}else{
			String s = null;
			Map<String,String> m = result.get(0);
			for(Map.Entry<String,String> me : m.entrySet()){
				s = me.getValue();
			}
			return s;
		}
	}

	public String getString(String sql){
		return getString(sql,null);
	}

	public int getInt(String sql,Object[] params){
		List<Map<String,String>> result = getMap(sql,params);
		if(result==null || result.size()==0){
			return -1;
		}else{
			int i = -1;
			Map<String,String> m = result.get(0);
			for(Map.Entry<String,String> me : m.entrySet()){
				i = Integer.parseInt(me.getValue());
				if(i>-1){
					break;
				}
			}
			return i;
		}
	}

	public int getInt(String sql){
		return getInt(sql,null);
	}

	public  List<Map<String,String>> getMap(String sql) {
		return getMap(sql,null);
	}

	public  List<Map<String,String>> getMap(String sql,Object[] params) {
		List<Map<String,String>> map = (List<Map<String,String>>) this.jdbcTemplate.query(sql,params,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						ResultSetMetaData rsd = rs.getMetaData();
						int colnum = rsd.getColumnCount();
						List<Map<String,String>> list = new ArrayList<Map<String,String>>();
						while (rs.next()) {
							Map<String, String> m = new HashMap<String, String>();
							for (int i = 1; i <= colnum; i++) {
								if(Types.CLOB==rsd.getColumnType(i)){
									Clob c = rs.getClob(i);
									try {
										String s = IOUtils.toString(c.getCharacterStream());
										m.put(rsd.getColumnLabel(i), s);
									}catch(Exception e){
										e.printStackTrace();
									}
								}else {
									m.put(rsd.getColumnLabel(i), rs.getString(rsd
											.getColumnLabel(i)));
								}
							}
							list.add(m);
						}
						return list;
					}
				});
		return map;
	}

	private static <V> V convert(Class<V> clazz, Map<String, String> m) {
		V obj = null;
		try {
			obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();

			String setMethod;
			Method method;
			for(Field f : fields){
				if(m.get(f.getName())!=null) {
					setMethod = "set" + StringUtils.capitalize(f.getName());
					method = clazz.getMethod(setMethod, f.getType());
					if(f.getType()==Integer.class){
						method.invoke(obj, Integer.parseInt(m.get(f.getName())));
					}else if (f.getType()==Double.class){
						method.invoke(obj, Double.parseDouble(m.get(f.getName())));
					}else if (f.getType()==Float.class){
						method.invoke(obj, Float.parseFloat(m.get(f.getName())));
					}else {
						method.invoke(obj, m.get(f.getName()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
}
