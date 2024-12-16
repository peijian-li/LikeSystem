package com.example.likesystem.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Result<T> {

    Boolean success;

    String message;

    T data;


    public static <T> Result<T> success(String message,T data){
        return new Result<>(true,message,data);
    }

    public static <T> Result<T> fail(String message){
        return new Result<>(false,message,null);
    }

}
