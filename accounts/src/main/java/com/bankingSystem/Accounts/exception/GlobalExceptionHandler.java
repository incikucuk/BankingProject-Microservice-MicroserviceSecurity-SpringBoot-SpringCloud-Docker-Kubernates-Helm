package com.bankingSystem.Accounts.exception;

import com.bankingSystem.Accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //Başarısız olan tüm doğrulama excepitonlar burada islenerek kullanıcı hata mesajını verecektir.(contollerda bulunan validasyon icin yapılmış @Valid ve @PAttern gibi degerlerin kontrol edilmesi istenmiştir.)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            String errorMessage = fieldError.getDefaultMessage();
            String fieldName = fieldError.getField();
            errorMessages.add(fieldName + ": " + errorMessage);
        }
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);


//        Map<String, String> validationErrors = new HashMap<>();
//        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
//
//        validationErrors.forEach((error) -> {
//            String fieldName = (String)error.getField();   //gecersiz degerlerden dolayı gecici olarak kaldirildi.
//            String message = error.getDefaultMessage();
//            validationErrors.put(fieldName, message);
//
//        });

        //  return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    //ExcepionHandler sınıfı kullanım amacı kontrol edilmiş edilmemis dahil her türlü exception temsil eden sınıftır.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    //yontemin islemesi gereken istisna adının ne oldugunu belirtir
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException exception,
                                                                                 WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false), //false kullanılmasının nedeni hata mesajını geri dondurmesi,true olursa detaylı hata bilgisini verir
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }
}
