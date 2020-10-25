package com.example.libroapp;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConnectionSql implements Runnable{

    private static String bd = "dbdproyectof3";
    private static String login = "user";
    private static String password = "pass";
    private static String url = "jdbc:mysql://189.223.247.24:3306/"+bd;
    private static Connection connection = null;
    private static String query = null;
    private static int i = 0;

    private volatile ResultSet output = null;


    public ConnectionSql(String query,int i) {
        this.query = query;
        this.i = i;
    }

    @Override
    public void run(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,login,password);
            if (connection!=null){
                System.out.println("Conexión a base de datos "+bd+" OK\n");
                //se realiza en el run para no tener que crear nuevos threads
                // 1 para realizar consulta
                // 2 para borrar
                // 3 para insertar/update
                if(i==0){

                }else if(i==1){
                    consultar();

                }else if(i==2){
                    delete();
                }else if(i==3){
                    insertar();
                }


            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            System.out.println("1");
        }catch(Exception ex){
            System.out.println("2");
            System.out.println(ex.getMessage());
        }

    }

    public void desconectar(){
        try{
            System.out.println("Cerrando conexion");
            connection.close();
        }catch(Exception ex){}
    }

    public ResultSet getOutput(){
        return output;
    }

    public void insertar() throws SQLException {
        PreparedStatement prS = connection.prepareStatement(query);
        int i = prS.executeUpdate();
    }

    public void delete() throws SQLException {
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        int i = preparedStmt.executeUpdate();
    }

    public void consultar() throws SQLException {
        Statement mystatement = connection.createStatement();
        output = mystatement.executeQuery(query);
    }

}






