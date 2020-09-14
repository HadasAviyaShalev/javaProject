package com.example.finalproject;
import classes.friends;
import classes.statuses;
import classes.task;
import controllers.controller;
import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@SpringBootApplication
@RestController
public class FinalprojectApplication {
    public static controller controller = new controller();
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
       // SpringApplication.run(FinalprojectApplication.class, args);
       // controller.initial();
        System.out.println(getTeammember());
        System.out.println(getallTaskByMemberCode(1).get(0).getTaskTitle());
        System.out.println(ifExit("hgd","jgjg@mdlsn.com"));
        invokeTheareds(1,statuses.NEW);
    }

    //http://localhost:8080/getallTeamMember
    @GetMapping("/getAllFriends")
    public static List<friends> getTeammember() throws ClassNotFoundException {

        List<friends> friendsList = new ArrayList<friends>();
        friendsList = controller.db.getAllFriends();
        return friendsList;
    }
    @GetMapping(value = "/getTaskFor/{code}")
    public static List<task>  getallTaskByMemberCode(@PathVariable int code) throws ClassNotFoundException {
        return  controller.getTaskFor(code);
    }
    @GetMapping(value = "/ifExit/{name}/{mail}")
    public static String ifExit(@PathVariable String name,@PathVariable String mail) throws ClassNotFoundException {
        return  controller.ifExit(name,mail);
    }
    @GetMapping(value = "/invokeThreads/{taskCode}/{taskStatus}")
    public static void invokeTheareds(@PathVariable int taskCode,@PathVariable statuses taskStatus) throws ClassNotFoundException {
        controller.invokeThreads(taskCode,taskStatus);
    }
}
