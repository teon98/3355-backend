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

}
