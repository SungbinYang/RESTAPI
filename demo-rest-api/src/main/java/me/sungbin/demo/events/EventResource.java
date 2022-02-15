package me.sungbin.demo.events;

import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * packageName : me.sungbin.demo.events
 * fileName : EventResource
 * author : rovert
 * date : 2022/02/07
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/07       rovert         최초 생성
 */

public class EventResource extends EntityModel<Event> {

    public EventResource(Event content) {
        super(content);
        add(linkTo(EventController.class).slash(content.getId()).withSelfRel());
    }
}
