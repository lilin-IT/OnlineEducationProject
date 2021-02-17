package com.lilin.education.service.edu.controller;

import com.lilin.education.common.base.result.R;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class LoginController {
    /**
     * 登录功能
     * @return
     */
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("info")
    public R info(){
        return R.ok().data("name","admin").data("roles","[admin]")
                .data("avatar","https://github.com/lilin-IT");
    }

    /**
     * 退出
     * @return
     */
    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }
}
