package com.devStack.utill;

import com.devStack.db.DBConnection;

import java.sql.*;

public class IdGenarator {
    public int genId(){
        try{
            ResultSet rst = CrudUtil.execute("SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1");
            if(rst.next()){
                return rst.getInt(1)+1;
            }

        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return 1;
    }

    public String generateId(String sql, String prefix){
        try{
            ResultSet rst = CrudUtil.execute(sql);
            if (rst.next()){
                String tempId =  rst.getString(1);
                int id= Integer.parseInt(tempId.split("-")[1]); // -->
                id++;
                return prefix+"-"+id;
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return prefix+"-1";
    }
}
