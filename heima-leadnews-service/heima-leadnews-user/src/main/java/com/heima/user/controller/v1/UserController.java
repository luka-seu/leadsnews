package com.heima.user.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.dtos.user.LoginDto;
import com.heima.user.service.IApUserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/vi/login")
public class UserController {


    @Autowired
    private IApUserService userService;

    @PostMapping("/loginAuth")
    public ResponseResult login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

}
