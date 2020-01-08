package com.baizhi.gjc.controller;

import com.baizhi.gjc.entity.Admin;
import com.baizhi.gjc.service.AdminService;
import com.baizhi.gjc.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @RequestMapping("/login")
    public String login(Admin admin, String code, HttpSession session){
        //获取验证码
        String scode = (String) session.getAttribute("scode");
        if(code.equals(scode)||code==""){
            Admin am = adminService.select(admin);
            System.out.println(am);
            if (am!=null){
                session.setAttribute("admin",am);
                return "ok";
            }
            return "error";
        }
        return "codeerror";
    }
    //验证码
    @RequestMapping("/ma")
    public void ma(HttpSession session, HttpServletResponse response) throws IOException {
        CreateValidateCode cvode = new CreateValidateCode();
        String code = cvode.getCode();//生成随机验证码
        cvode.write(response.getOutputStream()); // 把图片输出client
        //把生成的验证码存入session
        session.setAttribute("scode",code);
    }
    @RequestMapping("out")
    public String out(HttpSession session){
        session.removeAttribute("admin");
        return "redirect:/jsp/login.jsp";
    }
    @RequestMapping("/Useresc")
    public String Useresc(HttpSession session){
        String admin = (String) session.getAttribute("admin");
        session.removeAttribute(admin);
        return "redirect:/jsp/login.jsp";
    }
}
