package com.group.libraryapp.controller.book;

import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import com.group.libraryapp.service.book.BookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    //2. Bean 사용(컨테이너가 자동으로 설정)
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    //1. interface 사용
    //private final BookService bookService = new BookService();

    /*
       책 저장
       HTTP Method :POST
       HTTP Path : /book
       HTTP Body (JSON)
       {
           "name": String 책 이름
       }
       결과 반환 X(HTTP상태 200 OK)
    */
    @PostMapping("/book")
    public void saveBook(@RequestBody BookCreateRequest request) {
        bookService.saveBook(request);
    }

    /*
       책 대출
       HTTP Method :POST
       HTTP Path : /book/loan
       HTTP Body (JSON)
       {
           "userName": String 사용자 이름
           "bookName": String 책 이름
       }
       결과 반환 X(HTTP상태 200 OK)
    */
    @PostMapping("/book/loan")
    public void loanBook(@RequestBody BookLoanRequest request) {
        bookService.loanBook(request);
    }

    /*
       책 대출
       HTTP Method :PUT
       HTTP Path : /book/return
       HTTP Body (JSON)
       {
           "userName": String 사용자 이름
           "bookName": String 책 이름
       }
       결과 반환 X(HTTP상태 200 OK)
    */
    @PutMapping("/book/return")
    public void returnBook(@RequestBody BookReturnRequest request) {
        bookService.returnBook(request);
    }


}
