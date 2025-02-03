package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.dtos.user.LoginDto;
import com.heima.model.common.entity.ApUser;

public interface IApUserService extends IService<ApUser> {

    ResponseResult login(LoginDto loginDto);
}
