package org.zerock.b01.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// RestController : json형태로 객체 데이터 반환 페이지를 보내는 것이 아니라 데이터만 보냄
@RestController
@Log4j2
public class SampleJSONController {

    @GetMapping("/helloArr")
    public String[] helloArr(){
        log.info("helloArr...............");

        return new String[]{"AAA", "BBB", "CCC"};


    }
}
