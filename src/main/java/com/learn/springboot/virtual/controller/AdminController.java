package com.learn.springboot.virtual.controller;

import com.learn.springboot.virtual.pojo.CoinInfo;
import com.learn.springboot.virtual.pojo.Record;
import com.learn.springboot.virtual.pojo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/virtual/admin")
public class AdminController {

    @Resource
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/trade")
    public String trade(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String sql = "select * from coin_info ;";
        List<CoinInfo> query = jdbcTemplate.query(sql, new RowMapper<CoinInfo>(){
            @Override
            public CoinInfo mapRow(ResultSet rs, int i) throws SQLException {
                CoinInfo record = new CoinInfo();
                record.setUid(rs.getInt("uid"));
                record.setId(rs.getInt("id"));
                record.setName(rs.getString("uname"));
                record.setCoin(rs.getLong("coin"));
                record.setStatus(rs.getInt("status"));
                record.setType(rs.getInt("type"));
                return record;
            }
        });
        model.addAttribute("user", user);
        model.addAttribute("infoList", query);
        return "admin/trade";
    }
    @RequestMapping("/finance")
    public String finance(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String sql = "select * from record ;";
        List<Record> records = jdbcTemplate.query(sql, new RowMapper<Record>() {
            @Override
            public Record mapRow(ResultSet rs, int i) throws SQLException {
                Record record = new Record();
                record.setId(rs.getInt("id"));
                record.setUid(rs.getInt("uid"));
                record.setUname(rs.getString("uname"));
                record.setTypeOne(rs.getInt("typeOne"));
                record.setTypeTwo(rs.getInt("typeTwo"));
                record.setAmount(rs.getLong("amount"));
                record.setCreateDate(rs.getDate("createDate"));
                record.setComment(rs.getString("comment"));
                return record;
            }
        });

        model.addAttribute("recordList", records);
        model.addAttribute("user", user);
        return "admin/finance";

    }
    @RequestMapping("/user")
    public String user(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String sql = "select * from user;";
        List<User> userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                user.setUserName(rs.getString("name"));
                user.setPwd(rs.getString("pwd"));
                user.setCoin(rs.getLong("coin"));
                user.setRmb(rs.getLong("rmb"));
                user.setId(rs.getInt("id"));
                user.setCoinSell(rs.getLong("coinSell"));
                user.setCoinResidue(rs.getLong("coinResidue"));
                user.setStatus(rs.getInt("status"));
                return user;
            }
        });

        model.addAttribute("userList", userList);
        model.addAttribute("user", user);
        return "admin/user";
    }



}
