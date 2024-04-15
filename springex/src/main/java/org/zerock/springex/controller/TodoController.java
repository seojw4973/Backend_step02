package org.zerock.springex.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zerock.springex.dto.TodoDTO;

@Controller
@RequestMapping("/todo")
@Log4j2
public class TodoController {

    @RequestMapping("/list")
    public void list(){
        log.info("todo list.......");

        // /WEB-INF/views/todo/list.jsp
    }

    // @RequestMapping(value="/register", method= RequestMethod.GET)
    @GetMapping("/register")
    public void registerGet(){
        log.info("todo register.......");

        // /WEB-INF/views/todo/register.jsp
    }

    /* 웹에서 보내는 parameter 들이
        TodoDTO 내부의 필드들의 이름과 매칭되면 todoDTO 객체 내부에 저장된다.
    * */
    @PostMapping("/register")
    public void registerPost(TodoDTO todoDTO){
        log.info("todo register.......");
        log.info(todoDTO);
        // /WEB-INF/views/todo/register.jsp
    }



}
