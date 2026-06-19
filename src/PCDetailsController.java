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

public class PCDetailsController implements Initializable {
    
    private final static String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final static String URL = "jdbc:sqlserver://localhost:1433;databaseName=NEA_customcomputers; integratedSecurity=true";
    @FXML
    Text CPU,Motherboard,RAM,GPU,PSU,Cooler,Storage,Case,Name,Price,Deleted;
    private Parent root;
    private Stage stage;
    static String PCID_final;
    static int current_UserID;
    
    public void PCDetails(String PCID, int UserID){
        PCID_final = PCID;
        current_UserID = UserID;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try{    
            Connection conn = DriverManager.getConnection(URL);
            Statement sta = conn.createStatement();
            String DB_request = "SELECT *"
                    + " FROM computer"
                    +" WHERE PCID = "+ PCID_final;
            ResultSet rs = sta.executeQuery(DB_request);
            if(rs.next()){
                CPU.setText(rs.getString("CPU"));
                Motherboard.setText(rs.getString("MOBO"));
                RAM.setText(rs.getString("RAM"));
                GPU.setText(rs.getString("GPU"));
                Storage.setText(rs.getString("STORAGE"));
                Case.setText(rs.getString("PCCASE"));
                Cooler.setText(rs.getString("COOLER"));
                PSU.setText(rs.getString("PSU"));
                Name.setText(rs.getString("PC_name"));
                Price.setText("Price : £"+rs.getString("Price"));
            }
                
        }catch(SQLException ex){
            Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR: Cannot connect to database");
        }
    }

    public void Return(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewPCs.fxml"));
        root = loader.load();
        ViewPCsController PCViewer = loader.getController();
        PCViewer.ViewPC(current_UserID);
        
        
        Parent root = FXMLLoader.load(getClass().getResource("ViewPCs.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    
    }
    public void Delete(ActionEvent event)throws IOException{
        if(PCID_final.equals("Empty ID")){
            Deleted.setText("There is no PC in this slot");
            return;
        }
        
        
        try{    
            Connection conn = DriverManager.getConnection(URL);
            Statement sta = conn.createStatement();
            String DB_request = "DELETE FROM computer WHERE PCID ="+PCID_final;
            
            sta.execute(DB_request);


            }catch(SQLException ex){
                Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ERROR: Cannot connect to database");
            }
        Deleted.setText("Successfully deleted");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewPCs.fxml"));
        root = loader.load();
        ViewPCsController PCViewer = loader.getController();
        PCViewer.ViewPC(current_UserID);
    
    }
    
}
