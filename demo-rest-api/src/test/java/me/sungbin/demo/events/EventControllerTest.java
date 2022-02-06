package me.sungbin.demo.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sungbin.demo.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * packageName : me.sungbin.demo.events
 * fileName : EventControllerTest
 * author : rovert
 * date : 2022/02/06
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/06       rovert         최초 생성
 */

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("이벤트 생성")
    @TestDescription("정상적으로 이벤트를 생성하는 테스트")
    void createEvent() throws Exception {
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2022, 2, 6, 10, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 2, 7, 10, 21))
                .beginEventDateTime(LocalDateTime.of(2022, 2, 8, 10, 21))
                .endEventDateTime(LocalDateTime.of(2022, 2, 9, 10, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));
    }

    @Test
    @DisplayName("이벤트 생성 시, bad request 응답")
    @TestDescription("입력받을 수 없는 값을 사용한 경우에 에러가 발생하는 테스트")
    void createEvent_bad_request() throws Exception {
        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2022, 2, 6, 10, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 2, 7, 10, 21))
                .beginEventDateTime(LocalDateTime.of(2022, 2, 8, 10, 21))
                .endEventDateTime(LocalDateTime.of(2022, 2, 9, 10, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .free(true)
                .offLine(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("이벤트 생성 시, 입력값이 비어있어서 bad request 응답 처리")
    @TestDescription("입력 값이 비어있는 경우에 에러가 발생하는 테스트")
    void createEvent_BadRequest_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        this.mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("이벤트 생성 시, 입력값 오류로 bad request 응답 처리")
    @TestDescription("입력 값이 잘못 된 경우에 에러가 발생하는 테스트")
    void createEvent_BadRequest_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2022, 2, 26, 10, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 2, 25, 10, 21))
                .beginEventDateTime(LocalDateTime.of(2022, 2, 24, 10, 21))
                .endEventDateTime(LocalDateTime.of(2022, 2, 23, 10, 21))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .build();

        this.mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest());
    }
}