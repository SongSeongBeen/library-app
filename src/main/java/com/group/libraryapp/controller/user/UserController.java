package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.user.UserServiceV2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
    @Component 어노테이션 자동 감지
    컨트롤러, 서비스, 리포지토리가 모두 아니고 개발자가 직접 작성한 클래스를 스프링 빈으로 등록할 때 사용
*/

@RestController
public class UserController {
    /*  1. 스프링 컨테이너(클래스 저장소)
        2. 스프링컨테이너 Bean등록 ex) JdbcTemplate, DataSource, Environment...
        3. 의존성 자동 설정
    */
    private final UserServiceV2 userService;
    //private final UserServiceV1 userServiceV1;
    //private final FruitService fruitService;
    //JPA 사용 Service로 변경
    public UserController(UserServiceV2 userService) {
        this.userService = userService;
    }
    /*
    //@Autowired스프링 버전 업으로 생략 가능
    //@Qualifier 클래스이름 소문자로 바꿔서 가져올 스프링 bean에 지정한다(bean등록 시 동일한 명칭으로 맞춰서 사용가능)
    public UserController(UserServiceV1 userServiceV1, @Qualifier("main") FruitService fruitService) {
        this.fruitService = fruitService;
        this.userServiceV1 = userServiceV1;
    }
    */
    /* Repository에 전달 해야할 jdbcTemplate 정리
            3. Bean을 사용 하여 처리(스프링 빈을 주입 받는 방법)
            private final UserService userService;
                3-1. 생성자 방식(가장 권장 하는 방법)
                @Autowired  //@Autowired스프링 버전 업으로 생략 가능
                public UserController(UserService userService) {
                    this.userService = userService;
                }

                3-2. 셋터 방식
                @Autowired  //@Autowired스프링 버전 업으로 생략 가능
                public void setUserService(UserService userService) {
                    this.userService = userService;
                }

                3-3. 필드주입 직접
                @Autowired //생량 불가능
                private UserService userService;

            2. 의존성을 사용 하여 처리
            //private final JdbcTemplate jdbcTemplate;  //spring-boot autoconfigure 에 의해 dependencies 자동 주입
            private final UserService userService;
            //build.gradle
            //JdbcTemplate : implementation 'org.springframework.boot:spring-boot-starter-data-jpa' 사용
            public UserController(JdbcTemplate jdbcTemplate) {
                //this.jdbcTemplate = jdbcTemplate;
                this.userService = new UserService(jdbcTemplate);
            }

            1. JdbcTemplate을 직접 사용하여 생성자 처리
            private final JdbcTemplate jdbcTemplate;
            public UserController(JdbcTemplate jdbcTemplate) {
                this.jdbcTemplate = jdbcTemplate;
            }
        */

    /*
       사용자 저장
       HTTP Method : POST
       HTTP Path : /user
       HTTP Body (JSON)
       {
           "name": String  사용자 이름
           "age" : Integer 나이
       }
       결과 반환 - 처리 여부 X (HTTP상태 200 OK)
    */
    @PostMapping("/user")
    public void saveUser(@RequestBody UserCreateRequest request) {
        userService.saveUser(request);
    }

    /*
       사용자 조회
       HTTP Method : GET
       HTTP Path : /user
       결과 반환 List<UserResponse> (HTTP상태 200 OK)
       {
           "id"  : long    고유번호
           "name": String  사용자 이름
           "age" : Integer 나이
       }
    */
    @GetMapping("/user")
    public List<UserResponse> getUsers() {
       return userService.getUsers();
    }

    /*
       사용자 이름 수정
       HTTP Method : PUT
       HTTP Path : /user
       HTTP Body (JSON)
       {
           "id": long  고유번호
           "name" : String 사용자 이름
       }
       결과 반환 성공 - 처리 여부 X (HTTP상태 200 OK)
                실패 - id 체크 실패 시 IllegalArgumentException (HTTP 상태 500)

    */
    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        userService.updateUser(request);
    }

    /*
       사용자 삭제
       HTTP Method : DELETE
       HTTP Path : /user
       Params : (String)name : 사용자 이름
       결과 반환  성공 - 처리 여부 X (HTTP상태 200 OK)
                 실패 - name 체크 실패 시 IllegalArgumentException (HTTP 상태 500)
    */
    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        userService.deleteUser(name);
    }

    //test
    @GetMapping("/user/error-test")
    public void errorTest() {
       throw new IllegalArgumentException();
    }

}
