package com.keer.vote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoteApplication.class, args);
    }

    private static void initUser() {
        User user1 = new User("group1", "123456");
        User user2 = new User("group2", "123456");
        User user3 = new User("group3", "123456");
        User user4 = new User("group4", "123456");
        User user5 = new User("group5", "123456");
        LevelDbUtils.put("USER:" + user1.getUserName(), user1);
        LevelDbUtils.put("USER:" + user2.getUserName(), user2);
        LevelDbUtils.put("USER:" + user3.getUserName(), user3);
        LevelDbUtils.put("USER:" + user4.getUserName(), user4);
        LevelDbUtils.put("USER:" + user5.getUserName(), user5);


    }

}
