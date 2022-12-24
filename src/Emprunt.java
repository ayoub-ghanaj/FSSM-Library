package src;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Emprunt implements Initializable {

    @FXML
    private ComboBox<TyCat> docs_list;

    @FXML
    private ImageView doc_img;

    @FXML
    private TextField etu_id;

    @FXML
    private TextField etu_nom;

    @FXML
    private Button close_btn;

    @FXML
    private TextField etu_prenom;

    @FXML
    private ImageView Etud_img;

    @FXML
    private TextField doc_nom;

    @FXML
    private TextField doc_id;


    @FXML
    private Text error_date;
    @FXML
    private ComboBox<TyCat> etuants_list;

    @FXML
    private DatePicker return_date;

    @FXML
    public void handleExitClick(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        if(home_controller != null){
            home_controller.search_handler_func() ;
        }else if(home_controller1 != null){
            home_controller1.search_handler_func();
        }else if(home_controller2!= null){
            home_controller2.search_handler_func();
        }
        father.close();
    }


    private Stage father ;
    private int staff_id ;
    private String  staff_name ;
    public void setStaffId(int staffid){
        this.staff_id = staffid;
    }
    public void setStaffName( String staff_nam){
        this.staff_name =  staff_nam;
    }
    private double xOffset = 0;
    private double yOffset = 0;
    private double xOffsetadd = 0;
    private double yOffsetadd = 0;
    private boolean staff_is_admin ;
    public void setFather(Stage stg){
        this.father = stg;
    }

    private final SQLiteTools sql = new SQLiteTools();
    @FXML
    void addDocHandler(ActionEvent event) {

    }

    @FXML
    void refocHandler(ActionEvent event) throws SQLException, ClassNotFoundException {
        fillcomboDocs();
    }

    @FXML
    void addEtuHandler(ActionEvent event) {

    }

    @FXML
    void refEtuHandler(ActionEvent event) throws SQLException, ClassNotFoundException {
        fillcomboEtudiants();
    }

    @FXML
    void EmpruntHandler(ActionEvent event) throws SQLException, ClassNotFoundException {
        error_date.setVisible(false);
        // Get the selected date from the DatePicker control
        LocalDate selectedDate = return_date.getValue();

        // Create a DateTimeFormatter object with the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Format the selected date using the formatter
        String return_date = selectedDate.format(formatter);

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        String emprunt_date = now.format(formatter);

        LocalDate date1 = LocalDate.parse(emprunt_date, formatter);
        LocalDate date2 = LocalDate.parse(return_date, formatter);

// Compare the dates using the compareTo method
        int result = date1.compareTo(date2);
        if (result < 0) {
            if(document_id!="0" && etudiant_id != "0"  ){
                sql.addEmprunts(Integer.valueOf(document_id) , Integer.valueOf(etudiant_id),Integer.valueOf(this.staff_id) ,emprunt_date, return_date);
                fillcomboDocs();
            }
        }else {
            error_date.setVisible(true);
        }


    }
       private String etudiant_id ="0" ;
       private String document_id ="0";
    @FXML
    void choosdoc(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (docs_list == null) {
            document_id = "0";
        } else {
            document_id = String.valueOf(docs_list.getValue());
            ResultSet rs = sql.displayDoc(Integer.valueOf(document_id));
            while (rs.next()){
                doc_id.setText(rs.getString("doc_id"));
                doc_nom.setText(rs.getString("doc_libelle"));
                Image img = new Image(rs.getString("image"));
                doc_img.setImage(img);
            }
            rs.close();
        }
    }
    @FXML
    void choosetu(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (etuants_list == null) {
            etudiant_id = "0";
        } else {
            etudiant_id = String.valueOf(etuants_list.getValue());
            ResultSet rs = sql.displayEtudiant(Integer.valueOf(etudiant_id));
            while (rs.next()){
                etu_id.setText(rs.getString("etud_id"));
                etu_nom.setText(rs.getString("nom"));
                etu_prenom.setText(rs.getString("prenom"));
                Image img = new Image(rs.getString("image"));
                Etud_img.setImage(img);
            }
            rs.close();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillcomboDocs();
            fillcomboEtudiants();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void fillcomboEtudiants() throws SQLException, ClassNotFoundException {
        etuants_list.getItems().clear();
        ResultSet types_list = sql.displayStudents();
        ObservableList<TyCat> ty_ls = etuants_list.getItems();
        // Iterate through the result set and create objects for each record
        try {
            while (types_list.next()) {
                String id_in = String.valueOf(types_list.getInt("etud_id"));
                String name = types_list.getString("cne");
                ty_ls.add(new TyCat(id_in,name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void fillcomboDocs() throws SQLException, ClassNotFoundException {
        docs_list.getItems().clear();
        etuants_list.getItems().clear();
        ResultSet types_list = sql.displayDocsAvailable("","0","0");
        ObservableList<TyCat> ty_ls = docs_list.getItems();
        // Iterate through the result set and create objects for each record
        try {
            while (types_list.next()) {
                String id_in = String.valueOf(types_list.getInt("doc_id"));
                String name = types_list.getString("doc_libelle");
                ty_ls.add(new TyCat(id_in,name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Books_page_controller home_controller1;
    private Etudiants_page_controller home_controller2;
    private Emprunts_page_controller home_controller;
    public void setData( Stage father , int staff_id,Emprunts_page_controller  controller)  {
        this.staff_id = staff_id;
        this.father = father;
        home_controller = controller;
    }
    public void setDataDoc(int Doc_id , Stage father , int staff_id,Books_page_controller  controller) throws SQLException, ClassNotFoundException {
        this.staff_id = staff_id;
        this.father = father;
        home_controller1 =  controller;
        for (TyCat item : docs_list.getItems()) {
            if (item.getValue().equals(String.valueOf( Doc_id))) {
                docs_list.setValue(item);
                document_id = String.valueOf(Doc_id);
                break;
            }
        }
        document_id = String.valueOf(docs_list.getValue());
        ResultSet rs = sql.displayDoc(Integer.valueOf(document_id));
        while (rs.next()){
            doc_id.setText(rs.getString("doc_id"));
            doc_nom.setText(rs.getString("doc_libelle"));
            Image img = new Image(rs.getString("image"));
            doc_img.setImage(img);
        }
        rs.close();

    }
    public void setDataEtu(int Etu_id , Stage father , int staff_id , Etudiants_page_controller controller) throws SQLException, ClassNotFoundException {
        this.staff_id = staff_id;
        this.father = father;
        home_controller2 = controller;
        for (TyCat item : etuants_list.getItems()) {
            if (item.getValue().equals(String.valueOf( Etu_id))) {
                etuants_list.setValue(item);
                document_id = String.valueOf(Etu_id);
                break;
            }
        }
        etudiant_id = String.valueOf(etuants_list.getValue());
        ResultSet rs = sql.displayEtudiant(Integer.valueOf(etudiant_id));
        while (rs.next()){
            etu_id.setText(rs.getString("etud_id"));
            etu_nom.setText(rs.getString("nom"));
            etu_prenom.setText(rs.getString("prenom"));
            Image img = new Image(rs.getString("image"));
            Etud_img.setImage(img);
        }
        rs.close();
    }
}
