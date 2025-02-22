package com.boooking.awayhome.advices;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data

public class ApiResponse<T> {

    private LocalDateTime _timeStamp;
    private  T _data ;
    private ApiError _apiError;


    public ApiResponse(){
        this._timeStamp=LocalDateTime.now();
    }
    public ApiResponse(T data){
        this();
        this._data=data;
    }
    public ApiResponse (ApiError error){
        this();
        this._apiError=error;
    }
}
