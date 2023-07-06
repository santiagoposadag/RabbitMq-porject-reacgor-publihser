package com.santiago.posada.routes.exceptionHandler;

import com.santiago.posada.usecases.businessExceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ExceptionHandler {

    public Mono<ServerResponse> handleException(Throwable ex) {

        if(ex instanceof InternalError){
            System.out.println("We accessed the internal error");
            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .bodyValue(new ErrorResponse(ex.getMessage()));
        } else if (ex instanceof BusinessException) {
            System.out.println("We entered to the business exception");
            return ServerResponse.status(HttpStatus.BAD_REQUEST)
                    .bodyValue(new ErrorResponse(ex.getMessage()));
        }
        System.out.println("Printing exception +++++++++");
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .bodyValue(new ErrorResponse(ex.getMessage()));
    }
}
