package me.sungbin.demo.config;

import me.sungbin.demo.accounts.Account;
import me.sungbin.demo.accounts.AccountRole;
import me.sungbin.demo.accounts.AccountService;
import me.sungbin.demo.common.BaseControllerTest;
import me.sungbin.demo.common.TestDescription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName : me.sungbin.demo.config
 * fileName : AuthServerConfigTest
 * author : rovert
 * date : 2022/02/13
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/13       rovert         최초 생성
 */

class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    private AccountService accountService;

    @Test
    @DisplayName("인증 토큰을 발급 받는 테스트")
    @TestDescription("인증 토큰을 발급 받는 테스트")
    void getAuthToken() throws Exception {
        // Given
        String username = "sungbin@email.com";
        String password = "sungbin";

        Account sungbin = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        this.accountService.saveAccount(sungbin);

        String clientId = "myApp";
        String clientSecret = "pass";

        this.mockMvc.perform(post("/oauth/token")
                        .with(httpBasic(clientId, clientSecret))
                        .param("username", username)
                        .param("password", password)
                        .param("grant_type", "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
    }
}