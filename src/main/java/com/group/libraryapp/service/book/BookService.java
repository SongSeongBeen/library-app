package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    /*
        2. Bean 사용(컨테이너가 자동으로 설정)
            > 제어의 역전(Ioc, Inversion of Control)
            > 컨테이너가 선택해 넣어주는 과정을 의존성 주입(DI, Dependency Injection
    */
    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;

    //사용하는 Repository에 우선권 @Primary 설정
    public BookService(
            BookRepository bookRepository
            , UserLoanHistoryRepository userLoanHistoryRepository
            , UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    //1. interface 사용
    //private final BookRepository bookRepository = new BookmySqlRepository();

    //책 저장
    @Transactional
    public void saveBook(BookCreateRequest request) {
        bookRepository.save(new Book(request.getName()));
    }

    //책 대출
    @Transactional
    public void loanBook(BookLoanRequest request) {

        // 1. 책 정보 확인
        Book book = bookRepository.findByName(request.getBookName()).orElseThrow(IllegalAccessError::new);
        // 2. 대출 내역 확인 후 예외 처리
        if (userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)) {
            throw new IllegalArgumentException("대출 중인 책 입니다.");
        }
        System.out.println("book.getName()===" + book.getName());
        // 3. 사용자 정보 확인
        User user = userRepository.findByName(request.getUserName()).orElseThrow(IllegalArgumentException::new);

        //지연로딩 수정
        //casecade 옵션(새로운 연결관계 적용)
        user.loanBook(book.getName());
        // 4. 대출 했으면 저장
        //userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName()));

    }

    //책 반납
    @Transactional
    public void returnBook(BookReturnRequest request) {
        //1. 사용자 확인
        User user = userRepository.findByName(request.getUserName()).orElseThrow(IllegalArgumentException::new);
        /* 지연로딩 처리
        //2. 책 정보 확인
        UserLoanHistory userBookHistory = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(), request.getBookName())
            .orElseThrow(IllegalArgumentException::new);
        //3. 반납 플래그 변경
        userBookHistory.doReturn();
        */
        /*
            지연로딩(Lazy Loading) default:Lazy (EAGER 적용시 데이터 한번에 가져온다.)
            user 정보 가져온 후
            returnBook 메서드 처리 시(필요한 순가에 데이터를 가져온다.)
            UserLoanHistory 가져온다
        */
        user.returnBook(request.getBookName());

        //4. 변경 감지 후 영속성 적용 하여 save
    }
}
