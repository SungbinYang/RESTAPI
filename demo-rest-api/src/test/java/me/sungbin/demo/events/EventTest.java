package me.sungbin.demo.events;

import org.junit.jupiter.api.DisplayName;
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

    @Test
    @DisplayName("이벤트가 공짜인지 아닌지 여부를 확인하는 테스트")
    void testFree() {
        // Given
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();

        // When
        event.update();

        // Then
        assertTrue(event.isFree());

        // Given
        event = Event.builder()
                .basePrice(100)
                .maxPrice(0)
                .build();

        // When
        event.update();

        // Then
        assertFalse(event.isFree());

        // Given
        event = Event.builder()
                .basePrice(0)
                .maxPrice(100)
                .build();

        // When
        event.update();

        // Then
        assertFalse(event.isFree());
    }

    @Test
    @DisplayName("온라인이니지 오프라인인지 확인하는 테스트")
    void testOffLine() {
        // Given
        Event event = Event.builder()
                .location("강남역 토즈")
                .build();

        // When
        event.update();

        // Then
        assertTrue(event.isOffLine());

        // Given
        event = Event.builder().build();

        // When
        event.update();

        // Then
        assertFalse(event.isOffLine());
    }
}