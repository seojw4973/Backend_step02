package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.QBoard;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl(){
        super(Board.class);     // Board의 클래스를 사용하겠다.
    }

    // 우리는 BoardRepository 내부에서 호출할 메서드를
    // QueryDsl로 구현한다.
    @Override
    public Page<Board> search1(Pageable pageable) {

        // 우리가 작업하는 QueryDsl 프로그램이 -> JPQL로 변환하기 위해 사용
        QBoard board = QBoard.board;    // Q도메인 객체

        /* Querydsl을 통해서 1단계씩 sql문을 작성해나간다.
        * */

        // FROM board
        JPQLQuery<Board> query = from(board);   // select ... from board

        // WHERE title LIKE '%1%'
        query.where(board.title.contains("1"));

        // Paging
        // ORDER BY bno DESC limit 1, 10
        this.getQuerydsl().applyPagination(pageable, query);

        // SELECT * FROM board WHERE title LIKE '%1% ORDER BY bno DESC limit 1, 10'
        List<Board> list = query.fetch();

        // SELECT COUNT(bno) FROM board WHERE title LIKE '%1%'
        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        // Querydsl -> JPQL 변환하기 위한 역할
        QBoard board = QBoard.board;
        
        // FROM board
        JPQLQuery<Board> query = from(board);

        if((types != null && types.length>0) && keyword != null){
            // 괄호(의 역할을 담당
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            /* title Like '%1%'
            *  OR content LIKE '%1%'
            *  OR writer LIKE '%1%'
            * */

            for(String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }

            }
            // )

            /*
            * WHERE(
            *       title LIKE '%1%'
            *       OR content LIKE '%1%'
            *       OR wrtier LIKE '%1%'
            * )
            * */
            query.where(booleanBuilder);


        }
        // AND bno > 0L
        query.where(board.bno.gt(0L));

        // ORDER BT bno DESC limit 1, 10;
        this.getQuerydsl().applyPagination(pageable, query);
        /*
        SELECT
                FROM board
                WHERE (
                        title LIKE '%1%'
                        OR context LIKE '%1%'
                        OR writer LIKE '%1%'
                )
        AND bno > 0L
        */
        List<Board> list = query.fetch();

        long count = query.fetchCount();

        /* list : 실제 row 리스트들
            pageable : 페이지 처리 필요 정보
            count : 전체 row 갯수

            페이징 처리하려면 이것들이 전부 필요하므로
            묶어서 넘기려고 Page<E>를 상속받은 PageImpl<E> 클래스를 만들어놓은것이다.
        * */
        return new PageImpl<>(list, pageable, count);
    }
}
