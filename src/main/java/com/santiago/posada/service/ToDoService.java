package com.santiago.posada.service;

import com.santiago.posada.repository.AuthorRepository;
import com.santiago.posada.repository.ToDoRepository;
import com.santiago.posada.repository.model.Author;
import com.santiago.posada.repository.model.ToDo;
import com.santiago.posada.service.dtos.AuthorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private AuthorRepository authorRepository;


    public Flux<ToDo> getTasks(){
        return toDoRepository.findAll();
    }



    public Mono<Author> createUser(Author author){
        return authorRepository.save(author).onErrorMap(error -> new InternalError(error.getMessage()))
                .map(author1 -> {
                    System.out.println(author1);
                    return author1;
                });
    }


    public Mono<ToDo> updateTask(int id, String newTask){
        return toDoRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El registro no esta en la base de datos")))
                .flatMap(task -> toDoRepository.save(ToDo.from(newTask, id)));
    }

    public Mono<AuthorDTO> findAuthorWithAllHisTasks(int authorId){
        return toDoRepository.findByAuthorId(authorId)
                .collectList()
                .zipWith(authorRepository.findById(authorId))
                .map(tuple -> new AuthorDTO(tuple.getT2().getId(),
                        tuple.getT2().getName(),
                        tuple.getT2().getLastName(),
                        tuple.getT2().getAge(),
                        tuple.getT2().getEmail(),
                        tuple.getT2().getPhoneNumber(),
                        tuple.getT1()));
    }


}
