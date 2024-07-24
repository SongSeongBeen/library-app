package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/*
    JDBC 사용 코드
 */
@Service
public class UserServiceV1 {

    private final UserJdbcRepository userJdbcRepository;

    //@Autowired스프링 버전 업으로 생략 가능
    public UserServiceV1(UserJdbcRepository userJdbcRepository) {
        this.userJdbcRepository = userJdbcRepository;
    }
    /*
         3. Bean을 사용 하여 처리(스프링 빈을 주입 받는 방법)
            private final UserService userService;
                3-1. 생성자 방식(가장 권장 하는 방법)
                private final UserRepository userRepository;
                @Autowired  //@Autowired스프링 버전 업으로 생략 가능
                public UserService(UserRepository userRepository) {
                    this.userRepository = userRepository;
                }

                3-2. 셋터 방식
                private final UserRepository userRepository;
                @Autowired  //@Autowired스프링 버전 업으로 생략 가능
                public UserRepository getUserRepository() {
                    return userRepository;
                }

                3-3. 필드주입 직접
                @Autowired //생량 불가능
                private UserRepository userRepository;

        2. 의존성을 사용 하여 처리
        private final UserRepository userRepository;
        public UserService(JdbcTemplate jdbcTemplate) {
            userRepository = new UserRepository(jdbcTemplate);
        }

        1. JdbcTemplate을 직접 사용하여 생성자 처리
        private final JdbcTemplate jdbcTemplate;
        public UserService(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }
    */

    //저장
    public void saveUser(UserCreateRequest request) {
        userJdbcRepository.saveUser(request.getName(), request.getAge());
    }

    //조회
    public List<UserResponse> getUsers() {
        return userJdbcRepository.getUsers();
    }

    //수정
    public void updateUser(UserUpdateRequest request) {

        if (userJdbcRepository.isUserNotExist(request.getId())) {
            throw new IllegalArgumentException();
        }

        userJdbcRepository.updateUserName(request.getName(), request.getId());
    }

    //삭제
    public void deleteUser(String name) {
        if (userJdbcRepository.isUserNotExist(name)) {
            throw new IllegalArgumentException();
        }
    }


}
