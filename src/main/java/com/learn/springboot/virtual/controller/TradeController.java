package com.learn.springboot.virtual.controller;

import com.learn.springboot.virtual.pojo.Coin;
import com.learn.springboot.virtual.pojo.CoinInfo;
import com.learn.springboot.virtual.pojo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/virtual/trade")
public class TradeController {

    @Resource
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/user/index")
    public String userTrade(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String sql = "select * from coin_info where uid <> ? and status = 0 and type=0;";
        List<CoinInfo> records = jdbcTemplate.query(sql, new Object[]{user.getId()}, new RowMapper<CoinInfo>() {

            @Override
            public CoinInfo mapRow(ResultSet rs, int i) throws SQLException {
                CoinInfo record = new CoinInfo();
                record.setUid(rs.getInt("uid"));
                record.setId(rs.getInt("id"));
                record.setCoin(rs.getLong("coin"));
                record.setName(rs.getString("uname"));
                return record;
            }
        });

        model.addAttribute("recordList", records);
        model.addAttribute("user", user);
        return "user/trade";
    }

    @RequestMapping("/user/sell")
    @ResponseBody
    public int userSell(HttpServletRequest request, @RequestBody Coin coin) {
        int flag = 0;
        User user = (User) request.getSession().getAttribute("user");
        long sell = coin.getSell();
        long coinResidue = user.getCoinResidue();
        try {
            if (coinResidue - sell < 0) {
                flag = 1;//虚拟币不足
            } else {
                coinResidue = coinResidue - sell;
                long coinSell = user.getCoinSell();
                coinSell = coinSell + sell;
                user.setCoinResidue(coinResidue);
                user.setCoinSell(coinSell);

                String sql = "update user set coinResidue=?,coinSell=? where id =?;";
                int update = jdbcTemplate.update(sql, coinResidue, coinSell, user.getId());
                if (update == 1) {
                    flag = 2;
                }
                String upSql = "insert into coin_info(uid,uname,coin,status,type) values(?,?,?,0,0)";
                jdbcTemplate.update(upSql, user.getId(),user.getUserName(), sell);
            }
        } catch (Exception e) {
            flag = 3;
        }
        return flag;
    }

    @RequestMapping("/user/sellInfo")
    public String userSellInfo(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String sql = "select * from coin_info where uid=? and status=0 and type=0";
        List<CoinInfo> query = jdbcTemplate.query(sql, new Object[]{user.getId()}, new RowMapper<CoinInfo>(){
            @Override
            public CoinInfo mapRow(ResultSet rs, int i) throws SQLException {
                CoinInfo record = new CoinInfo();
                record.setUid(rs.getInt("uid"));
                record.setName(rs.getString("uname"));
                record.setId(rs.getInt("id"));
                record.setCoin(rs.getLong("coin"));
                return record;
            }
        });
        model.addAttribute("coinInfoList", query);
        model.addAttribute("user", user);
        return "user/sellInfo";
    }

    @RequestMapping("/user/info")
    public String userInfo(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String sql = "select * from coin_info where uid=? and (type=1 or (type=0 and status=1))";
        List<CoinInfo> query = jdbcTemplate.query(sql, new Object[]{user.getId()}, new RowMapper<CoinInfo>(){
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
        return "user/tradeInfo";
    }

    @RequestMapping("/user/buyCoin/{id}/{uid}/{coin}")
    public String buyCoin(Model model, HttpServletRequest request, @PathVariable(value = "id") int id,@PathVariable(value = "uid") int uid,@PathVariable(value = "coin") long coin) {
        User user = (User) request.getSession().getAttribute("user");

        //更新卖家信息
        long coinResidue = user.getCoinResidue();
        long l = coinResidue - coin;
        if (l < 0) {
            model.addAttribute("flag", "false");
            return "forward:/virtual/trade/user/index";
        }else{
            user.setCoinResidue(l);
            user.setCoin(user.getCoin()-coin);
        }

        //先查询卖家信息
        String query = "select * from user where id=?";
        List<User> users = jdbcTemplate.query(query, new Object[]{uid}, new RowMapper<User>() {

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
        User u = null;
        if (users.size() > 0) {
            u = users.get(0);
            u.setCoinResidue(u.getCoinResidue() + coin);
            u.setCoinSell(u.getCoinSell() - coin);
        }else {
            return "";
        }

        String sql = "update user set coinResidue=?,coinSell=? where id =?;";
        jdbcTemplate.update(sql, u.getCoinResidue(),u.getCoinSell(), uid);
        jdbcTemplate.update("update coin_info set status=1 where id =?",id);

        //买家信息
        String upSql = "insert into coin_info(uid,uname,coin,status,type) values(?,?,?,0,1)";
        jdbcTemplate.update("update user set coinResidue=?,coin=? where id =?",user.getCoinResidue(),user.getCoin(),user.getId());
        jdbcTemplate.update(upSql,user.getId(),user.getUserName(), coin);
        model.addAttribute("user", user);
        return "forward:/virtual/trade/user/index";
    }
}
