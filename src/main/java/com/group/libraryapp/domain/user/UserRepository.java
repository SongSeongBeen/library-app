package com.group.libraryapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /*
        find : 1건을 가져온다. 반환 타입은 객체가 될 수도 있고, Optional<타입>이 될 수도 있다.
        findAll : 쿼리의 결과물이 N개인 경우 사용. 반환 타입 List<타입>
        exists : 쿼리 결과가 존재하는지 확인. 반환 타입은 boolean
        count : SQL의 결과 개수를 반환 한다 반환 타입 Long
    */

    /*
        By 뒤에 들어갈 수 있는 구절
            GreaterThan : 초과
            GreaterThanequal : 이상
            LessThan : 미만
            LessThanEqual : 이하
            Between : 사이에
            StrartsWith : ~로 시작하는
            EndsWith : ~로 끝나는

        And 나 Or로 조홥
        List<User> findAllByNameAndAge(String name, int age); ex) SELECT * FROM user WHERE name = ? AND age = ?;
        List<User> findAllByAgeBetween(int startAge, int endAge); ex) SELECT * FROM user WHERE age BETWEEN ? AND ?;
     */

    //사용자 name 검증
    Optional<User> findByName(String name);
    //boolean existsByName(String name);





}
