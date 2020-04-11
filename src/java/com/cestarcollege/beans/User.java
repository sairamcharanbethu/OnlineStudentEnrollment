/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cestarcollege.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author sheik.mamun
 */
@ManagedBean
@RequestScoped
@SessionScoped
public class User implements Serializable{
    private int id;
    private String name;
    private String password;
    private String email;
    private String address;
    private String gender;
    private String program;
    private String term;

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
    
    List<User> userList;
    //Creating sessionMap to hold the session objects 
    private final Map<String, Object> sessionMap =FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
  
    public String getGenderName(char gender){
        if('M' == gender)
            return "Male";
        return "Female";
    }
    
    public List<User> usersList(){
        userList = new ArrayList<User>();
        Connection conn = getConnection();
        try{
               PreparedStatement ps = conn.prepareStatement("select * from CESTAR_STUDENT");
             ResultSet rs= ps.executeQuery();
        while(rs.next()){
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            //user.setPassword(rs.getString("password"));
            user.setProgram(rs.getString("program"));
            user.setTerm(rs.getString("term"));
            user.setGender(rs.getString("gender"));
            user.setEmail(rs.getString("email"));
            user.setAddress(rs.getString("address"));
            userList.add(user);
        }
            }catch(Exception e){
        }
       return userList;
    }
    /**It will contain the corresponding user information to edit.
     * 
     * @param id
     * @return 
     */
    public String edit(int id)
    {
        User editUser=null;
        boolean result=false;
        try {
            String sql="SELECT * FROM CESTAR_STUDENT WHERE id=?";
            Connection con=getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                editUser = new User();
                editUser.setId(id);
                editUser.setName(rs.getString("name"));
                editUser.setPassword(rs.getString("password"));
                editUser.setAddress(rs.getString("address"));
                editUser.setEmail(rs.getString("email"));
                editUser.setGender(rs.getString("gender"));
                editUser.setProgram(rs.getString("program"));
                editUser.setTerm(rs.getString("term"));
                sessionMap.put("editUser", editUser);
                result=true;
                
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(result)
            return "edit.xhtml?faces-redirect=true";
        else
            return "login.xhtml?faces-redirect=true";
    }
     public String edit_s(int id)
    {
        User seditUser=null;
        boolean result=false;
        try {
            String sql="SELECT * FROM CESTAR_STUDENT WHERE id=?";
            Connection con=getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                seditUser = new User();
                seditUser.setId(id);
                seditUser.setName(rs.getString("name"));
                seditUser.setPassword(rs.getString("password"));
                seditUser.setAddress(rs.getString("address"));
                seditUser.setEmail(rs.getString("email"));
                seditUser.setGender(rs.getString("gender"));
                seditUser.setProgram(rs.getString("program"));
                seditUser.setTerm(rs.getString("term"));
                sessionMap.put("seditUser", seditUser);
                result=true;
                
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(result)
            return "edit_s.xhtml?faces-redirect=true";
        else
            return "login_s.xhtml?faces-redirect=true";
    }
   
    
    public String update(User u){
        int result=0;
        try {
            String sql="UPDATE CESTAR_STUDENT SET name=?,password=?,"
                    + "program=?,term=?,gender=?,"
                    + "email=?,address=? WHERE id=?";
            Connection con=getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,u.getName());
            ps.setString(2,u.getPassword());
            ps.setString(3,u.getProgram());
            ps.setString(4, u.getTerm());
            ps.setString(5, u.getGender());
            ps.setString(6, u.getEmail());
            ps.setString(7, u.getAddress());
            ps.setInt(8, u.getId());
            result=ps.executeUpdate();
         
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }if(result>0)
            {
                
                return"login.xhtml?faces-redirect=false";
            }
            else
                 return"edit.xhtml?faces-redirect=true";
        
    }
    public String update_s(User u)
    {
        int result=0;
        try {
            String sql="UPDATE CESTAR_STUDENT SET name=?,password=?,"
                    + "program=?,term=?,gender=?,"
                    + "email=?,address=? WHERE id=?";
            Connection con=getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,u.getName());
            ps.setString(2,u.getPassword());
            ps.setString(3,u.getProgram());
            ps.setString(4, u.getTerm());
            ps.setString(5, u.getGender());
            ps.setString(6, u.getEmail());
            ps.setString(7, u.getAddress());
            ps.setInt(8, u.getId());
            result=ps.executeUpdate();
         
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }if(result>0)
            {
                return"login_s.xhtml?faces-redirect=true";
            }
            else
                 return"edit_s.xhtml?faces-redirect=true";
    }
    public String save(){
        int result =0;
     Connection conn =getConnection();
    String insertQuery = new StringBuilder()
                    .append("INSERT INTO CESTAR_STUDENT(")
                    .append(" name,password,program,term")
                    .append(",gender, email,address)")
                    .append("VALUES(?,?,?,?,?,?,?)")
                    .toString();
    try {
            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setString(3, program);
            ps.setString(4, term);
            ps.setString(5, gender);
            ps.setString(6, email);
            ps.setString(7, address);
            result =ps.executeUpdate();
    } catch (Exception e) {
            e.printStackTrace();
    }
    if(result > 0)
        return "login.xhtml?faces-redirect=true";
    else
        return "create.xhtml?faces-redirect=true";
    }
    public void delete(int id)
    {
        try {
            String sql="DELETE FROM CESTAR_STUDENT WHERE id="+id;
            Connection con=getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public Connection getConnection() {
        Connection con=null;
        try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                 con=DriverManager.getConnection(  
                                "jdbc:oracle:thin:@localhost:1521:xe","system","1234");  
        } catch (Exception e) {
                e.printStackTrace();
        }
	  return con;
    }
}
