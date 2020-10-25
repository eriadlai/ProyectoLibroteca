package com.example.libroapp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConsultaQuery implements Runnable{

    private Connection connection = null;
    private String query = null;
    private ResultSet output = null;


    public ConsultaQuery(String query, Connection c){
        connection = c;
        this.query = query;
    }

    public void run(){
        try {
            Statement mystatement = connection.createStatement();
            output = mystatement.executeQuery(query);
        }catch(Exception e){

        }
    }

    public ResultSet getOutput(){
        return output;
    }
}
