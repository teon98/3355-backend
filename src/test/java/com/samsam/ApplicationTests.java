package com.samsam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.samsam.repository.TestVORepository;
import com.samsam.vo.TestVO;

@SpringBootTest
class ApplicationTests {
	
	@Autowired
	TestVORepository repo;

	@Test
	void test1() {
		TestVO t1 = TestVO.builder().testID("980914").testName("태영").build();
		repo.save(t1);
	}

}
