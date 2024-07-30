package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import com.group.libraryapp.repository.book.BookMemoryRepository;
import com.group.libraryapp.repository.book.BookMysqlRepositoy;
import com.group.libraryapp.service.user.UserServiceV2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    private BookRepository bookRepository;
    private UserLoanHistoryRepository userLoanHistoryRepository;
    private UserRepository userRepository;

    public BookService(BookRepository bookRepository,
                       UserLoanHistoryRepository userLoanHistoryRepository,
                       UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request) {
        bookRepository.save(new Book(request.getName()));
    }

    @Transactional
    public void loanBook(BookLoanRequest request) {
        // 1. 책 정보를 가져온다.
        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException::new);
        // 2. 대출 기록 정보를 확인 > 대출 중인 책인지 확인한다. ==> 대출중 1, 반납 0
        // 3. 대출중이라면 예외 발생
        if (userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)) {
            throw new IllegalArgumentException("대출중인 책입니다.");
        }
        // 4. 유저 정보를 가져와야함.
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        // 5. 유저 정보와 책 정보를 기반으로 UserLoanHistory를 저장해야함.
//        userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName()));
        user.loanBook(book.getName());
    }

    @Transactional
    public void returnBook(BookReturnRequest request) {
        // 1. 일치하는 유저의 정보를 찾는다.
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        // 2. 유저 id, 책 이름, 반환 상태가 일치하는 객체가 있는지 조회한다.
//        UserLoanHistory userLoanHistory = userLoanHistoryRepository.findByUserIdAndBookNameAndIsReturn(user.getId(), request.getBookName(), false)
//                .orElseThrow(IllegalArgumentException::new);
        user.returnBook(request.getBookName());
        // 3. userLoanHistory의 isReturn을 true로 수정한다.
//        userLoanHistory.doReturn();
    }
}
