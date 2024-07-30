package com.group.libraryapp.controller.calculator;

import com.group.libraryapp.dto.calculator.request.CalculatorAddRequest;
import com.group.libraryapp.dto.calculator.request.CalculatorMultiplyRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

    @GetMapping("/add")
    public int addTwoNumbers(CalculatorAddRequest request) {
        /*
            API가 호출되고 파라미터에 값이 들어오게 되면 이 객체에 있는 값에 들어가게 됨.
            즉, 객체로 한번 감싸서 받게 된다.
            ==> @RequestParam을 길게 쓰면 함수가 길어지는데 객체를 바로 받게 되면 깔끔해진다.
                이때, 데이터를 전달해주는 객체인 CalculatorAddRequest를 DTO(Data Transfer Object)라 한다.
         */
        return request.getNumber1() + request.getNumber2();
    }


    @PostMapping("/multiply")
    public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request) {
        return request.getNumber1() * request.getNumber2();
    }


}
