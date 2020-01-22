package com.techprimers.security.securitydbexample.controller;

import java.util.Set;

import com.techprimers.security.securitydbexample.helper.Response2;
import com.techprimers.security.securitydbexample.model.Role;
import com.techprimers.security.securitydbexample.model.Users;
import com.techprimers.security.securitydbexample.repository.UserRepositoryImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CRUD {

    private UserRepositoryImpl userRepository;

    @Autowired
    public CRUD(UserRepositoryImpl userRepositoryImpl) {
        this.userRepository = userRepositoryImpl;
    }

    @RequestMapping("/user-not-found")
    public String userNotFound() {

        return "Bad Credentials - User Not‌ Found";
    }

    
    // @CrossOrigin(origins = "http://localhost:33475",methods = {RequestMethod.POST,RequestMethod.GET})
    @PostMapping("/sign-up")
    public Response2 addUser(@RequestBody Users user) {

        try {
            userRepository.save(user);
            return new Response2("Saved success");
        } catch (Exception ex) {
            Response2 re = new Response2();
            re.setErrorCode(HttpStatus.BAD_REQUEST);
            re.setErrorMessage("Exception occured !");
            System.out.println(ex.getMessage());
            return re;
        }
    }

    

    @RequestMapping("/admin")
    public String getAdmin() {
        return "Hey Admin here you can do anything";
    }

    @RequestMapping("/user")
    public Object getUser2(String username) {

        return new Response2("Hey User here you can do anything Your name is " + username);
    }

    @RequestMapping("/add")
    public String save() {

        Users user = new Users();
        user.setEmail("myEmail");
        user.setLastName("Kazemi");
        user.setUserName("Saeed");
        user.setPassword(this.BycryptEncode("9845151"));

        Users user2 = new Users();
        user2.setEmail("EEE");
        user2.setLastName("Rezaee");
        user2.setUserName("Qolam");
        user2.setPassword(this.BycryptEncode("5874856216"));

        Users user3 = new Users();
        user3.setEmail("EEE");
        user3.setLastName("Alami");
        user3.setUserName("Behnam");
        user3.setPassword(this.BycryptEncode("8989"));

        Users user4 = new Users();
        user4.setEmail("dfs");
        user4.setLastName("dsfds");
        user4.setUserName("Yaser");
        user4.setPassword(this.BycryptEncode("2525"));

        Role role = new Role();
        role.setRole("ADMIN");

        Role role2 = new Role();
        role2.setRole("USER");

        Role role3 = new Role();
        role3.setRole("VIEWER");

        Role role4 = new Role();
        role4.setRole("SUPERADMIN");

        user.setRoles(Set.of(role));
        user2.setRoles(Set.of(role2));
        user3.setRoles(Set.of(role3));
        user4.setRoles(Set.of(role4));

        try {
            userRepository.save(user);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);

            System.out.println("Saved ..... ");
            return "Saved !!!";
        } catch (Exception ex) {
            System.out.println("Error while saving  ..... " + ex);
            return "Error while saving  ..... " + ex;
        }

    }

    // @RequestMapping("/login")
    // // @ResponseBody
    // public String getLoginPage() {
    // return "login.html";
    // }

    @RequestMapping("/get-user/{id}")
    public Users getUser(@PathVariable("id") int id) {
        return userRepository.getUser(id);
    }

    @RequestMapping("/acces-denied")
    public String getAccessDeniedResponse() {
        return "You dont have permission to see here !";
    }

    @RequestMapping("/log-out-success")
    public String getLogOutSuccess() {
        return "You Logged out successfully !";
    }

    @RequestMapping("/")
    public String getHome() {
        return "HERE‌ IS‌ HOME‌ PAGE";
    }

    public String BycryptEncode(String pass) {
        return new BCryptPasswordEncoder().encode(pass);
    }

    public boolean isMatch(String userInput, String dbPass) {
        return new BCryptPasswordEncoder().matches(userInput, dbPass);
    }

}