package com.smartcart.ecommerce.dtos.reponse;

import com.smartcart.ecommerce.dtos.error.ErrorResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse<T>{
    private LocalDateTime dateTime;
    private T data;
    private ErrorResponse error;

    public ApiResponse(){
        this.dateTime=LocalDateTime.now();
    }

    public ApiResponse(T data){
        this();
        this.data=data;
    }

    public ApiResponse(ErrorResponse error){
        this();
        this.error=error;
    }
}
