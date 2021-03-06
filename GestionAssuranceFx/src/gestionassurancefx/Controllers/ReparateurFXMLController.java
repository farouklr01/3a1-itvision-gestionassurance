/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionassurancefx.Controllers;

import gestionassurancefx.Entities.Reparateur;
import gestionassurancefx.Services.ReparateurService;
import gestionassurancefx.Utils.Connexion;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterAttributes;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ReparateurFXMLController implements Initializable {
    @FXML
    private AnchorPane mainViewReparateur;
    @FXML
    private TextField idT;
    @FXML
    private TextField cinT;
    @FXML
    private TextField faxT;
    @FXML
    private TextField nomT;
    @FXML
    private TextField prenomT;
    @FXML
    private TextField mailT;
    @FXML
    private TextField numeroT;
    @FXML
    private TextField adresseT;
    @FXML
    private TextField descriptionT;
    @FXML
    private Button ajouter;
    @FXML
    private ComboBox<String> etatT;
        @FXML private TableView<Reparateur> tableRep;
     @FXML  private TableColumn<Reparateur,Integer> idRep;
     @FXML  private TableColumn<Reparateur, Integer> cinRep;
      @FXML  private TableColumn<Reparateur, Integer> faxRep; 
     @FXML  private TableColumn<Reparateur, String> nomRep;   
      @FXML  private TableColumn<Reparateur, String> prenomRep;
      @FXML  private TableColumn<Reparateur, String> mailRep;
     @FXML  private TableColumn<Reparateur, Integer> numeroRep;
      @FXML  private TableColumn<Reparateur, String> adresseRep;
     @FXML  private TableColumn<Reparateur, String> etatRep;
      @FXML  private TableColumn<Reparateur, String> descriptionRep;

    @FXML
    private Button modifier;
    @FXML
    private Button supprimer;
    @FXML
    private Button affecterRep;
    @FXML
    private TextField rechercherT;
    @FXML
    private Button rechercherRep;
    @FXML
    private Button affi;
    ReparateurService ES=new ReparateurService();
     public static int cinreparateur;
     Connection C = Connexion.getInstance().getCon();
    @FXML
    private Button imprimer;
    @FXML
    private Button raf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modifier.setVisible(false);
        supprimer.setVisible(false);
   affecterRep.setVisible(false);
       idT.setVisible(false);
       afficherReparateur();
       etatT.getItems().addAll("oui","non");
       affi.setVisible(true);
       
       Pattern intPattern = Pattern.compile("-?\\d*");
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (intPattern.matcher(change.getControlNewText()).matches()) {
                return change;
            }
            return null;
        };
        TextFormatter textFormatter = new TextFormatter(filter);
        TextFormatter tF1 = new TextFormatter(filter);
        TextFormatter tF2 = new TextFormatter(filter);
        TextFormatter tF3 = new TextFormatter(filter);
     
        
        idT.setTextFormatter(textFormatter);
        cinT.setTextFormatter(tF1);
        faxT.setTextFormatter(tF2);
    
    
        numeroT.setTextFormatter(tF3);
      
       
    
       
       
       
       

        // TODO
    }    

    @FXML
    private void ajouterReparateur(ActionEvent event) {
        
         String erreur="";
     if(numeroT.getText().length()!=8)
         erreur= erreur+ "verifier la longueur du telephone";
    if(!mailT.getText().matches("(?:\\w|[\\-_])+(?:\\.(?:\\w|[\\-_])+)*\\@(?:\\w|[\\-_])+(?:\\.(?:\\w|[\\-_])+)+" )) 
           erreur=erreur+"verifier votre adresseMail";

    
    if (!erreur.equals("")) {
          Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate Fields");
                alert.setHeaderText(null);
                alert.setContentText(erreur);
                alert.showAndWait();
        } else {
       
     Reparateur e= new Reparateur();
        e.setCinRep(Integer.parseInt( cinT.getText()));
        e.setFaxRep(Integer.parseInt(faxT.getText()));
       e.setNomRep(nomT.getText());
        e.setPrenomRep(prenomT.getText());
          e.setMailRep(mailT.getText());
      e.setNumeroRep(Integer.parseInt( numeroT.getText()));
         e.setAdresseRep(adresseT.getText()); 
        e.setEtatRep( etatT.getSelectionModel().getSelectedItem());
        // e.setEtatEx(etatT.getText());
          e.setDescriptionRep(descriptionT.getText());

        ES.ajouterReparateur(e);
        afficherReparateur();
    }

    }
    
      public void afficherReparateur() {
        idRep.setCellValueFactory(new PropertyValueFactory<Reparateur, Integer>("idRep"));
       cinRep.setCellValueFactory(new PropertyValueFactory<Reparateur, Integer>("cinRep"));
        faxRep.setCellValueFactory(new PropertyValueFactory<Reparateur, Integer>("faxRep"));
        nomRep.setCellValueFactory(new PropertyValueFactory<Reparateur, String>("nomRep"));
       prenomRep.setCellValueFactory(new PropertyValueFactory<Reparateur, String>("prenomRep"));
        mailRep.setCellValueFactory(new PropertyValueFactory<Reparateur, String>("mailRep"));
        numeroRep.setCellValueFactory(new PropertyValueFactory<Reparateur, Integer>("numeroRep"));
        adresseRep.setCellValueFactory(new PropertyValueFactory<Reparateur, String>("adresseRep"));
        etatRep.setCellValueFactory(new PropertyValueFactory<Reparateur, String>("etatRep"));
        descriptionRep.setCellValueFactory(new PropertyValueFactory<Reparateur, String>("descriptionRep"));
        
        tableRep.setItems(ES.afficherReparateur());
        tableRep.setEditable(true);
    
}


    @FXML
    private void itemClickedR(MouseEvent event) {
        
            Reparateur Os = tableRep.getSelectionModel().getSelectedItem();
         idT.setText(""+Os.getIdRep());
        cinT.setText(""+Os.getCinRep());
        faxT.setText(""+Os.getFaxRep());
       nomT.setText(Os.getNomRep());
         prenomT.setText(Os.getPrenomRep());
         mailT.setText(Os.getMailRep());
         numeroT.setText(""+Os.getNumeroRep());
         adresseT.setText(Os.getAdresseRep());
      
            etatT.getSelectionModel().select(Os.getEtatRep());
          
 
           descriptionT.setText(Os.getDescriptionRep());
        
     
        ajouter.setVisible(false);
     modifier.setVisible(true);
     supprimer.setVisible(true);
     affecterRep.setVisible(true);

        
        
    
    }
    

    @FXML
    private void modifierReparateur(ActionEvent event) {
        
        
        
         String erreur="";
     if(numeroT.getText().length()!=8)
         erreur= erreur+ "verifier la longueur du telephone";
    if(!mailT.getText().matches("(?:\\w|[\\-_])+(?:\\.(?:\\w|[\\-_])+)*\\@(?:\\w|[\\-_])+(?:\\.(?:\\w|[\\-_])+)+" )) 
           erreur=erreur+"verifier votre adresseMail";

    
    if (!erreur.equals("")) {
           Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate Fields");
                alert.setHeaderText(null);
                alert.setContentText(erreur);
                alert.showAndWait();
        } else {
       
        
             Reparateur O = new Reparateur(Integer.parseInt(idT.getText()), Integer.parseInt(cinT.getText()), Integer.parseInt(faxT.getText()), nomT.getText(), prenomT.getText(),mailT.getText(), Integer.parseInt(numeroT.getText()),adresseT.getText(),etatT.getSelectionModel().getSelectedItem(),descriptionT.getText());
        ES.modifierReparateur(O);
      ajouter.setVisible(true);
        modifier.setVisible(false);
        idT.clear();
       cinT.clear();
       faxT.clear();
      nomT.clear();
        prenomT.clear();
        mailT.clear();
        numeroT.clear();
        adresseT.clear();
        
      
        descriptionT.clear();

        afficherReparateur();
        //Rafraichir();
        
    }
    
    }
    
     public void Rafraichir()
    {
       
        idT.clear();
        cinT.clear();
       faxT.clear();
       nomT.clear();
       prenomT.clear();
       mailT.clear();
        numeroT.clear();
        adresseT.clear();
      
        descriptionT.clear();
        
      
        ajouter.setVisible(true);
        modifier.setVisible(false);
        supprimer.setVisible(false);
        affecterRep.setVisible(false);
        

    }

    @FXML
    private void supprimerReparateur(ActionEvent event) {
        supprimer.setOnAction(e->{
            Alert alert=new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure to delete");
           Optional <ButtonType> action=alert.showAndWait();
           if(action.get()==ButtonType.OK)
                {
        Reparateur Os = tableRep.getSelectionModel().getSelectedItem();
        ES.supprimerReparateur(Os.getIdRep());
        afficherReparateur();
       Rafraichir();}
           
                });

    }

    @FXML
    private void affecterRep(ActionEvent event) throws IOException {
        cinreparateur=Integer.parseInt(cinT.getText());
     AnchorPane paneExpert=FXMLLoader.load(getClass().getResource("affectationFXML.fxml"));
      mainViewReparateur.getChildren().setAll(paneExpert);
      // Alert.desplay("affectation","vous avec affectÃ© l'expert avec le cin:"+cinreparateur);
        

    }

    @FXML
    private void rechercherRep(ActionEvent event) {
        tableRep.setItems(ES.rechercherReparateur(rechercherT.getText()));
      tableRep.setEditable(true);
      affi.setVisible(true);

    }

    @FXML
    private void affi(ActionEvent event) {
        
         idRep.setCellValueFactory(new PropertyValueFactory<Reparateur, Integer>("idRep"));
       cinRep.setCellValueFactory(new PropertyValueFactory<Reparateur, Integer>("cinRep"));
        faxRep.setCellValueFactory(new PropertyValueFactory<Reparateur, Integer>("faxRep"));
        nomRep.setCellValueFactory(new PropertyValueFactory<Reparateur, String>("nomRep"));
       prenomRep.setCellValueFactory(new PropertyValueFactory<Reparateur, String>("prenomRep"));
        mailRep.setCellValueFactory(new PropertyValueFactory<Reparateur, String>("mailRep"));
        numeroRep.setCellValueFactory(new PropertyValueFactory<Reparateur, Integer>("numeroRep"));
        adresseRep.setCellValueFactory(new PropertyValueFactory<Reparateur, String>("adresseRep"));
        etatRep.setCellValueFactory(new PropertyValueFactory<Reparateur, String>("etatRep"));
        descriptionRep.setCellValueFactory(new PropertyValueFactory<Reparateur, String>("descriptionRep"));
        
        tableRep.setItems(ES.afficherReparateur());
        tableRep.setEditable(true);
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
    private void imprimer(ActionEvent event) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
          printNode(tableRep);
    }

    @FXML
    private void raf(ActionEvent event) {
     Rafraichir();
    }
    
}
