package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Board;
import org.zerock.b01.repository.search.BoardSearch;

/* 인터페이스를 만들고
*  JpaRepository를 상속받는데,
*  JpaRepository<사용 (테이블)클래스명, Pk의 자료형>

* 이렇게만 하면 자동으로 jpa 라이브러리에 의해서
* 자식 객체가 생성되어 Spring Container에 포함된다.
*
* Board에 대한 입출력이 필요하면
* @Autowired
* private BoardRepository boardRepository;
*
* 나 생성자 주입을 통해서 사용하면 된다.
*/

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {

//    1) 쿼리메서드
//    https://spring.io/projects/spring-data-jpa
//
//    2) MariaDB에 적합한 sql - NativeQuery
//    getTime()메서드를 쿼리문과 자동 매칭
    @Query(value="select now()", nativeQuery = true)
    String getTime();
//
//    3) JPQL - JPA에서 정한 표준SQL, JPA에서 사용하는 hibernate에서 NativeQuery로 자동 변환해서 DBMS로 전달
    @Query("select b from Board b where b.title like concat('%', :keyword, '%')")
    Page<Board> findKeyword(String keyword, Pageable pageable);
//
//    4) QueryDsl - 프로그래밍(객체, 메서드 호출) -> JPQL -> NativeQuery -> DBMS로 전달
//
}
