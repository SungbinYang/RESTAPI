package me.sungbin.demo.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;

/**
 * packageName : me.sungbin.demo.events
 * fileName : EventValidator
 * author : rovert
 * date : 2022/02/06
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/06       rovert         최초 생성
 */

@Component
public class EventValidator {

    public void validate(EventDto eventDto, BindingResult bindingResult) {
        if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0) {
            bindingResult.rejectValue("basePrice", "wrong Value", "BasePrice is wrong.");
            bindingResult.rejectValue("maxPrice", "wrong Value", "MaxPrice is wrong.");
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        LocalDateTime beginEventDateTime = eventDto.getBeginEventDateTime();
        LocalDateTime closeEnrollmentDateTime = eventDto.getCloseEnrollmentDateTime();

        if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
                endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
                endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            bindingResult.rejectValue("endEventDateTime", "wrong Value", "endEventDateTime is wrong");
        }

        if (beginEventDateTime.isAfter(eventDto.getEndEventDateTime()) ||
                beginEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
                beginEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            bindingResult.rejectValue("beginEventDateTime", "wrong Value", "beginEventDateTime is wrong");
        }

        if (closeEnrollmentDateTime.isBefore(eventDto.getBeginEnrollmentDateTime()) ||
                closeEnrollmentDateTime.isAfter(eventDto.getEndEventDateTime()) ||
                closeEnrollmentDateTime.isAfter(eventDto.getBeginEventDateTime())) {
            bindingResult.rejectValue("closeEnrollmentDateTime", "wrong Value", "closeEnrollmentDateTime is wrong");
        }
    }
}
