package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.dtos.user.LoginDto;
import com.heima.model.common.entity.ApUser;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.IApUserService;
import com.heima.utils.common.AppJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Wrapper;
import java.util.Map;
import java.util.Objects;


@Service
@Transactional
@Slf4j
public class ApUserService extends ServiceImpl<ApUserMapper, ApUser> implements IApUserService {


    @Override
    public ResponseResult login(LoginDto loginDto) {
        //1、判断参数是否合法
        if (StringUtils.isBlank(loginDto.getPhone()) || StringUtils.isBlank(loginDto.getPassword())) {
            log.debug("not valid phone or password");
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2、获取用户
        ApUser dbUser = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, loginDto.getPhone()));
        if (Objects.isNull(dbUser)) {
            log.info("no user in db phone = {}", loginDto.getPhone());
            String token = AppJwtUtil.getToken(0L);
            Map<String,Object> map = Maps.newHashMap();
            map.put("token",token);
            return ResponseResult.okResult(map);
        }
        //3、判断电话和密码是否正确
        String dbUserPassword = dbUser.getPassword();
        String dbUserSalt = dbUser.getSalt();
        String loginDtoPassword = loginDto.getPassword();
        String password = DigestUtils.md5DigestAsHex((loginDtoPassword + dbUserSalt).getBytes());
        if (!StringUtils.equals(dbUserPassword, password)) {
            log.info("password Error");
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        String token = AppJwtUtil.getToken(dbUser.getId().longValue());
        Map<String,Object> map = Maps.newHashMap();
        map.put("token",token);
        dbUser.setSalt("");
        dbUser.setPassword("");
        map.put("user",dbUser);

        return ResponseResult.okResult(map);
    }
}
