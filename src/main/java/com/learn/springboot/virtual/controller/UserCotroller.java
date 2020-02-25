package com.learn.springboot.virtual.controller;

import com.learn.springboot.virtual.pojo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/virtual/user")
public class UserCotroller {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/register")
    @ResponseBody
    public Boolean register(@RequestBody User user) {
        boolean flag = false;
        try {
            //执行写入
            int row = jdbcTemplate.update("INSERT INTO user (name,pwd)VALUES (?,?);", user.getUserName(), user.getPwd());
            if (row == 1) {
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    @RequestMapping("/modifyPwd")
    @ResponseBody
    public Boolean modify(HttpServletRequest request,@RequestBody User user) {
        boolean flag = false;
        User u = (User) request.getSession().getAttribute("user");
        user.setId(u.getId());

        String nPwd = request.getParameter("nPwd");

        //SQL
        String sql = "SELECT * FROM user WHERE id=? and pwd=?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{u.getId(), user.getPwd()}, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                user.setUserName(rs.getString("name"));
                user.setCoin(rs.getLong("coin"));
                user.setRmb(rs.getLong("rmb"));
                user.setId(rs.getInt("id"));
                return user;
            }
        });

        if (! (users.size() > 0)) {
            return false;
        }

        try {
            //执行写入
            int row = jdbcTemplate.update("UPDATE user SET pwd=? WHERE id=?;", nPwd,user.getId() );
            if (row == 1) {
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


}
