package me.sungbin.demo.events;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * packageName : me.sungbin.demo.events
 * fileName : Event
 * author : rovert
 * date : 2022/02/03
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/03       rovert         최초 생성
 */

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Event {

    @Id @GeneratedValue
    private Integer id;

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

    private boolean offLine;

    private boolean free;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    public void update() {
        // update free
        this.free = this.basePrice == 0 && this.maxPrice == 0;

        // update offLine
        this.offLine = this.location != null && !this.location.isBlank();
    }
}
