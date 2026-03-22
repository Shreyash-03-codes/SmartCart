package com.smartcart.ecommerce.common.dtos.reponse;

import com.smartcart.ecommerce.common.dtos.error.ErrorResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResult<T>{
    private LocalDateTime dateTime;
    private T data;
    private ErrorResponse error;

    public ApiResult(){
        this.dateTime=LocalDateTime.now();
    }

    public ApiResult(T data){
        this();
        this.data=data;
    }

    public ApiResult(ErrorResponse error){
        this();
        this.error=error;
    }
}
