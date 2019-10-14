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

	@Query(value = "select distinct key_info_function from t_key_info", nativeQuery = true)
	public List<String> findTermTypes();

	@Query(value = "select * from t_key_info where key_info_function = :termType", nativeQuery = true)
	public List<KeyInfo> findByTermType(@Param("termType") String termType);

	@Query(value = "select count(1) from t_key_info where key_info_function= :termType", nativeQuery = true)
	public int getNumByType(@Param("termType") String termType);

	@Query(value = "select * from t_key_info where key_info_id= :id", nativeQuery = true)
	public KeyInfo findTermById(@Param("id") String id);

}
