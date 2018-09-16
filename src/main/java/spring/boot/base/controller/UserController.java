package spring.boot.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import spring.boot.base.entity.SysUser;
import spring.boot.base.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 个人中心
     */
    @PreAuthorize("hasAuthority('UserIndex')")
    @GetMapping("/index")
    public String index() {
        return "user/index";
    }

    @RequestMapping("/hi")
    @ResponseBody
    public String hi() {
        SysUser sysUser = userService.getUserByName("zhangsan");
        return sysUser.toString();
    }

}