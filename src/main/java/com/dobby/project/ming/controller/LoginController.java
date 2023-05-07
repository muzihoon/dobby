package com.dobby.project.ming.controller;

import com.dobby.project.ming.dao.UserDao;
import com.dobby.project.ming.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    UserDao userDao;

    @GetMapping("login")
    public String loginForm() {return "ming/loginForm";}


    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("login")
    public String login(String MBR_ID, String PWD, String toURL, boolean saveid,
                        HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //id pwd 확인
        if (!loginCheck(MBR_ID, PWD)) {
            String msg = URLEncoder.encode("⚠️id 또는 pwd가 일치하지 않습니다.", "utf-8");
            return "redirect:/login?msg=" + msg;
        }

        HttpSession session = req.getSession();
        session.setAttribute("MBR_ID", MBR_ID);
            if (saveid) {
                Cookie c = new Cookie("MBR_ID", MBR_ID);
                resp.addCookie(c);
            } else {
                Cookie c = new Cookie("MBR_ID", MBR_ID);
                c.setMaxAge(0);
                resp.addCookie(c);
            }
            toURL = toURL == null || toURL.equals("") ? "/" : toURL;
            return "redirect:" + toURL;
        }
    //ELSE 없앰 RETRUN

    private boolean loginCheck(String MBR_ID, String PWD) throws Exception {
        User user = userDao.selectUser(MBR_ID);
        if (user == null) return false;
        return user.getPWD().equals(PWD);
    }

    @RequestMapping("/findID")
    public String findID() {
        return "ming/findID";
    }

    @RequestMapping("/findPWD")
    public String findPWD() {
        return "ming/findPWD";
    }

    @RequestMapping("/findIDResult")
    public String findIDResult() {
        return "ming/findIDResult";
    }
}