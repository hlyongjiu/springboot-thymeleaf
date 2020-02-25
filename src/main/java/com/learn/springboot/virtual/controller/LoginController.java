package com.learn.springboot.virtual.controller;

import com.learn.springboot.virtual.pojo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/virtual")
public class LoginController {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/index")
    public String login() {
        return "login";
    }

    @PostMapping(value = "/login")
    public String detail(Model model,
                         HttpServletRequest request) {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        //SQL
        String sql = "SELECT * FROM user WHERE name=? and pwd=?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{userName, password}, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                user.setUserName(rs.getString("name"));
                user.setCoin(rs.getLong("coin"));
                user.setRmb(rs.getLong("rmb"));
                user.setId(rs.getInt("id"));
                user.setCoinSell(rs.getLong("coinSell"));
                user.setCoinResidue(rs.getLong("coinResidue"));
                user.setStatus(rs.getInt("status"));
                return user;
            }
        });

        if (users.size() > 0) {
            User user = users.get(0);
            if (user.getStatus() == 0) {
                model.addAttribute("flag","false");
                return "login";
            }
            model.addAttribute("user",user);
            request.getSession().setAttribute("user", user);

            if (userName.equals("admin")) {
                return "admin/index";
            }else {
                return "user/index";
            }
        }
        model.addAttribute("flag","false");
        return "login";
    }

    @GetMapping(value = "/homepage")
    public String homepage(Model model,
                           HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "user/index";
    }

    @GetMapping(value = "/admin/homepage")
    public String adminhomepage(Model model,
                                HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "admin/index";
    }

}
