package me.sungbin.demo.events;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName : me.sungbin.demo.events
 * fileName : EventTest
 * author : rovert
 * date : 2022/02/03
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/03       rovert         최초 생성
 */


class EventTest {

    @Test
    void builder() {
        Event event = Event.builder()
                .name("rest api")
                .description("rest api development with spring")
                .build();
        assertNotNull(event);

        assertEquals("rest api",event.getName());
        assertEquals("rest api development with spring", event.getDescription());
    }

    @Test
    void javaBean() {
        // Given
        String name = "Event";
        String description = "spring";

        // When
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        // Then
        assertEquals(name, event.getName());
        assertEquals(description, event.getDescription());
    }
}