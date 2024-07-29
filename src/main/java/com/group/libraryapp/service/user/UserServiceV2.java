package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {

    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
        영속성 컨텍스트란.?
            테이블과 매핑된 Entity 객체를 관리/보관하는 역할
            스프링에서 트랜잭션을 사용하면 영속성 컨텍스트가 생겨나고, 트랜잭션이 종료되면 영속성 컨텍스트가 종료 된다.
        1. 변경감지(Dirty Check) - 영속성 컨텍스트 안에서 불러와진 Entity는 명시적으로 save 하지 않더라도, 변경을 감지해 자동 저장
        2. 쓰기지연 DB의 INSERT/UPDATE/DELETE SQL을 트랜잭션이 commit 될때 한번에 실행
        3. 1차 캐싱 ID를 기준으로 Entity 기억 필요한 동일 데이터를 사용시 db 통신을 계속 할 필요가 없다(데이터 재사용).
            - 캐싱된 객체는 완전이 동일하다. 인스턴스마다 고유 주소까지 동일
        4. 지연로딩(Lazy Loading) @MoTomany의 fetch 옵션 default - Lazy 적용 (EAGER 적용시 데이터 한번에 가져온다.)
        * 도메인 설계 시 주의
        지나치게 사용하면, 성능상 문제가 생길 수도 있고 도메인 간의 복잡한 연결로 인해 시스템을 파악하기 어려워짐
    */
    @Transactional
    public void saveUser(UserCreateRequest request) {
        /*
            1:1관계
            연관과계 연결 시, 트랜잭션이 끝나지 않았을 경우 한쪽만 연결해 두면 반대 쪽은 알수 없다.
            ex)
            set(id)
            get(id); null
            setter 연결 시, 객체 끼리도 같이 연결해주면 해결
            this.user = user;
            this.user = setUserLoanHistroty
        */
        //영속성 컨텍스트 시작
        userRepository.save(new User(request.getName(), request.getAge()));

        //Transactional 확인용
        //throw new IllegalArgumentException(); //IOExeption 과 같은 Checked Exception 은 롤백이 일어나지 않는다.
        //영속성 컨텍스트 종료
    }
    /*
        스프링 프레임웍 트랜젝션 사용
        SELECT 쿼리 readOnly 옵션 사용
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()//findAll select * from 처리
            .map(UserResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(UserUpdateRequest request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new); //Java 8 부터 optional 사용(User가 없으면 예외 처리)

        user.updateName(request.getName()); //유저바뀜 감지 후 자동 업데이트 함
        //userRepository.save(user);  //수정
    }

    @Transactional
    public void deleteUser(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(IllegalArgumentException::new);

        userRepository.delete(user); //삭제


    }
}
