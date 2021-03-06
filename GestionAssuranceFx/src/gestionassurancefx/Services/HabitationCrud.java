/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionassurancefx.Services;


import gestionassurancefx.Entities.Habitation;
import gestionassurancefx.Utils.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Psico
 */
public class HabitationCrud {
    Connection C = Connexion.getInstance().getCon();

    public void ajouterHabitation(Habitation h) {
     
           
      
        try {
            PreparedStatement pstmt=C.prepareStatement("insert into habitation (baieVitre,nbPieces,valmobile,sysAlarm,natureLocal) values (?,?,?,?,?)");
            
            pstmt.setInt(1,h.getBaie_vitre());
            pstmt.setInt(2,h.getNb_pieces());
            pstmt.setFloat(3,h.getValeur_mob());
            pstmt.setInt(4,h.getSys_alarm());
            pstmt.setString(5,h.getNature_local());
            
            pstmt.executeUpdate(); 
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(HabitationCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
                        
                        
			
             
      
            
            
            

    }

   


    
     public ObservableList<Habitation> afficherHabitation() {
      
         try {
             ObservableList<Habitation> habitations= FXCollections.observableArrayList();
             PreparedStatement pstmt=C.prepareStatement("select * from habitation");
             ResultSet rs=pstmt.executeQuery();
             
             while (rs.next()){
                 habitations.add(new Habitation((rs.getInt(1)),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getString(6)));
             }
             pstmt.close();
             return habitations;
         } catch (SQLException ex) {
             return null;
         }

    }
      public void modifierHabitation(Habitation h) {
        
        try {
            PreparedStatement ps = C.prepareStatement("update habitation set baieVitre=?,nbPieces=?,valmobile=?,sysAlarm=?,natureLocal=? where id_habitat=?");
            ps.setInt(1,h.getBaie_vitre());
            ps.setInt(2, h.getNb_pieces());
            ps.setFloat(3, h.getValeur_mob());
            ps.setInt(4, h.getSys_alarm());
            ps.setString(5, h.getNature_local());
            ps.setInt(6, h.getId_habitat());
            
            
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HabitationCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
      

    }
      
    public void SupprimerHabitation(int id) {
       
        try {
            PreparedStatement ps = C.prepareStatement("delete from habitation where id_habitat=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HabitationCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
           
      

}
    
       public int retourneidHabitation() {
      
         try {
             
             PreparedStatement pstmt=C.prepareStatement("select id_habitat from habitation order by id_habitat desc  limit 1");
             ResultSet rs=pstmt.executeQuery();
             int idd=0;
             while (rs.next()){
                 idd=rs.getInt(1);
             }
             pstmt.close();
             return idd;
         } catch (SQLException ex) {
             return 0;
         }

    }
       
            public ObservableList<Habitation> afficherHabitationparId(int id) {
      
         try {
             ObservableList<Habitation> habitations= FXCollections.observableArrayList();
             PreparedStatement pstmt=C.prepareStatement("select * from habitation where id_habitat=?");
             pstmt.setInt(1, id);
             ResultSet rs=pstmt.executeQuery();
             
             while (rs.next()){
                 habitations.add(new Habitation((rs.getInt(1)),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getString(6)));
             }
             pstmt.close();
             return habitations;
         } catch (SQLException ex) {
             return null;
         }

    }
}
