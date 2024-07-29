package com.group.libraryapp.domain.user.loanhistory;

import com.group.libraryapp.domain.user.User;
import jakarta.persistence.*;

@Entity
public class UserLoanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    //연관관계의 주인(Table을 보았을 때 누가 관계의 주도권을 가지고 있는지)
    //연관관계의 주도 권이 아닌 user 쪽에 어노테이션 옵션 설정 mappedBy
    //OneToOne - 1:1 설정
    //User 변경(JPA 매핑 ManyToOne N:1 관계 어노테이션 사용)
    @ManyToOne
    private User user;
    //private long userId;

    private String bookName;

    private Boolean isReturn;

    protected UserLoanHistory() {}

    public UserLoanHistory(User user, String bookName) {
        //연관관계 설정 후 변경
        this.user = user;
        //this.userId = userId;
        this.bookName = bookName;
        this.isReturn = false;
    }

    public void doReturn() {
        this.isReturn = true;
    }

    public String getBookName() {
        return this.bookName;
    }
}
