package me.sungbin.demo.index;

import me.sungbin.demo.common.BaseControllerTest;
import me.sungbin.demo.common.TestDescription;
import org.junit.Test;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName : me.sungbin.demo.index
 * fileName : IndexControllerTest
 * author : rovert
 * date : 2022/02/09
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/09       rovert         최초 생성
 */

public class IndexControllerTest extends BaseControllerTest {

    @Test
    @TestDescription("인덱스 페이지 진입 테스트")
    public void index() throws Exception {
        this.mockMvc.perform(get("/api/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.events").exists())
                .andDo(document("index",
                        links(
                                linkWithRel("events").description("The resource event")
                        )
                ));
    }
}