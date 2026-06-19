package fxml_ui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AssignBudgetController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    
    private final static String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final static String url = "jdbc:sqlserver://localhost:1433;databaseName=NEA_customcomputers; integratedSecurity=true";
    @FXML
    private Text ErrorMessage2,CPU,Motherboard,RAM,GPU,Storage,COOLER,PSU,CASE,CPU_price,Motherboard_price,RAM_price,
    GPU_price,Storage_price,COOLER_price,PSU_price,CASE_price,Total_output,Saved;
    public TextField EnterBudget;
    public TextField PC_NAME;
    float total = 0;
    private Parent root;
    private Stage stage;
    DecimalFormat df = new DecimalFormat("0.00");
    String CPU_Component; float CPU_Componentprice;
    String Motherboard_Component; float Motherboard_Componentprice;
    String RAM_Component; float RAM_Componentprice;
    String GPU_Component; float GPU_Componentprice;
    String Storage_Component; float Storage_Componentprice;
    String COOLER_Component; float COOLER_Componentprice;
    String CASE_Component; float CASE_Componentprice;
    String PSU_Component; float PSU_Componentprice;
    
    static int UserID;
    
    public void AssignBudget(int current_UserID){
        UserID = current_UserID;
    }
    
    
    
    public void Enter(ActionEvent event) throws IOException, SQLException{
    CPU_Component = "";CPU_Componentprice = 0;
    Motherboard_Component = "";Motherboard_Componentprice = 0;
    RAM_Component = "";RAM_Componentprice = 0;
    GPU_Component = "";GPU_Componentprice = 0;
    Storage_Component = "";Storage_Componentprice = 0;
    COOLER_Component = "";COOLER_Componentprice = 0;
    CASE_Component = "";CASE_Componentprice = 0;
    PSU_Component = "";PSU_Componentprice = 0;
    
    total = 0;
    int budget;
    try{    
        budget = Integer.parseInt(EnterBudget.getText());
    }catch(Exception ex){
        ErrorMessage2.setText("Budget must be an integer");
        return;
    
    }
        if(budget<500||budget>2000){
            ErrorMessage2.setText("Budget must be between £500 and £2000");
            
            return;
        }
        double CPU_budget = budget*0.25;
        double MB_budget = budget*0.15;
        double RAM_budget = budget*0.1;
        double GPU_budget = budget*0.2;
        double STOR_budget = budget*0.075;
        double CASE_budget = budget*0.075;
        double PSU_budget = budget*0.075;
        double COOL_budget=budget*0.05; 
        String model = "";
        String brand = "";
        String Socket = "";
        Connection conn = DriverManager.getConnection(url);
        Statement sta = conn.createStatement();
        
        
        
        try{
            String DB_request = "Select brand,model,price"
                    + " FROM CPU"+
                    " WHERE price < "+CPU_budget
                    +"and price > "+CPU_budget*0.9
                    +" ORDER BY price DESC";
            ResultSet rs = sta.executeQuery(DB_request);

            if(rs.next()){
                model = rs.getString("model");
                brand = rs.getString("brand");
                CPU_price.setText(rs.getString("price"));
                total = total + rs.getFloat("price");
                CPU_Componentprice = rs.getFloat("price");
                CPU_Component = brand+" "+model; 
                CPU.setText(CPU_Component);
                
                
            }   
              
            
        }catch(SQLException ex){
            Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR: Cannot connect to database");
        }
        
        try{
            String DB_request = "Select SocketType"
            +" FROM Socket"+
            " WHERE model = '"+model+"'";
            ResultSet rs = sta.executeQuery(DB_request);
            if(rs.next()){
               Socket = rs.getString("SocketType");
            }   
            
        }catch(SQLException ex){
            Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR: Cannot connect to database");
        }
            
             
        try{
            String DB_request = "Select brand,model,price"
            + " FROM MOTHERBOARD"+
            " WHERE price < "+MB_budget
            +"and price > "+MB_budget*0.5
            +" and socket = '"+Socket+"'";
            ResultSet rs = sta.executeQuery(DB_request);
            
            if(rs.next()){
                model = rs.getString("model");
                brand = rs.getString("brand");
                Motherboard_price.setText(rs.getString("price"));
                total = total + rs.getFloat("price");
                Motherboard_Componentprice = rs.getFloat("price");
                Motherboard_Component = brand+" "+model;
                Motherboard.setText(Motherboard_Component);
            }
                
            
            }catch(SQLException ex){
            Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR: Cannot connect to database");
            }
            
            Motherboard.setText(model + " "+brand);
            
            try{
            String DB_request = "Select brand,model,price"
                    + " FROM RAM"+
                    " WHERE price < "+RAM_budget
                    +"and price > "+RAM_budget*0.95;
            ResultSet rs = sta.executeQuery(DB_request);
            if(rs.next()){
                model = rs.getString("model");
                total = total + rs.getFloat("price");
                RAM_Componentprice = rs.getFloat("price");
                RAM_Component = brand +" "+ model;
                RAM_price.setText(rs.getString("price"));
                RAM.setText(RAM_Component);
                
            }   
            
            }catch(SQLException ex){
            Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR: Cannot connect to database");
            }
              
            
            try{
                String DB_request = "Select brand,model,chipset,price"
                    + " FROM GPU"+
                    " WHERE price < "+GPU_budget
                    +"and price > "+GPU_budget*0.9;
            ResultSet rs = sta.executeQuery(DB_request);
            if(rs.next()){
                brand = rs.getString("brand");
                model = rs.getString("chipset");
                total = total + rs.getFloat("price");
                GPU_Componentprice = rs.getFloat("price");
                GPU_Component = brand+" "+rs.getString("model")+" "+model;
                GPU_price.setText(rs.getString("price"));
                GPU.setText(GPU_Component);
            }   
            
            }catch(SQLException ex){
            Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR: Cannot connect to database");
            }
            
            
            try{
            String DB_request = "Select brand,model,capacity,price"
                    + " FROM STORAGE"+
                    " WHERE price < "+STOR_budget
                    +"and price > "+STOR_budget*0.95;
            ResultSet rs = sta.executeQuery(DB_request);
            if(rs.next()){
                total = total + rs.getFloat("price");
                Storage_Componentprice = rs.getFloat("price");
                Storage_Component =rs.getString("model")+" "+(rs.getString("capacity")).replace("000000000","")+"GB";
                Storage.setText(Storage_Component);
                Storage_price.setText(rs.getString("price"));
            }   
            
            }catch(SQLException ex){
            Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR: Cannot connect to database");
            }
            
            try{
            String DB_request = "Select brand,model,price"
                    + " FROM PC_CASE"+
                    " WHERE price < "+CASE_budget
                    +"and price > "+CASE_budget*0.9;
            ResultSet rs = sta.executeQuery(DB_request);
            if(rs.next()){
                CASE_Componentprice = rs.getFloat("price");
                CASE_Component = brand +" "+model;
                CASE.setText(CASE_Component);
                total = total + rs.getFloat("price");
                CASE_price.setText(rs.getString("price"));
                
            }   
            
            }catch(SQLException ex){
            Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR: Cannot connect to database");
            }
            
            
            try{
            String DB_request = "Select brand,model,price"
                    + " FROM COOLER"+
                    " WHERE price < "+COOL_budget
                    +"and price > "+COOL_budget*0.95;
            ResultSet rs = sta.executeQuery(DB_request);
            if(rs.next()){
                total = total + rs.getFloat("price");
                COOLER_Componentprice = rs.getFloat("price");
                COOLER_Component = rs.getString("model") +" "+rs.getString("brand");
                COOLER.setText(COOLER_Component);
                COOLER_price.setText(rs.getString("price"));
            }   
            
            }catch(SQLException ex){
                Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ERROR: Cannot connect to database");
            }
            
            
            
            try{
            String DB_request = "Select brand,model,price"
                    + " FROM PSU"+
                    " WHERE price < "+PSU_budget
                    +"and price > "+PSU_budget*0.95;
            ResultSet rs = sta.executeQuery(DB_request);
            if(rs.next()){
                PSU_price.setText(rs.getString("price"));
                total = total + rs.getFloat("price");
                PSU_Componentprice = rs.getFloat("price");
                PSU_Component = rs.getString("model")+" "+rs.getString("brand");
                PSU.setText(rs.getString("model")+" "+rs.getString("brand"));
                Total_output.setText(df.format(total));
            }   
            
            }catch(SQLException ex){
                Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ERROR: Cannot connect to database");
            }


            
            
            
            
            
        
        
    } 
    
    public void Save(ActionEvent event) throws IOException, SQLException{
    String PC_Name = PC_NAME.getText();
    if(PC_Name.equals("")){
        ErrorMessage2.setText("PC must have a name");
        return;
        
    }
    
    String DB_request = "INSERT INTO computer (CPU,MOBO,RAM,GPU,PCCASE,PSU,STORAGE,COOLER,UserID,Price, PC_name)"
            + "VALUES ('"+CPU_Component+"','"+Motherboard_Component+"','"+RAM_Component+"','"+
            GPU_Component+"','"+CASE_Component+"','"+PSU_Component+"','"+Storage_Component+"','"+COOLER_Component+"','"+UserID+"',"+df.format(total)+",'"+PC_Name+"')";
    
    try{
        Connection conn = DriverManager.getConnection(url);
        Statement sta = conn.createStatement();
        sta.execute(DB_request);
        
    
    }catch(Exception ex){
        Logger.getLogger(FXML_UI.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println("ERROR: Cannot connect to database");
    
    
    }
    Saved.setText("PC successfully saved");
    
    
    }
    
    public void Return(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeUser.fxml"));
        root = loader.load();
        WelcomeUserController welcomeuser = loader.getController();
        welcomeuser.WelcomeUser(UserID);
                        
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    
    
    
    
    }
    
    
}
