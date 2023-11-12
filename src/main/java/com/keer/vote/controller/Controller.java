package com.keer.vote.controller;

import com.keer.vote.constant.LevelDbConstants;
import com.keer.vote.entity.Result;
import com.keer.vote.entity.User;
import com.keer.vote.entity.VoteInfo;
import com.keer.vote.utils.LevelDbUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {
    /**
     * 登录
     *
     * @param username
     * @param pwd
     * @param response
     * @return
     */
    @GetMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String pwd, HttpServletResponse response) {
        System.out.println(username);
        User user = LevelDbUtils.get(LevelDbConstants.USER_KEY + username, User.class);
        if (user == null) {
            return Result.error("用户不存在");
        }
        if (!user.getPwd().equals(pwd)) {
            return Result.error("密码错误！");
        }
        response.addHeader("user", username);
        String isVote = LevelDbUtils.get("VOTE:" + user.getUserName(), String.class);
        if (isVote != null && isVote.equals("true")) {
            return Result.ok(true);
        } else {
            return Result.ok(false);
        }
    }

    @GetMapping("/isVote")
    public Result isVote(@RequestParam String username) {
        String isVote = LevelDbUtils.get("VOTE:" + username, String.class);
        if (isVote != null && isVote.equals("true")) {
            return Result.ok(true);
        } else {
            return Result.ok(false);
        }
    }

    /**
     * 学生投票
     *
     * @param user
     * @return
     */
    @PostMapping("/vote/stu")
    public Result voteByStu(@RequestBody User user, HttpServletRequest request) {
        String username = request.getHeader("user");
        if (username == null) {
            Result result = new Result();
            result.setCode(400);
            return result;
        }
        String isVote = LevelDbUtils.get("VOTE:" + user.getUserName(), String.class);
        if (isVote != null && isVote.equals("true")) {
            return Result.error("已经提交过");
        }
        User userOld = LevelDbUtils.get(LevelDbConstants.USER_KEY + username, User.class);
        VoteInfo info = LevelDbUtils.get("vote-info", VoteInfo.class);
        if (info == null) {
            info = new VoteInfo();

        }
        user.setPwd(userOld.getPwd());
        info = vote(user, info);
        LevelDbUtils.put(LevelDbConstants.USER_KEY + user.getUserName(), user);
        LevelDbUtils.put("vote-info", info);
        LevelDbUtils.put("VOTE:" + user.getUserName(), "true");
        return Result.ok("投票成功！");
    }

    private VoteInfo vote(User user, VoteInfo info) {
        info.vote("zx", user.getZx());
        info.vote("mg", user.getMg());
        info.vote("cx", user.getCx());
        info.vote("kj", user.getKj());
        info.vote("tj", user.getTj());
        return info;
    }

    /**
     * 老师投票
     *
     * @param username
     * @param award
     * @return
     */
    @GetMapping("/vote/tea")
    public Result voteByTea(@RequestParam String username, @RequestParam String award) {
        VoteInfo info = LevelDbUtils.get("vote-info", VoteInfo.class);
        if (info == null) {
            info = new VoteInfo();
        }
        info.vote(award, username);
        LevelDbUtils.put("vote-info", info);
        return Result.ok("投票成功！");
    }

    /**
     * 是否投票结果
     *
     * @param isShow ture 展示；false 不展示
     * @return
     */
    @GetMapping("/isShow")
    public Result showVoteInfo(String isShow) {
        LevelDbUtils.put("isShow", isShow);
        return Result.ok(isShow);
    }

    /**
     * 获取投票结果
     *
     * @return
     */
    @GetMapping("/vetoResult")
    public Result getVetoResult() {
        String isShow = LevelDbUtils.get("isShow", String.class);
        if (isShow == null || !isShow.equals("true")) {
            return Result.error("投票还未结束，请稍后查看投票结果！");
        }
        VoteInfo info = LevelDbUtils.get("vote-info", VoteInfo.class);
        Map<String, String> map = info.resultInfo();
        return Result.ok(map);
    }

    @GetMapping("/vetoResult/tea")
    public Result getVetoResultTea() {
        VoteInfo info = LevelDbUtils.get("vote-info", VoteInfo.class);
        Map<String, String> map = info.resultInfo();
        return Result.ok(map);
    }

    /**
     * 获取投票信息
     *
     * @return
     */
    @GetMapping("/vetoInfo")
    public Result getVoteInfo() {
        VoteInfo info = LevelDbUtils.get("vote-info", VoteInfo.class);
        return Result.ok(info);
    }

    @GetMapping("/userVetoInfo")
    public Result getUserVoteInfo() {
        Map<String, String> map = new HashMap<>();
        String isVote = LevelDbUtils.get("VOTE:group1", String.class);
        map.put("group1", isVote != null ? isVote : "false");
        isVote = LevelDbUtils.get("VOTE:group2", String.class);
        map.put("group2", isVote != null ? isVote : "false");
        isVote = LevelDbUtils.get("VOTE:group3", String.class);
        map.put("group3", isVote != null ? isVote : "false");
        isVote = LevelDbUtils.get("VOTE:group4", String.class);
        map.put("group4", isVote != null ? isVote : "false");
        isVote = LevelDbUtils.get("VOTE:group5", String.class);
        map.put("group5", isVote != null ? isVote : "false");
        return Result.ok(map);
    }

    @GetMapping("/reVote")
    public Result reVote(@RequestParam String username) {
        User user = LevelDbUtils.get(LevelDbConstants.USER_KEY + username, User.class);
        VoteInfo info = LevelDbUtils.get("vote-info", VoteInfo.class);
        String isvote = LevelDbUtils.get("VOTE:" + username, String.class);
        if (isvote == null || isvote.equals("false")) {
            return Result.ok();
        }
        if (user.getZx() != null) {
            info.vote("zx", user.getZx(), -1);
            user.setZx(null);
        }
        if (user.getMg() != null) {
            info.vote("mg", user.getMg(), -1);
            user.setMg(null);
        }
        if (user.getCx() != null) {
            info.vote("cx", user.getCx(), -1);
            user.setCx(null);
        }
        if (user.getKj() != null) {
            info.vote("kj", user.getKj(), -1);
            user.setKj(null);
        }
        if (user.getTj() != null) {
            info.vote("tj", user.getTj(), -1);
            user.setTj(null);
        }
        LevelDbUtils.put(LevelDbConstants.USER_KEY + username, user);
        LevelDbUtils.put("vote-info", info);
        LevelDbUtils.put("VOTE:" + username, "false");
        return Result.ok();
    }

    @GetMapping("/clearVoteInfo")
    public Result clearVoteInfo() {
        User user1 = new User("group1", "123456");
        User user2 = new User("group2", "123456");
        User user3 = new User("group3", "123456");
        User user4 = new User("group4", "123456");
        User user5 = new User("group5", "123456");
        LevelDbUtils.put(LevelDbConstants.USER_KEY + user1.getUserName(), user1);
        LevelDbUtils.put(LevelDbConstants.USER_KEY + user2.getUserName(), user2);
        LevelDbUtils.put(LevelDbConstants.USER_KEY + user3.getUserName(), user3);
        LevelDbUtils.put(LevelDbConstants.USER_KEY + user4.getUserName(), user4);
        LevelDbUtils.put(LevelDbConstants.USER_KEY + user5.getUserName(), user5);
        LevelDbUtils.put("VOTE:" + user1.getUserName(), "false");
        LevelDbUtils.put("VOTE:" + user2.getUserName(), "false");
        LevelDbUtils.put("VOTE:" + user3.getUserName(), "false");
        LevelDbUtils.put("VOTE:" + user4.getUserName(), "false");
        LevelDbUtils.put("VOTE:" + user5.getUserName(), "false");
        LevelDbUtils.put("vote-info", new VoteInfo());
        return Result.ok();
    }

}
