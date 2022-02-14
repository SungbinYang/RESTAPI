package me.sungbin.demo.common;

import me.sungbin.demo.index.IndexController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.validation.BindingResult;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * packageName : me.sungbin.demo.common
 * fileName : ErrorsResource
 * author : rovert
 * date : 2022/02/09
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/09       rovert         최초 생성
 */

public class ErrorsResource extends Resource<BindingResult> {

    public ErrorsResource(BindingResult content, Link... links) {
        super(content, links);
        add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
    }
}
