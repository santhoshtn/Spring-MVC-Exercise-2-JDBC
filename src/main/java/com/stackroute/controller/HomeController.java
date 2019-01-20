package com.stackroute.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.*;

@Controller
public class HomeController {
    private Connection con;
    @RequestMapping(value = "/")
    public String greeting(ModelMap map){
        return "index";
    }
    @RequestMapping("populate")
    public ModelAndView populate (@RequestParam("username") String name,@RequestParam("password") String password ){
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/login","santhosh","santhosh");

            String insertTableSQL = "INSERT INTO details"
                    + "( username, password ) VALUES"
                    + "(?,?)";
            PreparedStatement stmt=con.prepareStatement(insertTableSQL);
            stmt.setString(1,name);
            stmt.setString(2,password);
            stmt.executeUpdate();
            PreparedStatement stmt1=con.prepareStatement("select username from details where username=? ");
            stmt1.setString(1,name);
            ResultSet rs =stmt1.executeQuery();
            ModelAndView mv= new ModelAndView();
            mv.setViewName("show");
            rs.next();
            String i=rs.getString(1);
            mv.addObject("obj",i);
            return mv;
        }catch(Exception e){System.out.println(e);
            return new ModelAndView();}


    }
}
