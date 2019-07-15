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
	 * @param findContent
	 * @return
	 */
	@Query(value = "select * from t_key_info where chinese like CONCAT('%',:findContent,'%') or english like CONCAT('%',:findContent,'%')", nativeQuery = true)
	public List<KeyInfo> search(@Param("findContent") String findContent);

}
