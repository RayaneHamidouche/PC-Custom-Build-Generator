package fxml_ui;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class FXMLDocumentController implements Initializable {
    private final static String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final static String url = "jdbc:sqlserver://localhost:1433;databaseName=NEA_customcomputers; integratedSecurity=true";
    
    @FXML
    private Text ErrorMessage;
    private Stage stage;
    private Parent root;
    Button Login;
    Button SignUp;
    Button Delete;
    public TextField Username;
    public PasswordField Password;
    public int current_UserID;
 

    public String encryptString(String input) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger bigInt = new BigInteger(messageDigest); 
        
        return bigInt.toString(16);
    }
    
    
    @FXML
    private void Login(ActionEvent event) throws IOException, NoSuchAlgorithmException{
        String user = (encryptString(Username.getText())).substring(0,16);
        String pass = (encryptString(Password.getText())).substring(0,16);
        
        
            try{    
                Connection conn = DriverManager.getConnection(url);
                Statement sta = conn.createStatement();
                String DB_request = "SELECT Username,Pass,UserID"
                        + " FROM customer"
                        +" WHERE Username = '"+user+"'";
                ResultSet rs = sta.executeQuery(DB_request);
                
                if(rs.next()){
                    if(user.equals(rs.getString("Username"))&&pass.equals(rs.getString("Pass"))){ 
                        current_UserID = rs.getInt("UserID");
                        
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeUser.fxml"));
                        root = loader.load();
                        WelcomeUserController welcomeuser = loader.getController();
                        welcomeuser.WelcomeUser(current_UserID);
                        
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        
                }else if(user.equals(rs.getString("Username"))&&!pass.equals(rs.getString("Pass"))){
                        ErrorMessage.setText("Incorrect password");
                    
                }
                }else{ErrorMessage.setText("There is no account with that username or you have not entered a username");}
                
                
 
            }catch(SQLException ex){
                Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ERROR: Cannot connect to database");
            }
            
            
    }
    
    
    @FXML 
    void SignUp(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        String user = (encryptString(Username.getText())).substring(0,16);
        String pass = (encryptString(Password.getText())).substring(0,16);
        
            
            try{    
                Connection conn = DriverManager.getConnection(url);
                Statement sta = conn.createStatement();
                String DB_request = "SELECT * FROM customer WHERE Username = '"+user+"'";
                ResultSet rs = sta.executeQuery(DB_request);
              
                if(rs.next()){
                    ErrorMessage.setText("Account with the username already exists");
                    return;
                }
                if(Password.getText().trim().isEmpty()){
                    ErrorMessage.setText("You need to have a Password");
                    return;
                }
                if(Username.getText().trim().isEmpty()){
                    ErrorMessage.setText("You need to have a Username");
                    return;
                }
                
                
        
            }catch(SQLException ex){
                Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ERROR: Cannot connect to database");
            }
            
                
            
            try{    
                Connection conn = DriverManager.getConnection(url);
                Statement sta = conn.createStatement();
                String DB_request = "INSERT INTO customer (Username,Pass) "
                                  + "VALUES ('"+user+"','"+pass+"')";
                sta.execute(DB_request);
              
        
            }catch(SQLException ex){
                Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ERROR: Cannot connect to database");
            }
            
            
            
            Parent root = FXMLLoader.load(getClass().getResource("WelcomeUser.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
           
            
     
     }
    
     @FXML 
    private void Delete(ActionEvent event) throws IOException, NoSuchAlgorithmException {
         String user = (encryptString(Username.getText())).substring(0,16);
         try{    
            Connection conn = DriverManager.getConnection(url);
            Statement sta = conn.createStatement();
            String DB_request = "DELETE FROM customer WHERE Username = '"+user+"'";
            
            sta.execute(DB_request);


            }catch(SQLException ex){
                Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ERROR: Cannot connect to database");
            }
         
         ErrorMessage.setText("Successfully deleted");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
   
}
