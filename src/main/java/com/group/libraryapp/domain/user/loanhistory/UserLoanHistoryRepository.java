package com.group.libraryapp.domain.user.loanhistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {
    boolean existsByBookNameAndIsReturn(String name, boolean isReturn);

    //반납 기능 지연로딩 처리 후 주석 처리
    //Optional<UserLoanHistory> findByUserIdAndBookName(Long userid, String bookname);
}
