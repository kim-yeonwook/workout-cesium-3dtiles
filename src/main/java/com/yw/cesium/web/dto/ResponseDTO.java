package com.yw.cesium.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class ResponseDTO<T> {

    @JsonIgnore
    public static final String SUCCESS_CODE = "SUCCESS";
    @JsonIgnore
    public static final String ERROR_CODE = "FAIL";

    @JsonIgnore
    public static final String SUCCESS_MESSAGE = "작업 성공";
    @JsonIgnore
    public static final String ERROR_MESSAGE = "작업 실패";

    private String code;

    private String msg;

    private T data;
}
