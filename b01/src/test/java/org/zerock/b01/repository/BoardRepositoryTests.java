package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Board;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {
    
    @Autowired
    private BoardRepository boardRepository;
    
    @Test
    public void testInsert(){
        // 1이상 ~ 100 이하까지
        IntStream.rangeClosed(1, 100).forEach(i ->{
            Board board = Board.builder()
                    .title("title..." + i)
                    .content("content..." + i)
                    .writer("user" + (i % 10))
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO : " + result.getBno());
        });
    }

    @Test
    public void testSelect(){
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        log.info(board);
    }

    @Test
    public void testUpdate(){
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();     // 정상인 경우 board 객체 리턴

        board.change("update..title 100---", "update content 100");
    
        /* save : pk가 테이블에 존재하지 않으면 -> 새로운 데이터 삽입을 위해 insert문 실행
                  pk가 기존 데이터에 존재하면 -> 수정하기 위해 update문 실행
        * */
        boardRepository.save(board);

        log.info(board);
    }

    @Test
    public void testDelete(){
        Long bno = 1L;

        boardRepository.deleteById(bno);
    }
}
