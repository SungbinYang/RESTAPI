package me.sungbin.demo.common;

import me.sungbin.demo.index.IndexController;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.BindingResult;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

public class ErrorsResource extends EntityModel<BindingResult> {

    public ErrorsResource(BindingResult content) {
        super(content);
        add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
    }
}
