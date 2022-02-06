package me.sungbin.demo.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * packageName : me.sungbin.demo.events
 * fileName : EventDto
 * author : rovert
 * date : 2022/02/06
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/06       rovert         최초 생성
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDto {

    private String name;

    private String description;

    private LocalDateTime beginEnrollmentDateTime;

    private LocalDateTime closeEnrollmentDateTime;

    private LocalDateTime beginEventDateTime;

    private LocalDateTime endEventDateTime;

    private String location; // (optional) : 이게 없으면 온라인 모임

    private int basePrice; // (optional)

    private int maxPrice; // (optional)

    private int limitOfEnrollment;
}
