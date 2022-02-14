package me.sungbin.demo.index;

import me.sungbin.demo.events.EventController;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * packageName : me.sungbin.demo.index
 * fileName : IndexController
 * author : rovert
 * date : 2022/02/09
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/09       rovert         최초 생성
 */

@RestController
public class IndexController {

    @GetMapping("/api")
    public ResourceSupport index() {
        var index = new ResourceSupport();
        index.add(linkTo(EventController.class).withRel("events"));

        return index;
    }
}
