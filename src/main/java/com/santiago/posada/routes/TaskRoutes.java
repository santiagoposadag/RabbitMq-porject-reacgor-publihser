package com.santiago.posada.routes;

import com.santiago.posada.repository.ToDoRepository;
import com.santiago.posada.repository.model.Author;
import com.santiago.posada.repository.model.ToDo;
import com.santiago.posada.service.ToDoService;
import com.santiago.posada.usecases.CreateTaskUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TaskRoutes {

    @Autowired
    private ToDoService service;

    @Bean
    public RouterFunction<ServerResponse> getTasks(){
        return route(GET("route/get/all"),
                request -> ServerResponse
                        .ok()
                        .body(BodyInserters.fromPublisher(service.getTasks(), ToDo.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> addTask(CreateTaskUseCase useCase){
        return route(POST("route/save/task"),
                request -> request.bodyToMono(ToDo.class)
                        .flatMap(todo -> useCase.apply(todo))
                        .flatMap(result -> ServerResponse.ok().bodyValue(result)));
    }

    @Bean
    public RouterFunction<ServerResponse> createUser(){
        return route(POST("route/create/user"),
                request -> request.bodyToMono(Author.class)
                        .flatMap(author -> service.createUser(author))
                        .flatMap(result -> ServerResponse.ok().bodyValue(result)));
    }

    @Bean
    public RouterFunction<ServerResponse> getUserWithTasks(){
        return route(GET("route/get/user/{userId}"),
                request -> service.findAuthorWithAllHisTasks(Integer
                        .parseInt(request.pathVariable("userId")))
                        .flatMap(author -> ServerResponse.ok().bodyValue(author)));
    }

    //Generar métodos repository, el servicio y la ruta para poder solicitar todos
    //los autores con todas sus tareas


}
