package com.boooking.awayhome.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;
@Data
@Builder
public class ApiError {
    private HttpStatus _status;
    private String message;
    private List<String> _subErrors;
}
