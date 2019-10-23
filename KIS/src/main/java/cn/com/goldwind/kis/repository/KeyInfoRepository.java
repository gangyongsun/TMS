package cn.com.goldwind.kis.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.goldwind.kis.entity.KeyInfo;

public interface KeyInfoRepository extends JpaRepository<KeyInfo, String>, Serializable {

	/**
	 * 根据关键词的中or英文名进行查询
	 * 
	 * @param keyInfo
	 * @return
	 */
	@Query(value = "select * from t_key_info where chinese like CONCAT('%',:keyInfo,'%') or english like CONCAT('%',:keyInfo,'%')", nativeQuery = true)
	public List<KeyInfo> findByKeyInfo(@Param("keyInfo") String keyInfo);

	/**
	 * 搜索二级分类
	 * 
	 * @return
	 */
	@Query(value = "select distinct classification2 from t_key_info", nativeQuery = true)
	public List<String> findTermTypes();

	/**
	 * 按二级类型搜索
	 * 
	 * @param termType
	 * @return
	 */
	@Query(value = "select * from t_key_info where classification2 = :termType", nativeQuery = true)
	public List<KeyInfo> findByTermType(@Param("termType") String termType);

	/**
	 * 搜索二级分类术语的数量
	 * 
	 * @param termType
	 * @return
	 */
	@Query(value = "select count(1) from t_key_info where classification2= :termType", nativeQuery = true)
	public int getNumByType(@Param("termType") String termType);

	/**
	 * 根据ID搜索术语详细信息
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "select * from t_key_info where id= :id", nativeQuery = true)
	public KeyInfo findTermById(@Param("id") String id);

}
