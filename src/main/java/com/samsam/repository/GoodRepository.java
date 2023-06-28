package com.samsam.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.GoodVO;
import com.samsam.vo.PostVO;
import com.samsam.vo.UserVO;

public interface GoodRepository extends CrudRepository<GoodVO, Integer> {
	@Query(value= "select count(*) from Goods where post_no = ?1", nativeQuery = true)
	int findByGoodsCount(int post_no);
	
	// 좋아요 갯수 내림차순
	@Query(value = "SELECT g.post_no AS postNo, COUNT(*) AS likeCount FROM Goods g GROUP BY g.post_no ORDER BY likeCount DESC", nativeQuery = true)
	List<Map<String, Object>> findLikeCountByPostNo();
	
	GoodVO findByUserAndPost(UserVO user, PostVO post);
}
