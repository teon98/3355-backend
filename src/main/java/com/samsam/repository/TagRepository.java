package com.samsam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.DailyStampVO;
import com.samsam.vo.TagVO;

public interface TagRepository extends CrudRepository<TagVO, Integer> {
	public TagVO findByTagContent(String tagContent);
	
	@Query(value = "select tag_content from tags where rownum<=10 order By tag_count desc", nativeQuery = true)
	public List<String> findByTags();

	//오운완 태그가 아닌 태그 중 상위 9개 뽑아오기
	@Query(value = "SELECT * FROM ( select *  from tags order by tag_count desc ) where rownum < 10", nativeQuery = true)
	public List<TagVO> findAllHighTen();

}
