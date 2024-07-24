package com.group.libraryapp.domain.user;


import jakarta.persistence.*;

@Entity // 스프링이 User객체와 user 테이블 같은 것으로 본다.(데이터 베이스 저장되고, 관리되어야 하는 데이터)
public class User {

   @Id //import javax.persistence.Id; 사용
   @GeneratedValue(strategy = GenerationType.IDENTITY) //자동증가 설정
   private Long id = null;

    //@Column 의 length 기본값 255
    @Column(nullable = false, length = 20)
    private String name;
    //완전 동일한 경우 @Column 생략 가능
    private Integer age;

    protected User() {}

    public User(String name, Integer age) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다."));

        }
        this.name = name;
        this.age = age;
    }

    public void updateName(String name) {
        this.name= name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }


}
