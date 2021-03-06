/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionassurancefx.Controllers;

import static gestionassurancefx.Controllers.GestionAssureParticulierController.cinCont;
import static gestionassurancefx.Controllers.GestionAssureParticulierController.nomEntrCont;
import gestionassurancefx.Entities.Contrat;
import gestionassurancefx.Services.ContratCrud;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterAttributes;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;

/**
 * FXML Controller class
 *
 * @author Ahmed Derbel
 */
public class GestionContratController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TextField nomContratField;
    @FXML
    private TextArea descriptionContratField;
    private TextField idClientField;
    @FXML
    private ComboBox<String> typeContratField;
    @FXML
    private DatePicker dateDebutField;
    @FXML
    private DatePicker dateEcheanceField;
    private ContratCrud crud;
    @FXML
    private Button ajouterBtn;
    @FXML
    private TableView<Contrat> contratview;
    @FXML
    private TableColumn<Contrat, Integer> id;
    @FXML
    private TableColumn<Contrat, String> nom;
    @FXML
    private TableColumn<Contrat, String> desc;
    @FXML
    private TableColumn<Contrat, Integer> idassure;
    @FXML
    private TableColumn<Contrat, String> type;
    @FXML
    private TableColumn<Contrat, LocalDate> datedebut;
    @FXML
    private TableColumn<Contrat, LocalDate> dateecheance;
    @FXML
    private TableColumn<Contrat, Integer> etat;
    @FXML
    private TableColumn<Contrat, Float> prime;
    @FXML
    private TextField primeField;
    @FXML
    private ComboBox<String> etatField;
    
    /**
     * Initializes the controller class.
     */
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateBtn;

    Contrat updateContrat;
   
    @FXML
    private Button printBtn;
   
    @FXML
    private TextField cinAssureField;
    private GestionAssureParticulierController ApCont; 
    @FXML
    private Label nomAssLabel;
    private TextField nomAssField;
    @FXML
    private Label cinAssLabel;
    @FXML
    private TableColumn<Contrat,String> nomEntr;
    @FXML
    private TextField nomEntrField;
    @FXML
    private TextField idTypeField;
    @FXML
    private TableColumn<Contrat,Integer> id_type;
    @FXML
    private AnchorPane tableviewPane;
    @FXML
    private Button UpdateTypeContrat;
    
       
    
    public void initialize(URL url, ResourceBundle rb) {
       
        if(nomEntrCont != null ){
            nomAssLabel.setVisible(true);//label du nom d entreprise
            nomEntrField.setVisible(true);
            cinAssLabel.setVisible(false);
            cinAssureField.setVisible(false);
        }
        
        initColumns();
        crud = new ContratCrud();
        typeContratField.getItems().addAll("Habitation", "Voyage", "Voiture");
        etatField.getItems().addAll("Paye", "Non paye");
        cinAssureField.setText(cinCont);
        nomEntrField.setText(nomEntrCont);
        //cinAssureField.setText(nomEntrCont);
        contratview.setItems(crud.getAllContrat());
     
        try {
            crud.getExpiredContrat();
        } catch (SQLException ex) {
            Logger.getLogger(GestionContratController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
   
    public void initColumns() {
        id.setCellValueFactory(new PropertyValueFactory<Contrat, Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Contrat, String>("nom"));
        desc.setCellValueFactory(new PropertyValueFactory<Contrat, String>("Description"));
        idassure.setCellValueFactory(new PropertyValueFactory<Contrat, Integer>("cin_assure"));
        nomEntr.setCellValueFactory(new PropertyValueFactory<Contrat, String>("nomEntr"));
        type.setCellValueFactory(new PropertyValueFactory<Contrat, String>("type"));
        datedebut.setCellValueFactory(new PropertyValueFactory<Contrat, LocalDate>("date_debut"));
        dateecheance.setCellValueFactory(new PropertyValueFactory<Contrat, LocalDate>("date_Echeance"));
        etat.setCellValueFactory(new PropertyValueFactory<Contrat, Integer>("Etat"));
        prime.setCellValueFactory(new PropertyValueFactory<Contrat, Float>("prime"));
        id_type.setCellValueFactory(new PropertyValueFactory<Contrat,Integer>("id_type"));

    }

    
    
    
    @FXML
    private void ajouterClicked(ActionEvent event) {
        //contratview.getItems().clear();
        
        Date date_debut = java.sql.Date.valueOf(dateDebutField.getValue());
        Date date_echeance = java.sql.Date.valueOf(dateEcheanceField.getValue());
        int prime = Integer.parseInt(primeField.getText());
        int etat = 0;
      //  int idass = Integer.parseInt(etatField.getSelectionModel().getSelectedItem());
        if (etatField.getSelectionModel().getSelectedItem().equalsIgnoreCase("Paye")) {
            etat = 1;
        } else {
            etat = 0;
        }
        Contrat cr = new Contrat();
        cr.setDate_Echeance(date_echeance);
        cr.setDate_debut(date_debut);
        cr.setNom(nomContratField.getText());
        cr.setDescription(descriptionContratField.getText());
        
       //String[ ] arr=idAssureField.getSelectionModel().getSelectedItem().split(" ");
        
      if(cinCont != null ){
           cr.setCin_assure(Integer.parseInt(cinAssureField.getText()));
      }
        
       if(nomEntrCont != null ){
           cr.setNomEntr(nomEntrField.getText());
        }
        
       
        cr.setType(typeContratField.getSelectionModel().getSelectedItem());
        cr.setId_type(Integer.parseInt(idTypeField.getText()));
        cr.setEtat(etat);
        cr.setPrime(prime);
        crud.ajouterContrat(cr);
        contratview.getItems().clear();
        contratview.setItems(crud.getAllContrat());
        System.out.println("Contrat Ajouter");

    }

    private void AfficherClicked(Event event) {
        System.out.println(crud.getAllContrat());
    }

    @FXML
    private void deleteContrat(ActionEvent event) {
        crud.SupprimerContrat(contratview.getSelectionModel().getSelectedItem().getId());
        nomContratField.setText("");
        descriptionContratField.setText("");
        cinAssureField.setText("");
        nomEntrField.setText("");
        //typeContratField.setItems(typeliste);
        typeContratField.getSelectionModel().select("");
        idTypeField.setText("");
        dateDebutField.setValue(null);
        dateEcheanceField.setValue(null);
        etatField.getSelectionModel().select("");
        primeField.setText("");
        contratview.getItems().clear();
        contratview.setItems(crud.getAllContrat());
        System.out.println("deleted");
    }

    private Contrat cr = new Contrat();

    @FXML
    private void ItemSelected(MouseEvent event) {
        cr = contratview.getSelectionModel().getSelectedItem();
        if (cr != null) {
            String etat = "";
            String prime = "";
            prime += cr.getPrime();
           
            if (cr.getEtat() == 0) {
                etat = "non paye";
            } else {
                etat = "Paye";
            }

          if(cr.getNomEntr()!= null){
            nomAssLabel.setVisible(true);//label du nom d entreprise
            nomEntrField.setVisible(true);
            cinAssLabel.setVisible(false);
            cinAssureField.setVisible(false);
               }
          if (cr.getCin_assure() != 0 ){
              nomAssLabel.setVisible(false);//label du nom d entreprise
            nomEntrField.setVisible(false);
            cinAssLabel.setVisible(true);
            cinAssureField.setVisible(true);
          }
          
            nomContratField.setText(cr.getNom());
            descriptionContratField.setText(cr.getDescription());
            cinAssureField.setText(Integer.toString(cr.getCin_assure()));
            nomEntrField.setText(cr.getNomEntr());
            typeContratField.getSelectionModel().select(cr.getType());
            idTypeField.setText(Integer.toString(cr.getId_type()));
            dateDebutField.setValue(cr.getDate_debut().toLocalDate());
            dateEcheanceField.setValue(cr.getDate_Echeance().toLocalDate());
            etatField.getSelectionModel().select(etat);
            primeField.setText(prime);

        }
    }

    @FXML
    private void updateClicked(ActionEvent event) {
       
        if (cr != null) {
            Date date_debut = java.sql.Date.valueOf(dateDebutField.getValue());
            Date date_echeance = java.sql.Date.valueOf(dateEcheanceField.getValue());
            cr.setDate_Echeance(date_echeance);
            cr.setDate_debut(date_debut);
            cr.setNom(nomContratField.getText());
            cr.setDescription(descriptionContratField.getText());
          
             if(cinCont != null ){
            cr.setCin_assure(Integer.parseInt(cinAssureField.getText()));
           
            
      }
        
       if(nomEntrCont != null ){
          cr.setNomEntr(nomEntrField.getText());
         
        }
        
              cr.setType(typeContratField.getSelectionModel().getSelectedItem());
              cr.setId_type(Integer.parseInt(idTypeField.getText()));
            cr.setPrime(Integer.parseInt(primeField.getText()));
            if (etatField.getSelectionModel().getSelectedItem().equals("Non paye")) {
                cr.setEtat(0);
            } else {
                cr.setEtat(1);
            }
            System.out.println(cr);
            crud.modifierContrat(cr);
            contratview.getItems().clear();
            contratview.setItems(crud.getAllContrat());
        }else {
            System.out.println("clicker sur un object");
        }
    }
    
    
    public static void printNode(final Node node) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    Printer printer = Printer.getDefaultPrinter();
    PageLayout pageLayout
        = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
    PrinterAttributes attr = printer.getPrinterAttributes();
    PrinterJob job = PrinterJob.createPrinterJob();
    double scaleX
        = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
    double scaleY
        = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
    Scale scale = new Scale(scaleX, scaleY);
    node.getTransforms().add(scale);

    if (job != null && job.showPrintDialog(node.getScene().getWindow())) {
      boolean success = job.printPage(pageLayout, node);
      if (success) {
        job.endJob();

      }
    }
    node.getTransforms().remove(scale);
  }

    @FXML
    private void printClicked(ActionEvent event) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        printNode(tableviewPane);
    }

   

}
