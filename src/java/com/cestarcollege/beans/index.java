/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cestarcollege.beans;

import com.cestar.dBUtil.ConnectionManager;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author saira
 */
@ManagedBean
@SessionScoped
public class index implements Serializable{
    String errorMessage;
    int id; //to store pure login ID
    String psw, name;
    Student aStudent;
    Admin aAdmin;
    String[] splitArray;    //Used to split given string into login type and ID
    String idString;    //To store the complete ID string from the user
    ArrayList<Student> listStudents = new ArrayList<>();

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student getaStudent() {
        return aStudent;
    }

    public void setaStudent(Student aStudent) {
        this.aStudent = aStudent;
    }

    public Admin getaAdmin() {
        return aAdmin;
    }

    public void setaAdmin(Admin aAdmin) {
        this.aAdmin = aAdmin;
    }

    public String[] getSplitArray() {
        return splitArray;
    }

    public void setSplitArray(String[] splitArray) {
        this.splitArray = splitArray;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public ArrayList<Student> getListStudents() {
        return listStudents;
    }

    public void setListStudents(ArrayList<Student> listStudents) {
        this.listStudents = listStudents;
    }
           
     
    
    public String login() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;    
        splitArray = idString.split("/");
       
        id = Integer.parseInt(splitArray[1]);

        try {
             connection =ConnectionManager.getConnection();
           
             
            if (splitArray[0].equalsIgnoreCase("student")) {
                System.out.println(id);
                  ps=connection.prepareStatement("select * from CESTAR_STUDENT where id=?");
               ps.setInt(1,id);
             rs=ps.executeQuery();
                if (rs.next()) {
                    if (rs.getString("password").equals(psw)) {
                        aStudent=new Student(id, rs.getString("name"), psw, rs.getString("program"), rs.getString("term"), rs.getString("gender"), rs.getString("address"),rs.getString("email"));
                        return "login_s.xhtml";
                    } else {
                        errorMessage = "Incorrect password!";
                        return "index_error.xhtml";
                    }
                } else {
                    errorMessage = "Incorrect Student ID!";
                    return "index_error.xhtml";
                }
            } else {
                ps=connection.prepareStatement("select * from CESTAR_ADMIN where id=?");
                ps.setInt(1, id);
                rs=ps.executeQuery();
                if (rs.next()) {
                    if (rs.getString(2).equals(psw)) {
                        aAdmin = new Admin(id, psw);
                        return "login.xhtml";
                    } else {
                        errorMessage = "Incorrect password!";
                        return "index_error.xhtml";
                    }
                } else {
                    errorMessage = "Incorrect Faculty ID!";
                    return "index_error.xhtml";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                ps.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        errorMessage = "";
        return "index_error.xhtml";
    }
     public String logout() {
        aStudent = null;
        idString = null;
        psw = null;
        return "index.xhtml";
    }

}
