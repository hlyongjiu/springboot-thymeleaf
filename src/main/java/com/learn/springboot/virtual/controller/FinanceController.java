package com.learn.springboot.virtual.controller;

import com.learn.springboot.virtual.pojo.Record;
import com.learn.springboot.virtual.pojo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/virtual/finance")
public class FinanceController {
    @Resource
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/user/index")
    public String userTrade(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String sql = "select * from record where uid = ? ;";
        List<Record> records = jdbcTemplate.query(sql, new Object[]{user.getId()}, new RowMapper<Record>() {

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
        return "user/finance";
    }

    @RequestMapping("/user/rmbPut")
    @ResponseBody
    public int rmbPut(HttpServletRequest request, @RequestBody Record record) {
        int flag = 1;
        try {
            User user = (User) request.getSession().getAttribute("user");
            user.setRmb(user.getRmb() + record.getAmount());
            record.setTypeTwo(1);
            String sql = "insert into record(uid,uname,typeOne,typeTwo,createDate,comment,amount) values(?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql, user.getId(), user.getUserName(), record.getTypeOne(), record.getTypeTwo(), new Date(new java.util.Date().getTime()), record.getComment(),record.getAmount());

            jdbcTemplate.update("update user set rmb=? where id=?", user.getRmb(), user.getId());
        } catch (Exception e) {
            flag = 0;
        }
        return flag;
    }

    @RequestMapping("/user/rmbGet")
    @ResponseBody
    public int rmbGet(HttpServletRequest request, @RequestBody Record record) {
        int flag = 1;
        try {
            User user = (User) request.getSession().getAttribute("user");
            long l = user.getRmb() - record.getAmount();
            if (l < 0) {
                return 2;
            }
            user.setRmb(l);
            record.setTypeTwo(2);
            String sql = "insert into record(uid,uname,typeOne,typeTwo,createDate,comment,amount) values(?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql, user.getId(), user.getUserName(), record.getTypeOne(), record.getTypeTwo(), new Date(new java.util.Date().getTime()), record.getComment(),record.getAmount());

            jdbcTemplate.update("update user set rmb=? where id=?", user.getRmb(), user.getId());
        } catch (Exception e) {
            flag = 0;
        }
        return flag;
    }

    @RequestMapping("/user/coinPut")
    @ResponseBody
    public int coinPut(HttpServletRequest request, @RequestBody Record record) {
        int flag = 1;
        try {
            User user = (User) request.getSession().getAttribute("user");
            user.setCoin(user.getCoin() + record.getAmount());
            user.setCoinResidue(user.getCoinResidue() + record.getAmount());
            record.setTypeTwo(3);
            String sql = "insert into record(uid,uname,typeOne,typeTwo,createDate,comment,amount) values(?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql, user.getId(), user.getUserName(), record.getTypeOne(), record.getTypeTwo(),  new Date(new java.util.Date().getTime()), record.getComment(),record.getAmount());

            jdbcTemplate.update("update user set coin=?,coinResidue=? where id=?", user.getCoin(),user.getCoinResidue(), user.getId());
        } catch (Exception e) {
            flag = 0;
        }
        return flag;
    }

    @RequestMapping("/user/coinGet")
    @ResponseBody
    public int coinGet(HttpServletRequest request, @RequestBody Record record) {
        int flag = 1;
        try {
            User user = (User) request.getSession().getAttribute("user");
            long l = user.getCoinResidue() - record.getAmount();
            if (l < 0) {
                return 2;
            }
            user.setCoin(user.getCoin() - record.getAmount());
            user.setCoinResidue(l);
            record.setTypeTwo(4);
            String sql = "insert into record(uid,uname,typeOne,typeTwo,createDate,comment,amount) values(?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql, user.getId(), user.getUserName(), record.getTypeOne(), record.getTypeTwo(), new Date(new java.util.Date().getTime()), record.getComment(),record.getAmount());

            jdbcTemplate.update("update user set coin=?,coinResidue=? where id=?", user.getCoin(),user.getCoinResidue(), user.getId());
        } catch (Exception e) {
            flag = 0;
        }
        return flag;
    }


}
