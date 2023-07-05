package com.santiago.posada.repository;

import com.santiago.posada.repository.model.Author;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends R2dbcRepository<Author, Integer> {
}
