package fxml_ui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class ViewPCsController implements Initializable{
    private Stage stage;
    private final static String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final static String URL = "jdbc:sqlserver://localhost:1433;databaseName=NEA_customcomputers; integratedSecurity=true";
    
    public static ArrayList<String> PC_names = new ArrayList<String>();
    public static ArrayList<String> PCIDs = new ArrayList<String>();
    private Parent root;
    static int UserID;
    private String PC_name;
    

    public void ViewPC(int current_UserID) throws IOException{
        
        UserID = current_UserID;
        try{
             Connection conn = DriverManager.getConnection(URL);
             Statement sta = conn.createStatement();
             String DB_request = "SELECT *"
                     + " FROM computer"
                     + " WHERE UserID = "+UserID;
            ResultSet rs = sta.executeQuery(DB_request);
            
            
                
            while(rs.next()){
                PC_names.add(rs.getString("PC_name"));
                PCIDs.add(rs.getString("PCID"));
            }
            
        
        }catch(SQLException ex){
            Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR: Cannot connect to database");
        }
        
        
        while(PC_names.size()<5){
            PC_names.add("Empty Slot");
            PCIDs.add("Empty ID");
        }

    }    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void View1(ActionEvent event)throws IOException{
        String PCID = PCIDs.get(0);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PCDetails.fxml"));
        root = loader.load();
        PCDetailsController Details = loader.getController();
        Details.PCDetails(PCID,UserID);
        
        
        Parent root = FXMLLoader.load(getClass().getResource("PCDetails.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    
        
                
    } 
   
    public void View2(ActionEvent event)throws IOException{
        String PCID = PCIDs.get(1);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PCDetails.fxml"));
        root = loader.load();
        PCDetailsController Details = loader.getController();
        Details.PCDetails(PCID,UserID);
        
        
        Parent root = FXMLLoader.load(getClass().getResource("PCDetails.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } 
    
    public void View3(ActionEvent event)throws IOException{
        String PCID = PCIDs.get(2);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PCDetails.fxml"));
        root = loader.load();
        PCDetailsController Details = loader.getController();
        Details.PCDetails(PCID,UserID);
        
        
        Parent root = FXMLLoader.load(getClass().getResource("PCDetails.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } 
    
    public void View4(ActionEvent event)throws IOException{
        String PCID = PCIDs.get(3);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PCDetails.fxml"));
        root = loader.load();
        PCDetailsController Details = loader.getController();
        Details.PCDetails(PCID,UserID);
        
        
        Parent root = FXMLLoader.load(getClass().getResource("PCDetails.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } 
    
    public void View5(ActionEvent event)throws IOException{
        String PCID = PCIDs.get(4);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PCDetails.fxml"));
        root = loader.load();
        PCDetailsController Details = loader.getController();
        Details.PCDetails(PCID,UserID);
        
        
        Parent root = FXMLLoader.load(getClass().getResource("PCDetails.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } 
    
    public void Return(ActionEvent event)throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeUser.fxml"));
        root = loader.load();
        WelcomeUserController welcomeuser = loader.getController();
        welcomeuser.WelcomeUser(UserID);
        
        Parent root = FXMLLoader.load(getClass().getResource("WelcomeUser.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
