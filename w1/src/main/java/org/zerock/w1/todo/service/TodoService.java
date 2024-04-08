package org.zerock.w1.todo.service;

import org.zerock.w1.todo.dto.TodoDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/* Singleton Pattern(싱글톤 패턴)
DB 접속처럼 여러개의 객체가 필요하지 않고, 1개의 객체만 요구될 때,
(즉, 일원화가 필요할 때) 객체를 아예 못 만들도록 클래스를 만드는 기법

public class TodoService{
    private static TodoService _instance;

    // 생성자를 외부에서 호출하지 못하도록 private으로 설정
    private TodoService(){}

    // TodoService 객체를 얻을 때는 이 함수로 얻는다.
    public static TodoService getInstance(){
        if(_instance == null)
            _instance = new TodoService();

        return _instance;
    }
}
// 이 코드를 어디서나 호출해도 동일한 객체를 호출하게 된다.
TodoService todoService = TodoService.getInstance();
*/

/* Java의 enum은 class이다.
    그러므로 생성자, 필드, 메서드 모두 보유가능
    그런데 아래처럼 상수를 바로 써주면 정적 객체가 만들어진다.
    싱글톤 패턴을 사용하고 싶을 때 보통 enum을 사용

    INSTANCE;       // TodoService 객체
    SECOND;         // TodoService 객체
    THIRD;          // TodoService 객체
*/
public enum TodoService {
    INSTANCE;       // TodoService의 객체

    public void register(TodoDto todoDto){
        System.out.println("DEBUG......." + todoDto);

        System.out.println("원래 Dao가 있어서 데이터를 Dao에 전달해서 저장해야 한다.");
    }

    // TodoDto 10개를 만들어서, List로 저장 스트림
    // 원래는 Dao에서 값을 꺼내어 jsp에 전달해야하는데 없으니까
    // 그냥 아래처럼 List를 만들어 준 것이다.
    public List<TodoDto> getList(){
        List<TodoDto> todoDTOS = IntStream.range(0, 10).mapToObj(i->{
            TodoDto dto = new TodoDto();
            dto.setTno((long)i);
            dto.setTitle("Todo..." + i);
            dto.setDueDate(LocalDate.now());

            return dto;
        }).collect(Collectors.toList());

        return todoDTOS;
    }
}
