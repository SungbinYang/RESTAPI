package me.sungbin.demo.accounts;

import me.sungbin.demo.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * packageName : me.sungbin.demo.accounts
 * fileName : AccountServiceTest
 * author : rovert
 * date : 2022/02/14
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/14       rovert         최초 생성
 */

class AccountServiceTest extends BaseControllerTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("아이디를 찾는 테스트")
    void findByUsername() {
        // Given
        String username = "sungbin@email.com";
        String password = "sungbin";

        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        this.accountService.saveAccount(account);

        // When
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Then
        assertTrue(this.passwordEncoder.matches(password, userDetails.getPassword()));
    }

    @Test
    @DisplayName("아이디를 불러오다가 실패")
    void findByUsernameFail() {
        // Expected :: 이 부분을 먼저 예측을 해야한다.
        String username = "random@email.com";

        // When
        assertThrows(UsernameNotFoundException.class, () -> this.accountService.loadUserByUsername(username));
    }
}