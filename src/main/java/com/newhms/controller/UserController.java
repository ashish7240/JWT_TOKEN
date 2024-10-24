package com.newhms.controller;

import com.newhms.payload.AppUserDto;
import com.newhms.payload.LoginDto;
import com.newhms.payload.TokenDto;
import com.newhms.service.UserService;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private UserService appUserService;

    public UserController(UserService appUserService) {
        this.appUserService = appUserService;
    }
    @PostMapping("/signup")
    public ResponseEntity<?>createUser(
            @RequestBody AppUserDto appUserDto
    ){
        AppUserDto userDetails = appUserService.createUserDetails(appUserDto);
        return new ResponseEntity<>(userDetails,HttpStatus.CREATED);

    }
    @PostMapping("/login")
    public ResponseEntity<?>login(
            @RequestBody LoginDto loginDto
    ){
        String token = appUserService.verifyLogin(loginDto);
        if (token != null){
            TokenDto tokenDto = new TokenDto(token,"JWT");
            return new ResponseEntity<>(tokenDto,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid username/password",HttpStatus.FORBIDDEN);
        }
    }


}


