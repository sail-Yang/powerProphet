package com.sailyang.powerprophet.controller;

import com.sailyang.powerprophet.pojo.R;
import com.sailyang.powerprophet.pojo.ToEmail;
import com.sailyang.powerprophet.pojo.User;
import com.sailyang.powerprophet.service.UserService;
import com.sailyang.powerprophet.utils.VerCodeGenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/power/email")
public class EmailController {
    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @CrossOrigin
    @PostMapping(value = "/getcode/login")
    @ResponseBody
    public R sendcodeLogin(@RequestParam(value = "email") String toEmail) {
        User user = userService.getByEmail(toEmail);
        String msg;
        if(user == null){
            msg = "该用户不存在,请先注册";
            return new R(-1,msg,null);
        }else{
            SimpleMailMessage message = new SimpleMailMessage();
            ToEmail email = new ToEmail();
            email.setTo(toEmail);
            message.setFrom(from);
            message.setTo(email.getTo());
            message.setSubject("您本次的验证码是");
            String verCode = VerCodeGenerateUtils.generateVerCode();
            redisTemplate.opsForValue().set(toEmail,verCode,60*5, TimeUnit.SECONDS);
            message.setText("尊敬的"+ user.getUsername() +" ,您好:\n"
                    + "\n本次请求的邮件验证码为:" + verCode + ",本验证码 5 分钟内效，请及时输入。（请勿泄露此验证码）\n"
                    + "\n如非本人操作，请忽略该邮件。\n(这是一封通过自动发送的邮件，请不要直接回复）");
            mailSender.send(message);
            return new R(20000,"获取成功",null);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/getcode/signup")
    @ResponseBody
    public R sendcodeSignUp(@RequestParam(value = "email") String toEmail ,@RequestParam(value = "username") String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        ToEmail email = new ToEmail();
        email.setTo(toEmail);
        message.setFrom(from);
        message.setTo(email.getTo());
        message.setSubject("您本次的验证码是");
        String verCode = VerCodeGenerateUtils.generateVerCode();
        redisTemplate.opsForValue().set(toEmail,verCode,60*5, TimeUnit.SECONDS);
        message.setText("尊敬的"+ username +" ,您好:\n"
                + "\n本次请求的邮件验证码为:" + verCode + ",本验证码 5 分钟内效，请及时输入。（请勿泄露此验证码）\n"
                + "\n如非本人操作，请忽略该邮件。\n(这是一封通过自动发送的邮件，请不要直接回复）");
        mailSender.send(message);
        return new R(20000,"获取成功",null);
    }
}
