package com.devStack.utill;

import java.sql.*;

public class IdGenarator {
    public int genId(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/medex",
                    "root",
                    "Dinuj@5615011"
            );
            String sql = "SELECT id FROM user ORDER BY id DESC LIMIT 1";
            PreparedStatement psd = connection.prepareStatement(sql);
            ResultSet rst = psd.executeQuery();
            if(rst.next()){
                return rst.getInt(1)+1;
            }

        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return 1;
    }
}
