package org.zerock.b01.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;
import org.zerock.b01.service.ReplyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController                         // Rest방식/json 데이터로 응답
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @ApiOperation(value = "Replies POST", notes = "POST 방식으로 댓글 등록")                // swagger-ui에 보여짐
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)

    /* @RequestBody ReplyDTO replyDTO
        json 문자열로 전송되는 값들을 replyDTO에 저장하겠다.
    * */
    public Map<String, Long> register(@Valid @RequestBody ReplyDTO replyDTO,
                                                      BindingResult bindingResult) throws BindException {
        log.info(replyDTO);

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = new HashMap<>();

        Long rno = replyService.register(replyDTO);

        resultMap.put("rno", rno);

        return resultMap;
        // 브라우저한테는 200 ok라는 헤더정보와 함께 resultMap이 json으로 전달된다.
        // return ResponseEntity.ok(resultMap);

    }

    @ApiOperation(value = "Replies of Board", notes = "GET 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/list/{bno}")      // 패스 상에 있는 bno가져와서 매개변수로 넣음
    public PageResponseDTO<ReplyDTO> getList(@PathVariable("bno") Long bno, PageRequestDTO pageRequestDTO){
        PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfBoard(bno, pageRequestDTO);

        return responseDTO;
    }

    @ApiOperation(value = "Read Reply", notes = "GET 방식으로 특정 댓글 조회")
    @GetMapping("/{rno}")
    public ReplyDTO getReplyDTO(@PathVariable("rno") Long rno){
        ReplyDTO replyDTO = replyService.read(rno);

        return replyDTO;
    }

    @ApiOperation(value = "Delete Reply", notes = "DELETE 방식으로 특정 댓글 삭제")
    @DeleteMapping("/{rno}")
    public Map<String, Long> remove(@PathVariable("rno") Long rno){
        replyService.remove(rno);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;
    }

    // 교재에는 remove로 되어있으나 modify가 맞음
    // remove일때도 실행이 되는 이유는 자바의 다형성(메서드 이름이 동일해도 매개변수가 다르면 다른 메서드로 인식하기 때문)
    // 다른 메서드로 인식되고, 전송방식이 DELETE/PUT의 차이가 있으므로 정상동작이 됨
    @ApiOperation(value="Modify Reply", notes="PUT 방식으로 특정 댓글 수정")
    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> modify(@PathVariable("rno") Long rno, @RequestBody ReplyDTO replyDTO){
        replyDTO.setRno(rno);

        replyService.modify(replyDTO);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;
    }
}
