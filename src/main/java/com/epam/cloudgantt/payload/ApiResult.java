package com.epam.cloudgantt.payload;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<E> {

    private E data;

    private boolean success;

    private String message;

    private List<ErrorData> errors;

    private ApiResult(E data) {
        this.data = data;
        this.success = true;
    }

    private ApiResult(E data, boolean success) {
        this.data = data;
        this.success = success;
    }

    private ApiResult(String errorMsg, Integer code) {
        this.success = false;
        this.errors = List.of(new ErrorData(errorMsg, code));
    }

    private ApiResult(List<ErrorData> errors) {
        this.success = false;
        this.errors = errors;
    }

    public static <T> ApiResult<T> successResponse(T data) {
        return new ApiResult<>(data);
    }

    public static ApiResult<ErrorData> errorResponse(String errorMsg, Integer code) {
        return new ApiResult<>(errorMsg, code);
    }

    public static ApiResult<ErrorData> errorResponse(List<ErrorData> errors) {
        return new ApiResult<>(errors);
    }

    public static <E> ApiResult<E> errorResponseWithData(E e) {
        return new ApiResult<>(e, false);
    }

}
