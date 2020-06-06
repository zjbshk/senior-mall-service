package cn.infomany.module.user.api;

import cn.infomany.common.anno.DistributedLock;
import cn.infomany.common.anno.FrequencyLimit;
import cn.infomany.common.anno.UserNo;
import cn.infomany.common.anno.UserSignature;
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
    @DistributedLock(value = "#methodName", leaseTime = 10)
    public Result login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return loginService.login(loginDTO);
    }

    @GetMapping("/logout")
    public Result logout(@UserNo Long no, @UserSignature String signature) {
        return loginService.logout(no, signature);
    }

    @PostMapping("")
    public Result addUser(@Valid @RequestBody AddUserDTO addUser) {
        return null;
    }


}
