package me.sungbin.demo.accounts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * packageName : me.sungbin.demo.accounts
 * fileName : AccountServiceTest
 * author : rovert
 * date : 2022/02/13
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/13       rovert         최초 생성
 */

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("사용자 아이디를 찾는다")
    void findByUsername() {
        // Given
        String password = "sungbin";
        String username = "sungbin@email.com";
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        this.accountRepository.save(account);

        // When
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Then
        assertEquals(password, userDetails.getPassword());
    }

    @Test
    @DisplayName("사용자 아이디를 찾는것을 실패")
    void findByUsernameFail() {
        String username = "random@email.com";

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            accountService.loadUserByUsername(username);
        });

        assertThat(exception.getMessage()).containsSequence(username);
    }
}