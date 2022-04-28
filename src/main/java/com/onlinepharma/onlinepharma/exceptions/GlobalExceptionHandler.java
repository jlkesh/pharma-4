//package com.onlinepharma.onlinepharma.exceptions;
//
//import com.onlinepharma.onlinepharma.dto.response.AppErrorDto;
//import com.onlinepharma.onlinepharma.dto.response.DataDto;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@RestController
//@ControllerAdvice("com.onlinepharma")
//public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//
////    @ExceptionHandler(value = {BadCredentialsException.class})
////    public ResponseEntity<DataDto<AppErrorDto>> handle500(BadCredentialsException e, WebRequest webRequest) {
////        return new ResponseEntity<>(new DataDto<>(
////                new AppErrorDto(
////                        HttpStatus.BAD_REQUEST, e.getMessage(), webRequest)),
////                HttpStatus.BAD_REQUEST);
////    }
//
//    @ExceptionHandler(value = {BadCredentialsException.class})
//    public String handle500(BadCredentialsException e, WebRequest webRequest) {
//        return "error";
////        return new ResponseEntity<>(new DataDto<>(
////                new AppErrorDto(
////                        HttpStatus.BAD_REQUEST, e.getMessage(), webRequest)),
////                HttpStatus.BAD_REQUEST);
//    }
//
//}
