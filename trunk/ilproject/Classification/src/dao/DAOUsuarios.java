/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.sql.*;
import java.sql.Date;

import dominio.*;

/**
 *
 * @author jorge
 */
public class DAOUsuarios extends DAO {

    public String getPasswordUsuario(String login) throws SQLException {
    	System.out.print("Recogiendo paass");
        ResultSet rs = executeQuery("SELECT PASSWORD FROM USER WHERE NAME=?", login);
        if (rs.next()) {
            return rs.getString(1);
        }

        else {
            return null;
        }
    }



    public Usuario setUsuario(String login,String password,String email) throws SQLException {
    	ResultSet rs = executeQuery("SELECT password FROM USER WHERE name=?", login);

        if (rs.next()) {
            return null;
        }
        else {
 		executeUpdate("INSERT INTO USER(name,password,email) VALUES(?,?,?)",login,password,email);
		Usuario user=new Usuario();
		user.setLogin(login);
		user.setTipo(1);
            return user;
        }
     
    }


    public Usuario getUsuarioByLogin(String login) throws SQLException {
        return parseUsuario(executeQuery("SELECT NAME FROM USER WHERE name=?", login));
    }

    private Usuario parseUsuario(ResultSet rs) throws SQLException {

        if (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setLogin(rs.getString("NAME"));
         //   usuario.setTipo(rs.getInt("TIPO"));
            return usuario;
        }
        return null;
    }

    public void updateCita(int id,String alumno)throws SQLException{
        executeUpdate("UPDATE CITAS SET ALUMNO=?,ESTADO=TRUE WHERE ID=?",alumno,id);
    }

    public void deleteCitaProfesor(String login,int id)throws SQLException{
        executeUpdate("DELETE FROM CITAS WHERE profesor = ? AND id=?",login,id);
     }

    public void setCitasProfesor(String login,Date fecha,Time hora)throws SQLException{
        executeUpdate("INSERT INTO CITAS(profesor,date,hora,estado) VALUES(?,?,?,false)",login,fecha,hora);
     }

     public void modifyCita(int id,Date fecha,Time hora)throws SQLException{
       executeUpdate("UPDATE CITAS SET DATE=?,HORA=? WHERE ID=?",fecha,hora,id);
        }

}
