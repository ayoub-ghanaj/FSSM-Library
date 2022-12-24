package src;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.Long.MAX_VALUE;

public class EditeStaffController implements Initializable {

    @FXML
    private TextField label_textField;

    @FXML
    private Button close_btn;

    @FXML
    private ComboBox<TyCat> Type_combo;



    @FXML
    private TextField staff_id_tf;
    @FXML
    private TextField staff_nom_tf;
    @FXML
    private TextField staff_prenom_tf;
    @FXML
    private TextField staff_cin_tf;
    @FXML
    private TextField staff_phone_tf;

    @FXML
    private PasswordField staff_pass_tf;
    @FXML
    private GridPane dataGridListup;
    @FXML
    private GridPane dataGridListdown;
    private String id;

    private Stage father;
    private double xOffset = 0;
    private double yOffset = 0;

    final private SQLiteTools sql = new SQLiteTools();

    private File File_image =null ;
    private Image image = null;
    private  Staff_page_controller home_controller;
    private  EditeBookController home_controller1;
    private  EditeEleveController home_controller2;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.close_btn.setOnAction(e->{
                try {
                    if(home_controller != null){
                        home_controller.search_handler_func();
                    }else if(home_controller1 != null){
                        home_controller1.refreshdata();
                    }else if(home_controller2 != null){
                        home_controller2.refreshdata();
                    }
                    father.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }catch (Exception e){

        }


    }
    public void setDataEB(String id_in , Stage father_in , EditeBookController  home_con) throws SQLException, ClassNotFoundException {
        this.id = id_in;
        this.home_controller1 = home_con;
        staff_id_tf.setText(id_in);
        fillcombos();
        this.father = father_in;
        ResultSet data = sql.displayStaff(Integer.valueOf(id_in));
        while (data.next()){
            for (TyCat item : Type_combo.getItems()) {
                if (item.getValue().equals(String.valueOf( data.getInt("role")))) {
                    Type_combo.setValue(item);
                    break;
                }
            }
            staff_cin_tf.setText(data.getString("cin"));
            staff_nom_tf.setText(data.getString("nom"));
            staff_prenom_tf.setText(data.getString("prenom"));
            staff_phone_tf.setText(data.getString("phone"));
            data.close();
        }
        refreshdata();
    }
    public void setDataEE(String id_in , Stage father_in , EditeEleveController  home_con) throws SQLException, ClassNotFoundException {
        this.id = id_in;
        this.home_controller2 = home_con;
        staff_id_tf.setText(id_in);
        fillcombos();
        this.father = father_in;
        ResultSet data = sql.displayStaff(Integer.valueOf(id_in));
        while (data.next()){
            for (TyCat item : Type_combo.getItems()) {
                if (item.getValue().equals(String.valueOf( data.getInt("role")))) {
                    Type_combo.setValue(item);
                    break;
                }
            }
            staff_cin_tf.setText(data.getString("cin"));
            staff_nom_tf.setText(data.getString("nom"));
            staff_prenom_tf.setText(data.getString("prenom"));
            staff_phone_tf.setText(data.getString("phone"));
            data.close();
        }
        refreshdata();

    }
    public void     setData(String id_in , Stage father_in , Staff_page_controller home_con) throws SQLException, ClassNotFoundException {
        this.id = id_in;
        this.home_controller = home_con;
        staff_id_tf.setText(id_in);
        fillcombos();
        this.father = father_in;
        ResultSet data = sql.displayStaff(Integer.valueOf(id_in));
        while (data.next()){
            for (TyCat item : Type_combo.getItems()) {
                if (item.getValue().equals(String.valueOf( data.getInt("role")))) {
                    Type_combo.setValue(item);
                    break;
                }
            }
            staff_cin_tf.setText(data.getString("cin"));
            staff_nom_tf.setText(data.getString("nom"));
            staff_prenom_tf.setText(data.getString("prenom"));
            staff_phone_tf.setText(data.getString("phone"));
            data.close();
        }
//        this.Image_view.setImage(img);
        refreshdata();
    }
    public void refreshdata() throws SQLException, ClassNotFoundException {
        Label label1 = new Label( "Etudiant");
        label1.setMaxWidth(MAX_VALUE);
        label1.setAlignment(Pos.CENTER);
        Label label2 = new Label( "Document");
        label2.setMaxWidth(MAX_VALUE);
        label2.setAlignment(Pos.CENTER);
        Label label3 = new Label( "Date Emprunt");
        label3.setMaxWidth(MAX_VALUE);
        label3.setAlignment(Pos.CENTER);
        Label label4 = new Label( "Date Retour");
        label4.setMaxWidth(MAX_VALUE);
        label4.setAlignment(Pos.CENTER);

        dataGridListup.add(label1,0,1);
        dataGridListup.add(label3,1,1);
        dataGridListup.add(label4,2,1);
        dataGridListup.add(label2,3,1);
        int i =2;
        ResultSet rs =  sql.getBookStaff(this.id);
        while (rs.next()){
            Button etudiant = new Button( rs.getString("cne"));
            etudiant.setId(rs.getString("etud_id"));
            etudiant.setCursor(Cursor.HAND);
            etudiant.setStyle("  -fx-text-fill: rgb(240, 240, 240);" +
                    "  -fx-background-color: rgb(51, 51, 51);" +
                    "  -fx-border-color: rgb(12, 12, 12);" +
                    "  -fx-border-width: 0px;" +
                    "  -fx-border-radius: 1px;" +
                    "  -fx-font-size: 11px;" +
                    "  -fx-font-weight: bold;");
            etudiant.setMaxWidth(MAX_VALUE);
            Button staff_btn = new Button( rs.getString("doc_libelle"));
            staff_btn.setMaxWidth(MAX_VALUE);
            staff_btn.setCursor(Cursor.HAND);
            staff_btn.setId(rs.getString("doc_id"));
            staff_btn.setStyle("  -fx-text-fill: rgb(240, 240, 240);" +
                    "  -fx-background-color: rgb(51, 51, 51);" +
                    "  -fx-border-color: rgb(12, 12, 12);" +
                    "  -fx-border-width: 0px;" +
                    "  -fx-border-radius: 1px;" +
                    "  -fx-font-size: 11px;" +
                    "  -fx-font-weight: bold;");
//                    "  -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.274), 10, 0, 0, 0);" +
            etudiant.setOnAction(ec->{
//                System.out.println(etudiant.getId() );
                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader( Objects.requireNonNull(getClass().getResource("../resources/fxml/Editeetudiante.fxml")));
                Parent root = null;
                try {
                    root = (Parent) fxmlLoader.load();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // Get the controller for the scene
                EditeEleveController controller = fxmlLoader.getController();

                // Pass data to the controller
                Stage stage = new Stage();
                stage.setTitle("Edite Document");
                try {
                    controller.setDataES(etudiant.getId() , stage  ,this);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                // Create the scene and the stage
                Scene scene = new Scene(root);
                scene.setOnMousePressed(e-> {
                    xOffset = e.getSceneX();
                    yOffset = e.getSceneY();

                });
                scene.setOnMouseDragged(e->{
                    stage.setX(e.getScreenX() - xOffset);
                    stage.setY(e.getScreenY() - yOffset);

                });
                stage.setScene(scene);

                // Set the stage as modal
                stage.initModality(Modality.APPLICATION_MODAL);

                // Set the owner of the modal stage
                stage.initOwner(father);
                stage.initStyle(StageStyle.UNDECORATED);
                // Show the stage
                stage.show();

            });
            if(Main.isAdmin) {
                staff_btn.setOnAction(em -> {
                    // Load the FXML file
                    FXMLLoader fxmlLoader = new FXMLLoader( Objects.requireNonNull(getClass().getResource("../resources/fxml/EditeBook.fxml")));
                    Parent root = null;
                    try {
                        root = (Parent) fxmlLoader.load();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    // Get the controller for the scene
                    EditeBookController controller = fxmlLoader.getController();

                    // Pass data to the controller
                    Stage stage = new Stage();
                    stage.setTitle("Edite Document");
                    try {
                        controller.setDataES(this.id , stage  ,this);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    // Create the scene and the stage
                    Scene scene = new Scene(root);
                    scene.setOnMousePressed(e-> {
                        xOffset = e.getSceneX();
                        yOffset = e.getSceneY();

                    });
                    scene.setOnMouseDragged(e->{
                        stage.setX(e.getScreenX() - xOffset);
                        stage.setY(e.getScreenY() - yOffset);

                    });
                    stage.setScene(scene);

                    // Set the stage as modal
                    stage.initModality(Modality.APPLICATION_MODAL);

                    // Set the owner of the modal stage
                    stage.initOwner(father);
                    stage.initStyle(StageStyle.UNDECORATED);
                    // Show the stage
                    stage.show();
                });
            }
            Label date1 = new Label( rs.getString("emp_d"));
            date1.setMaxWidth(MAX_VALUE);
            date1.setAlignment(Pos.CENTER);

            Label date2 = new Label( rs.getString("return_d"));
            date2.setMaxWidth(MAX_VALUE);
            date2.setAlignment(Pos.CENTER);
            dataGridListup.add(etudiant,0,i);
            dataGridListup.add(date1,1,i);
            dataGridListup.add(date2,2,i);
            dataGridListup.add(staff_btn,3,i);

            i++;
        }
        ResultSet rs2 = sql.getLog(this.id);
        int v = 1;
        while (rs2.next()){
            Text time = new Text(rs2.getString("date"));
            Text info = new Text(rs2.getString("info"));
            dataGridListdown.add(time,0,v);
            dataGridListdown.add(info,1,v);
            v++;
        }
        rs2.close();
    }
    public void fillcombos() throws SQLException, ClassNotFoundException {
        Type_combo.getItems().clear();
        ObservableList<TyCat> ty_ls = Type_combo.getItems();
        // Iterate through the result set and create objects for each record
        ty_ls.add(new TyCat("1","Admin"));
        ty_ls.add(new TyCat("2","Staff"));
    }

    @FXML
    public void deleteHandler(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        int num = sql.deleteStaff(this.id);
        if(num>0){
            if(home_controller != null){
                home_controller.search_handler_func();
            }else if(home_controller1 != null){
                home_controller1.refreshdata();
            }else if(home_controller2 != null){
                home_controller2.refreshdata();
            }
            father.close();
        }
    }
    @FXML
    public void saveHandler(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        int num = sql.updateStaff(this.id,staff_nom_tf.getText().trim() ,staff_prenom_tf.getText().trim() ,  staff_cin_tf.getText().trim() , staff_phone_tf.getText().trim() , staff_pass_tf.getText().trim(),Type_combo.getValue().getValue() );
        if(num>0){
            if(home_controller != null){
                home_controller.search_handler_func();
            }else if(home_controller1 != null){
                home_controller1.refreshdata();
            }else if(home_controller2 != null){
                home_controller2.refreshdata();
            }
            father.close();
        }

    }

}
