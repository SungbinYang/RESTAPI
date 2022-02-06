package me.sungbin.demo.events;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName : me.sungbin.demo.events
 * fileName : EventRepository
 * author : rovert
 * date : 2022/02/06
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/06       rovert         최초 생성
 */

public interface EventRepository extends JpaRepository<Event, Integer> {
}
