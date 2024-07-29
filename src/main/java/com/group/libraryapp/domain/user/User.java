package com.group.libraryapp.domain.user;


import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import jakarta.persistence.*;

import java.util.List;

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
    /*
       연관관계 설정(연관관계의 주인의 값이 설정되어야만 진정한 데이터가 저장된다.)
       1:1관계
            1. 상대 테이블을 참조하고 있으면 연관관계의 주인
            2. 연관관계의 주인이 아니면 mappedBy를 사용
            3. 연관관계의 주인의 setter가 사용되어야만 테이블 연결
       N:1관계(@ManyToOne 단방향으로만 사용할 수도 있다.)

       @JoinColumn
       연관관계 주인이 활용할 수 있는 어노테이션
       필드의 이름이나 null 여부, 유일성여부, 업데이트 여부등을 지정

       N:M 관계 @ManyToMany(구조가 복잡하고, 테이블이 직관적으로 매핑이 되지 않음 사용 권장 X)

       * casecade 옵션 *
        - 한 객체가 저장되거나 삭제될때, 그 변경이 폭포처럼 흘러 "연결되어 있는 객체도 함께 저장되거나 삭제"
        (유저 삭제시 유저가 빌린 책까지 같이 삭제) 책 데이터 변경 시 적용 X
       * orphanRemoval 옵션 *
        - 유저 책 연결이 끊어 지면 데이터 베이스에서도 삭제 시킴
    */
    //대출기록 1:N 개 어노테이션 설정
    //변경 User를 가져와, 바로 대출처리UserLoanhistory
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLoanHistory> userLoanHistory;

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
    
    /*
        casecade 옵션 적용 후
        대출 지연로딩 처리(domain 계층 비즈니스 로직 추가)
    */
    public void loanBook(String bookName) {
        this.userLoanHistory.add(new UserLoanHistory(this, bookName));
    }

    //반납 지연로딩 처리(domain 계층 비즈니스 로직 추가)
    public void returnBook(String bookName) {
        UserLoanHistory targethistory = this.userLoanHistory.stream()
            .filter(history -> history.getBookName().equals(bookName)) //같은 책이름
            .findFirst()    //첫번째 해당 하는것 처리
            .orElseThrow(IllegalArgumentException::new);
        targethistory.doReturn();
    }
}
