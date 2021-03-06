package me.sungbin.demo.events;

import me.sungbin.demo.accounts.Account;
import me.sungbin.demo.accounts.AccountRepository;
import me.sungbin.demo.accounts.AccountRole;
import me.sungbin.demo.accounts.AccountService;
import me.sungbin.demo.common.AppProperties;
import me.sungbin.demo.common.BaseControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
 * 2022/02/06       rovert         ?????? ??????
 */

class EventControllerTest extends BaseControllerTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AppProperties appProperties;

    @BeforeEach
    void setUp() {
        this.eventRepository.deleteAll();
        this.accountRepository.deleteAll();
    }

    @Test
    @DisplayName("??????????????? ???????????? ???????????? ?????????")
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
                .location("????????? D2 ???????????? ?????????")
                .build();

        mockMvc.perform(post("/api/events")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON+ ";charset=UTF-8")
                        .accept(MediaTypes.HAL_JSON + ";charset=UTF-8")
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("free").value(false))
                .andExpect(jsonPath("offLine").value(true))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-events").exists())
                .andExpect(jsonPath("_links.update-event").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("create-event",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("query-events").description("link to query-events"),
                                linkWithRel("update-event").description("link to update an existing events"),
                                linkWithRel("profile").description("link to rest api document")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header : application/hal+json"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header : application/json;charset=UTF-8")
                        ),
                        requestFields(
                                fieldWithPath("name").description("name of new event"),
                                fieldWithPath("description").description("description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                fieldWithPath("beginEventDateTime").description("date time of start of new event"),
                                fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                fieldWithPath("location").description("location of new event"),
                                fieldWithPath("basePrice").description("basePrice of new event"),
                                fieldWithPath("maxPrice").description("maxPrice of new event"),
                                fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header : URL where you can view the newly created event"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type : application/hal+json;charset=UTF-8")
                        ),
                        responseFields(
                                fieldWithPath("id").description("identifier of new event"),
                                fieldWithPath("name").description("name of new event"),
                                fieldWithPath("description").description("description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                fieldWithPath("beginEventDateTime").description("date time of start of new event"),
                                fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                fieldWithPath("location").description("location of new event"),
                                fieldWithPath("basePrice").description("basePrice of new event"),
                                fieldWithPath("maxPrice").description("maxPrice of new event"),
                                fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event"),
                                fieldWithPath("offLine").description("it tells if this event is offLine event or not"),
                                fieldWithPath("free").description("it tells if this event is free or not"),
                                fieldWithPath("eventStatus").description("event status"),
                                fieldWithPath("manager.id").description("manager id of new event"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.query-events.href").description("link to query event list"),
                                fieldWithPath("_links.update-event.href").description("link to update existing event"),
                                fieldWithPath("_links.profile.href").description("link to rest api document")
                        )
                ));
    }

    private String getBearerToken(boolean needToCreateAccount) throws Exception {
        return "Bearer " + getAccessToken(needToCreateAccount);
    }

    private String getAccessToken(boolean needToCreateAccount) throws Exception {
        //Given
        if (needToCreateAccount) {
            createAccount();
        }
        ResultActions perform = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .param("username", appProperties.getUserUsername())
                .param("password", appProperties.getUserPassword())
                .param("grant_type", "password"));
        MockHttpServletResponse response = perform.andReturn().getResponse();
        String responseBody = response.getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(responseBody).get("access_token").toString();
    }

    private Account createAccount() {
        Account sungbin = Account.builder()
                .email(appProperties.getUserUsername())
                .password(appProperties.getUserPassword())
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        return this.accountService.saveAccount(sungbin);
    }

    @Test
    @DisplayName("???????????? ??? ?????? ?????? ????????? ????????? ????????? ???????????? ?????????")
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
                .location("????????? D2 ???????????? ?????????")
                .free(true)
                .offLine(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        mockMvc.perform(post("/api/events")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("?????? ?????? ???????????? ????????? ????????? ???????????? ?????????")
    void createEvent_BadRequest_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        this.mockMvc.perform(post("/api/events")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("?????? ?????? ?????? ??? ????????? ????????? ???????????? ?????????")
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
                .location("????????? D2 ???????????? ?????????")
                .build();

        this.mockMvc.perform(post("/api/events")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].codes").exists())
                .andExpect(jsonPath("_links.index").exists());
    }

    @Test
    @DisplayName("30?????? ???????????? 10?????? ????????? ????????? ????????????")
    void queryEvents() throws Exception {
        // given
        IntStream.range(0, 30).forEach(this::generateEvent);

        // when
        this.mockMvc.perform(get("/api/events")
                        .param("page", "1")
                        .param("size", "10")
                        .param("sort", "name,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.first").exists())
                .andExpect(jsonPath("_links.prev").exists())
                .andExpect(jsonPath("_links.next").exists())
                .andExpect(jsonPath("_links.last").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(jsonPath("page.size").exists())
                .andExpect(jsonPath("page.totalElements").exists())
                .andExpect(jsonPath("page.totalPages").exists())
                .andExpect(jsonPath("page.number").exists())
                .andDo(document("query-events",
                        links(
                                linkWithRel("profile").description("Link to profile"),
                                linkWithRel("self").description("Link to self"),
                                linkWithRel("first").description("Link to get an event"),
                                linkWithRel("next").description("Link to next page"),
                                linkWithRel("last").description("Link to last page"),
                                linkWithRel("prev").description("Link to prev page")
                        ),
                        requestParameters(
                                parameterWithName("page").description("page to retrieve, begin with and default is 0").optional(),
                                parameterWithName("size").description("Size of the page to retrieve, default 10").optional(),
                                parameterWithName("sort").description("How to sort elements by some criteria").optional()
                        ),
                        responseFields(
                                fieldWithPath("_embedded.eventList[0].id").description("identifier of new event"),
                                fieldWithPath("_embedded.eventList[0].name").description("name of new event"),
                                fieldWithPath("_embedded.eventList[0].description").description("description of new event"),
                                fieldWithPath("_embedded.eventList[0].beginEnrollmentDateTime").description("date time of begin of new event"),
                                fieldWithPath("_embedded.eventList[0].closeEnrollmentDateTime").description("date time of close of new event"),
                                fieldWithPath("_embedded.eventList[0].beginEventDateTime").description("date time of start of new event"),
                                fieldWithPath("_embedded.eventList[0].endEventDateTime").description("date time of end of new event"),
                                fieldWithPath("_embedded.eventList[0].location").description("location of new event"),
                                fieldWithPath("_embedded.eventList[0].basePrice").description("basePrice of new event"),
                                fieldWithPath("_embedded.eventList[0].maxPrice").description("maxPrice of new event"),
                                fieldWithPath("_embedded.eventList[0].limitOfEnrollment").description("limitOfEnrollment of new event"),
                                fieldWithPath("_embedded.eventList[0].offLine").description("it tells if this event is offLine event or not"),
                                fieldWithPath("_embedded.eventList[0].free").description("it tells if this event is free or not"),
                                fieldWithPath("_embedded.eventList[0].eventStatus").description("event status"),
                                fieldWithPath("_embedded.eventList[0]manager").description("manager of new event"),
                                fieldWithPath("_embedded.eventList[0]._links.self.href").description("each event link"),
                                fieldWithPath("_links.first.href").description("link to first"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.prev.href").description("link to prev"),
                                fieldWithPath("_links.next.href").description("link to next"),
                                fieldWithPath("_links.last.href").description("link to last"),
                                fieldWithPath("_links.profile.href").description("link to api document"),
                                fieldWithPath("page.number").description("The number of this page."),
                                fieldWithPath("page.size").description("The size of this page."),
                                fieldWithPath("page.totalPages").description("The total number of pages."),
                                fieldWithPath("page.totalElements").description("The total number of results.")
                        )
                ));
    }

    @Test
    @DisplayName("30?????? ???????????? 10?????? ????????? ????????? ???????????? + ???????????? ??????")
    void queryEventsWithAuthentication() throws Exception {
        // given
        IntStream.range(0, 30).forEach(this::generateEvent);

        // when
        this.mockMvc.perform(get("/api/events")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .param("page", "1")
                        .param("size", "10")
                        .param("sort", "name,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.first").exists())
                .andExpect(jsonPath("_links.prev").exists())
                .andExpect(jsonPath("_links.next").exists())
                .andExpect(jsonPath("_links.last").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(jsonPath("_links.create-event").exists())
                .andExpect(jsonPath("page.size").exists())
                .andExpect(jsonPath("page.totalElements").exists())
                .andExpect(jsonPath("page.totalPages").exists())
                .andExpect(jsonPath("page.number").exists())
                .andDo(document("query-events",
                        links(
                                linkWithRel("profile").description("Link to profile"),
                                linkWithRel("self").description("Link to self"),
                                linkWithRel("first").description("Link to get an event"),
                                linkWithRel("next").description("Link to next page"),
                                linkWithRel("last").description("Link to last page"),
                                linkWithRel("prev").description("Link to prev page"),
                                linkWithRel("create-event").description("when user login, user create event")
                        ),
                        requestParameters(
                                parameterWithName("page").description("page to retrieve, begin with and default is 0").optional(),
                                parameterWithName("size").description("Size of the page to retrieve, default 10").optional(),
                                parameterWithName("sort").description("How to sort elements by some criteria").optional()
                        ),
                        responseFields(
                                fieldWithPath("_embedded.eventList[0].id").description("identifier of new event"),
                                fieldWithPath("_embedded.eventList[0].name").description("name of new event"),
                                fieldWithPath("_embedded.eventList[0].description").description("description of new event"),
                                fieldWithPath("_embedded.eventList[0].beginEnrollmentDateTime").description("date time of begin of new event"),
                                fieldWithPath("_embedded.eventList[0].closeEnrollmentDateTime").description("date time of close of new event"),
                                fieldWithPath("_embedded.eventList[0].beginEventDateTime").description("date time of start of new event"),
                                fieldWithPath("_embedded.eventList[0].endEventDateTime").description("date time of end of new event"),
                                fieldWithPath("_embedded.eventList[0].location").description("location of new event"),
                                fieldWithPath("_embedded.eventList[0].basePrice").description("basePrice of new event"),
                                fieldWithPath("_embedded.eventList[0].maxPrice").description("maxPrice of new event"),
                                fieldWithPath("_embedded.eventList[0].limitOfEnrollment").description("limitOfEnrollment of new event"),
                                fieldWithPath("_embedded.eventList[0].offLine").description("it tells if this event is offLine event or not"),
                                fieldWithPath("_embedded.eventList[0].free").description("it tells if this event is free or not"),
                                fieldWithPath("_embedded.eventList[0].eventStatus").description("event status"),
                                fieldWithPath("_embedded.eventList[0]manager").description("manager of new event"),
                                fieldWithPath("_embedded.eventList[0]._links.self.href").description("each event link"),
                                fieldWithPath("_links.first.href").description("link to first"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.prev.href").description("link to prev"),
                                fieldWithPath("_links.next.href").description("link to next"),
                                fieldWithPath("_links.last.href").description("link to last"),
                                fieldWithPath("_links.profile.href").description("link to api document"),
                                fieldWithPath("_links.create-event.href").description("when user login, user create event link"),
                                fieldWithPath("page.number").description("The number of this page."),
                                fieldWithPath("page.size").description("The size of this page."),
                                fieldWithPath("page.totalPages").description("The total number of pages."),
                                fieldWithPath("page.totalElements").description("The total number of results.")
                        )
                ));
    }

    @Test
    @DisplayName("????????? ???????????? ?????? ????????????")
    void getEvent() throws Exception {
        // Given
        Account account = this.createAccount();
        Event event = this.generateEvent(100, account);

        // when & Then
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/events/{id}", event.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-event",
                        links(
                                linkWithRel("self").description("link to this event."),
                                linkWithRel("profile").description("link to profile.")
                        ),
                        pathParameters(
                                parameterWithName("id").description("identifier of an Event.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("identifier of new event"),
                                fieldWithPath("name").description("name of new event"),
                                fieldWithPath("description").description("description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                fieldWithPath("beginEventDateTime").description("date time of start of new event"),
                                fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                fieldWithPath("location").description("location of new event"),
                                fieldWithPath("basePrice").description("basePrice of new event"),
                                fieldWithPath("maxPrice").description("maxPrice of new event"),
                                fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event"),
                                fieldWithPath("offLine").description("it tells if this event is offLine event or not"),
                                fieldWithPath("free").description("it tells if this event is free or not"),
                                fieldWithPath("manager.id").description("manager id of new event"),
                                fieldWithPath("eventStatus").description("event status"),
                                fieldWithPath("_links.self.href").description("each event link"),
                                fieldWithPath("_links.profile.href").description("each api document event link")
                        )
                ));
    }

    @Test
    @DisplayName("?????? ???????????? ???????????? ??? 404?????? ??????")
    void getEvent404() throws Exception {
        // Given
        int noneExistingId = 1;

        // when & Then
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/event/{id}", noneExistingId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("get-event-fail",
                        pathParameters(
                                parameterWithName("id").description("identifier of an Event.")
                        )
                ));
    }

    @Test
    @DisplayName("???????????? ??????????????? ????????????")
    void updateEvent() throws Exception {
        // given
        Account account = this.createAccount();
        Event event = this.generateEvent(200, account);
        String eventName = "Update Event";
        EventDto eventDto = this.modelMapper.map(event, EventDto.class);

        eventDto.setName(eventName);

        // when & then
        this.mockMvc.perform(put("/api/events/{id}", event.getId())
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(false))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(eventName))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("update-event",
                        links(
                                linkWithRel("self").description("link to this event."),
                                linkWithRel("profile").description("link to profile.")
                        ),
                        requestFields(
                                fieldWithPath("name").description("name of new event"),
                                fieldWithPath("description").description("description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                fieldWithPath("beginEventDateTime").description("date time of start of new event"),
                                fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                fieldWithPath("location").description("location of new event"),
                                fieldWithPath("basePrice").description("basePrice of new event"),
                                fieldWithPath("maxPrice").description("maxPrice of new event"),
                                fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event")
                        ),
                        responseFields(
                                fieldWithPath("id").description("identifier of new event"),
                                fieldWithPath("name").description("name of new event"),
                                fieldWithPath("description").description("description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                fieldWithPath("beginEventDateTime").description("date time of start of new event"),
                                fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                fieldWithPath("location").description("location of new event"),
                                fieldWithPath("basePrice").description("basePrice of new event"),
                                fieldWithPath("maxPrice").description("maxPrice of new event"),
                                fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event"),
                                fieldWithPath("offLine").description("it tells if this event is offLine event or not"),
                                fieldWithPath("free").description("it tells if this event is free or not"),
                                fieldWithPath("manager.id").description("manager id of new event"),
                                fieldWithPath("eventStatus").description("event status"),
                                fieldWithPath("_links.self.href").description("each event link"),
                                fieldWithPath("_links.profile.href").description("each api document event link")
                        )
                ));
    }

    @Test
    @DisplayName("?????? ?????? ???????????? ????????? ????????? ?????? ??????")
    void updateEvent_400_empty() throws Exception {
        // given
        Event event = this.generateEvent(200);
        EventDto eventDto = new EventDto();

        System.out.println(eventDto);

        // when & then
        this.mockMvc.perform(put("/api/events/{id}", event.getId())
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("?????? ?????? ????????? ????????? ????????? ?????? ??????")
    void updateEvent_400_wrong() throws Exception {
        // given
        Event event = this.generateEvent(200);
        EventDto eventDto = this.modelMapper.map(event, EventDto.class);
        eventDto.setBasePrice(20000);
        eventDto.setMaxPrice(1000);

        // when & then
        this.mockMvc.perform(put("/api/events/{id}", event.getId())
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("???????????? ?????? ????????? ?????? ??????")
    void updateEvent_404() throws Exception {
        // given
        Event event = this.generateEvent(200);
        EventDto eventDto = this.modelMapper.map(event, EventDto.class);

        // when & then
        this.mockMvc.perform(put("/api/events/21312321")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private Event generateEvent(int index) {
        Event event = buildEvent(index);

        return this.eventRepository.save(event);
    }

    private Event generateEvent(int index, Account account) {
        Event event = buildEvent(index);
        event.setManager(account);

        return this.eventRepository.save(event);
    }

    private Event buildEvent(int index) {
        return Event.builder()
                .name("event " + index)
                .description("test event")
                .beginEnrollmentDateTime(LocalDateTime.of(2022, 2, 6, 10, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 2, 7, 10, 21))
                .beginEventDateTime(LocalDateTime.of(2022, 2, 8, 10, 21))
                .endEventDateTime(LocalDateTime.of(2022, 2, 9, 10, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("????????? D2 ???????????? ?????????")
                .free(false)
                .offLine(true)
                .eventStatus(EventStatus.DRAFT)
                .build();
    }
}