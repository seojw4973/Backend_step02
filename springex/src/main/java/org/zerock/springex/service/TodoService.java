package org.zerock.springex.service;

import org.zerock.springex.domain.TodoVO;
import org.zerock.springex.dto.PageRequestDTO;
import org.zerock.springex.dto.PageResponseDTO;
import org.zerock.springex.dto.TodoDTO;
import sun.jvm.hotspot.debugger.Page;

import java.util.List;

public interface TodoService {

    void register(TodoDTO todoDTO);

    // 테이블의 데이터를 모두 가져와라
    // List<TodoDTO> getAll();

    PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO);

    TodoDTO getOne(Long tno);

    void remove(Long tno);

    void modify(TodoDTO todoDTO);


}
