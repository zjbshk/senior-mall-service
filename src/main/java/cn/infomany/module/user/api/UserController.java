package cn.infomany.module.user.api;

import cn.infomany.common.anno.UserNo;
import cn.infomany.common.domain.Result;
import cn.infomany.module.user.domain.dto.AddUserDTO;
import cn.infomany.module.user.domain.dto.LoginDTO;
import cn.infomany.module.user.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户控制器（用户模块）
 *
 * @author zjb
 * @date 2020/6/2
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/user")
@Validated
public class UserController {

    @Autowired
    private ILoginService loginService;

    @PostMapping("/login")
    @RequiresGuest
    public Result login(@Valid @RequestBody LoginDTO loginDTO) {
        return loginService.login(loginDTO);
    }

    @GetMapping("/logout")
    public Result logout(@UserNo Long no) {
        return loginService.logout(no);
    }

    @PostMapping("")
    public Result addUser(@Valid @RequestBody AddUserDTO addUser) {
        return null;
    }


}
