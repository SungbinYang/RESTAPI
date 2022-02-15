package me.sungbin.demo.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

/**
 * packageName : me.sungbin.demo.common
 * fileName : AppProperties
 * author : rovert
 * date : 2022/02/15
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/15       rovert         최초 생성
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "my-app")
public class AppProperties {

    @NotEmpty
    private String adminUsername;

    @NotEmpty
    private String adminPassword;

    @NotEmpty
    private String userUsername;

    @NotEmpty
    private String userPassword;

    @NotEmpty
    private String clientId;

    @NotEmpty
    private String clientSecret;
}
