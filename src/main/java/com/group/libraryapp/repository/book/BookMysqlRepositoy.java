package com.group.libraryapp.repository.book;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


@Repository
public class BookMysqlRepositoy implements BookRepository {
    @Override
    public void saveBook() {
        System.out.println("BookMysqlRepository");
    }
}