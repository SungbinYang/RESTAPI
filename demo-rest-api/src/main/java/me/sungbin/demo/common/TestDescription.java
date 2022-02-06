package me.sungbin.demo.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * packageName : me.sungbin.demo.common
 * fileName : TestDescription
 * author : rovert
 * date : 2022/02/06
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/06       rovert         Junit4를 쓸경우 @DisplayName 대체 애노테이션
 *                                 Junit5에서는 설명용으로 대체
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface TestDescription {
    String value();
}
