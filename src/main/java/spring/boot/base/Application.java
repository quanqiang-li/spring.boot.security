package spring.boot.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基本的spring boot和Security集成
 * {@link https://www.cnblogs.com/cjsblog/p/9152455.html}
 * 这里我设计的例子是用户登录成功以后跳到个人中心，然后用户可以可以进入图书列表查看。
 * 用户zhangsan可以查看所有的，而lisi只能查看图书列表，不能添加不能查看详情。密码都是123456
 * 1.访问登录http://localhost:8080/login.html  尝试正确的和错误的
 * 2.再访问个人中心http://localhost:8080/user/index
 * @author carl
 *
 */

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
