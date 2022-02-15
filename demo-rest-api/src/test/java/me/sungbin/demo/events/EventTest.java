package me.sungbin.demo.events;

import junitparams.JUnitParamsRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

@RunWith(JUnitParamsRunner.class)
public class EventTest {

    // 무료인지 아닌지 확인용 junit 파라미터 테스트
    private static Stream<Arguments> paramsForTestFree() {
        return Stream.of(
                Arguments.of(0, 0, true),
                Arguments.of(100, 0, false),
                Arguments.of(0, 100, false),
                Arguments.of(100, 200, false)
        );
    }

    // 오프라인인지 아닌지 확인하는 파라미터 테스트
    private static Stream<Arguments> paramsForTestOffLine() {
        return Stream.of(
                Arguments.of("강남", true),
                Arguments.of("", false),
                Arguments.of(null, false)
        );
    }

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

    @ParameterizedTest
    @MethodSource("paramsForTestFree")
    @DisplayName("이벤트가 공짜인지 아닌지 여부를 확인하는 테스트")
    void testFree(int basePrice, int maxPrice, boolean isFree) {
        // Given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        // When
        event.update();

        // Then
        assertEquals(isFree, event.isFree());
    }

    @ParameterizedTest
    @MethodSource("paramsForTestOffLine")
    @DisplayName("온라인이니지 오프라인인지 확인하는 테스트")
    void testOffLine(String location, boolean isOffLine) {
        // Given
        Event event = Event.builder()
                .location(location)
                .build();

        // When
        event.update();

        // Then
        assertEquals(isOffLine, event.isOffLine());
    }
}