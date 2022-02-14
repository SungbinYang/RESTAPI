package me.sungbin.demo.common;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

/**
 * packageName : me.sungbin.demo.common
 * fileName : RestDocsConfiguration
 * author : rovert
 * date : 2022/02/13
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/13       rovert         최초 생성
 */

@TestConfiguration
public class RestDocsConfiguration {

    @Bean
    public RestDocsMockMvcConfigurationCustomizer restDocsMockMvcConfigurationCustomizer() {
        return configurer -> configurer.operationPreprocessors()
                .withRequestDefaults(modifyUris().host("sungbin.me").removePort(), prettyPrint())
                .withResponseDefaults(modifyUris().host("sungbin.me").removePort(), prettyPrint());
    }
}
