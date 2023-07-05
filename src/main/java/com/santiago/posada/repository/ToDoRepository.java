package com.santiago.posada.repository;

import com.santiago.posada.repository.model.ToDo;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ToDoRepository extends R2dbcRepository<ToDo, Integer> {
    Flux<ToDo> findByAuthorId(int authorId);
}
