package me.sungbin.demo.accounts;

import me.sungbin.demo.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

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

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    @TestDescription("아이디를 찾는 테스트")
    public void findByUsername() {
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
    @TestDescription("아이디를 불러오다가 실패")
    public void findByUsernameFail() {
        String username = "random@email.com";

        // Expected :: 이 부분을 먼저 예측을 해야한다.
        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expectMessage(Matchers.containsString(username));

        // When
        this.accountService.loadUserByUsername(username);
    }
}