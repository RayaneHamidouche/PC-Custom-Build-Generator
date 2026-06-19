package fxml_ui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomeUserController implements Initializable {
    private final static String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final static String URL = "jdbc:sqlserver://localhost:1433;databaseName=NEA_customcomputers; integratedSecurity=true";
    
    @FXML
    private Text NumOfBuilds, ErrorMessage;
    private Parent root;
    private Stage stage;
    
    String count = "";
    
    
    private static int UserID;
    public void WelcomeUser(int current_UserID){
        UserID = current_UserID;
        try{
            Connection conn = DriverManager.getConnection(URL);
            Statement sta = conn.createStatement();
            String DB_request = "SELECT COUNT(PCID) As 'COUNT' From computer WHERE UserID = '"+UserID+"'";
            ResultSet rs = sta.executeQuery(DB_request);
            if(rs.next()){
                count = rs.getString("COUNT");
                NumOfBuilds.setText("You have "+count+"/5 builds in our system");
            }
           
        
        }catch(SQLException ex){
            Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR: Cannot connect to database");

        }
       
    }   
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    
    
    public void CreateNewPC(ActionEvent event) throws IOException{
        if(count.equals("5")){
            ErrorMessage.setText("You have too many builds.\nDelete some before creating a new one.");
            return;
       
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AssignBudget.fxml"));
        root = loader.load();
        AssignBudgetController AssignBudget = loader.getController();
        AssignBudget.AssignBudget(UserID);
      
        Parent root = FXMLLoader.load(getClass().getResource("AssignBudget.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    
    public void ViewPC(ActionEvent event)throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewPCs.fxml"));
        root = loader.load();
        ViewPCsController PCViewer = loader.getController();
        PCViewer.ViewPC(UserID);
        
        
        Parent root = FXMLLoader.load(getClass().getResource("ViewPCs.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    
    
    }
    
    public void SignOut(ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    
    }  
}
