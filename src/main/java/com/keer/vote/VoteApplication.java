package com.keer.vote;

import com.keer.vote.constant.LevelDbConstants;
import com.keer.vote.entity.User;
import com.keer.vote.utils.LevelDbUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class VoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoteApplication.class, args);
        initUser();
        System.out.println("用户初始化完毕");
    }

    private static void initUser() {
        User user1 = new User("group1", "123456");
        User user2 = new User("group2", "123456");
        User user3 = new User("group3", "123456");
        User user4 = new User("group4", "123456");
        User user5 = new User("group5", "123456");
        User teacher = new User("wcy", "wcy");
        List<String> keys = LevelDbUtils.getKeys();
        if (keys == null || keys.isEmpty()) {
            LevelDbUtils.put(LevelDbConstants.USER_KEY + user1.getUserName(), user1);
            LevelDbUtils.put(LevelDbConstants.USER_KEY + user2.getUserName(), user2);
            LevelDbUtils.put(LevelDbConstants.USER_KEY + user3.getUserName(), user3);
            LevelDbUtils.put(LevelDbConstants.USER_KEY + user4.getUserName(), user4);
            LevelDbUtils.put(LevelDbConstants.USER_KEY + user5.getUserName(), user5);
            LevelDbUtils.put(LevelDbConstants.USER_KEY + teacher.getUserName(), teacher);
        }

    }

}
