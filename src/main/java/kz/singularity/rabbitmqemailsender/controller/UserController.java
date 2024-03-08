package kz.singularity.rabbitmqemailsender.controller;

import kz.singularity.rabbitmqemailsender.model.RequestDto;
import kz.singularity.rabbitmqemailsender.model.ResponseDto;
import kz.singularity.rabbitmqemailsender.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerUser(@RequestBody RequestDto requestDto) {
        usersService.registerUser(requestDto);
        return new ResponseEntity<>(ResponseDto.builder()
                .statusCode(HttpStatus.CREATED.toString())
                .statusMsg("GOOD")
                .build(), HttpStatus.OK);
    }

    // add text
}
