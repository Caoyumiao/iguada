package life.iGuaDa.community;

import life.iGuaDa.community.dto.PaginationDTO;
import life.iGuaDa.community.service.QuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {
	@Autowired
	QuestionService questionService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void questionTest(){
		PaginationDTO paginationDTO = questionService.listByCollectionUserId(1l, 0, 10);
		System.out.println(paginationDTO.getData().toString());
	}

}
