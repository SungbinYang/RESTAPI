package me.sungbin.demo.events;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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

public class EventResource extends Resource<Event> {

    public EventResource(Event content, Link... links) {
        super(content, links);
        add(linkTo(EventController.class).slash(content.getId()).withSelfRel());
    }
}
