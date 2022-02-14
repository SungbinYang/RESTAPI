package me.sungbin.demo.accounts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * packageName : me.sungbin.demo.accounts
 * fileName : AccountRepository
 * author : rovert
 * date : 2022/02/14
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/14       rovert         최초 생성
 */

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String username);
}
