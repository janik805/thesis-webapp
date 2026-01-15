package com.awesome.thesis;

import com.awesome.thesis.configuration.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class ThesisApplicationTests {

	@Test
	void contextLoads() {
	}

}
