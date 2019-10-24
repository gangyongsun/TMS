package cn.com.goldwind.kis.mybatis;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import cn.com.goldwind.kis.mybatis.page.MysqlDialect;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.until.LoggerUtils;
import cn.com.goldwind.kis.until.StringUtils;

public class BaseMybatisDao<T> extends SqlSessionDaoSupport {
	private String NAMESPACE;

	final static Class<? extends Object> SELF = BaseMybatisDao.class;

	protected final Log logger = LogFactory.getLog(BaseMybatisDao.class);

	/**
	 * 默认的查询Sql id
	 */
	final static String SQL_ID_FINDALL = "findAll";

	/**
	 * 默认的查询Count sql id
	 */
	final static String SQL_ID_FINDCOUNT = "findCount";

	public BaseMybatisDao() {
		try {
			Object genericClz = getClass().getGenericSuperclass();
			if (genericClz instanceof ParameterizedType) {
				Class<T> entityClass = (Class<T>) ((ParameterizedType) genericClz).getActualTypeArguments()[0];
				NAMESPACE = entityClass.getPackage().getName() + "." + entityClass.getSimpleName();
			}
		} catch (RuntimeException e) {
			LoggerUtils.error(SELF, "初始化失败，继承BaseMybatisDao，没有泛型！");
		}
	}

	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	
	public TableSplitResult findPage(String sqlId, String countId, Map<String, Object> paramMap, Integer pageNo, Integer pageSize) {
		TableSplitResult page = new TableSplitResult();
		pageNo = null == pageNo ? 1 : pageNo;
		pageSize = null == pageSize ? 10 : pageSize;
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		int offset = (page.getPageNo() - 1) * page.getPageSize();
		
		String page_sql = String.format(" limit  %s , %s ", offset, pageSize);
		paramMap.put("page_sql", page_sql);
		
		sqlId = String.format("%s.%s", NAMESPACE, sqlId);
		
		Configuration configuration = this.getSqlSession().getConfiguration();
		BoundSql boundSql = configuration.getMappedStatement(sqlId).getBoundSql(paramMap);
		String sqlcode = boundSql.getSql();
		LoggerUtils.fmtDebug(SELF, "findPage sql : %s", sqlcode);
		String countCode = "";
		BoundSql countSql = null;
		if (StringUtils.isBlank(countId)) {
			countCode = sqlcode;
			countSql = boundSql;
		} else {
			countId = String.format("%s.%s", NAMESPACE, countId);
			countSql = configuration.getMappedStatement(countId).getBoundSql(paramMap);
			countCode = countSql.getSql();
		}
		try {
//			Connection conn = this.getSqlSession().getConnection();
			
			SqlSessionTemplate st = (SqlSessionTemplate) getSqlSession();
			Connection conn = SqlSessionUtils.getSqlSession(st.getSqlSessionFactory(), st.getExecutorType(), st.getPersistenceExceptionTranslator()).getConnection();
			
//			System.out.println(conn.isClosed());
			
			List resultList = this.getSqlSession().selectList(sqlId, paramMap);
			page.setRows(resultList);
			
			// 处理Count
			PreparedStatement ps = getPreparedStatement4Count(countCode, countSql.getParameterMappings(), paramMap, conn);
			ps.execute();
			ResultSet set = ps.getResultSet();
			
			while (set.next()) {
				page.setTotal(set.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtils.error(SELF, "jdbc.error.code.findByPageBySqlId", e);
		}
		return page;
	}
	

	public TableSplitResult findPage(Map<String, Object> params, Integer pageNo, Integer pageSize) {
		return findPage(SQL_ID_FINDALL, SQL_ID_FINDCOUNT, params,pageNo, pageSize);
	}
	
	/**
	 * 组装
	 * 
	 * @param sql
	 * @param parameterMappingList
	 * @param params
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	private PreparedStatement getPreparedStatement(String sql, List<ParameterMapping> parameterMappingList, Map<String, Object> params, Connection conn) throws SQLException {
		// 分页根据数据库分页
		MysqlDialect mysqlDialect = new MysqlDialect();

		PreparedStatement ps = conn.prepareStatement(mysqlDialect.getCountSqlString(sql));
		int index = 1;
		for (int i = 0; i < parameterMappingList.size(); i++) {
			ps.setObject(index++, params.get(parameterMappingList.get(i).getProperty()));
		}
		return ps;
	}

	/**
	 * 分页查询Count 直接用用户自己写的Count sql
	 * 
	 * @param sql
	 * @param parameterMappingList
	 * @param params
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	private PreparedStatement getPreparedStatement4Count(String sql, List<ParameterMapping> parameterMappingList, Map<String, Object> params, Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(StringUtils.trim(sql));
		int index = 1;
		for (int i = 0; i < parameterMappingList.size(); i++) {
			ps.setObject(index++, params.get(parameterMappingList.get(i).getProperty()));
		}
		return ps;
	}

}
