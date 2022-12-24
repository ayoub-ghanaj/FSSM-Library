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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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

public class EditeBookController implements Initializable {

    @FXML
    private TextField label_textField;

    @FXML
    private Button close_btn;

    @FXML
    private ComboBox<TyCat> Type_combo;

    @FXML
    private ComboBox<TyCat> Cate_combo;

    @FXML
    private ImageView Image_view;

    @FXML
    private TextField id_textField;

    @FXML
    private Button Save_btn;
    @FXML
    private GridPane dataGridList;
    private String id;

    private Stage father;
    private double xOffset = 0;
    private double yOffset = 0;

   final private SQLiteTools sql = new SQLiteTools();

    private  File File_image =null ;
    private  Image image = null;
    private  Books_page_controller home_controller;
    private  EditeEleveController home_controller1;
    private  EditeStaffController home_controller2;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.Image_view.setOnMouseClicked(e->{
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Image");
                fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
                );
                 File_image = fileChooser.showOpenDialog(father);
                if (File_image != null) {
                     image = new Image(File_image.toURI().toString());
                    Image_view.setImage(image);
                }
            });
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
    public void setDataES(String id_in , Stage father_in , EditeStaffController  home_con) throws SQLException, ClassNotFoundException {
        this.id = id_in;
        this.home_controller2 = home_con;
        id_textField.setText(id_in);
        fillcombos();
        this.father = father_in;
        ResultSet data = sql.displayDoc(Integer.valueOf(id_in));
        while (data.next()){
            Image src_image = new Image(data.getString("image"));
            this.Image_view.setImage(src_image);
            for (TyCat item : Type_combo.getItems()) {
                if (item.getValue().equals(String.valueOf( data.getInt("type_id")))) {
                    Type_combo.setValue(item);
                    break;
                }
            }
            for (TyCat item : Cate_combo.getItems()) {
                if (item.getValue().equals(String.valueOf( data.getInt("cat_id")))) {
                    Cate_combo.setValue(item);
                    break;
                }
            }
            label_textField.setText(data.getString("doc_libelle"));
            data.close();
        }
        refreshdata();
    }
    public void setDataEE(String id_in , Stage father_in , EditeEleveController  home_con) throws SQLException, ClassNotFoundException {
        this.id = id_in;
        this.home_controller1 = home_con;
        id_textField.setText(id_in);
        fillcombos();
        this.father = father_in;
        ResultSet data = sql.displayDoc(Integer.valueOf(id_in));
        while (data.next()){
            Image src_image = new Image(data.getString("image"));
            this.Image_view.setImage(src_image);
            for (TyCat item : Type_combo.getItems()) {
                if (item.getValue().equals(String.valueOf( data.getInt("type_id")))) {
                    Type_combo.setValue(item);
                    break;
                }
            }
            for (TyCat item : Cate_combo.getItems()) {
                if (item.getValue().equals(String.valueOf( data.getInt("cat_id")))) {
                    Cate_combo.setValue(item);
                    break;
                }
            }
            label_textField.setText(data.getString("doc_libelle"));
            data.close();
        }
        refreshdata();

    }
    public void setData(String id_in , Stage father_in , Books_page_controller  home_con) throws SQLException, ClassNotFoundException {
        this.id = id_in;
        this.home_controller = home_con;
        id_textField.setText(id_in);
        fillcombos();
        this.father = father_in;
        ResultSet data = sql.displayDoc(Integer.valueOf(id_in));
        while (data.next()){
            Image src_image = new Image(data.getString("image"));
            this.Image_view.setImage(src_image);
            for (TyCat item : Type_combo.getItems()) {
                if (item.getValue().equals(String.valueOf( data.getInt("type_id")))) {
                    Type_combo.setValue(item);
                    break;
                }
            }
            for (TyCat item : Cate_combo.getItems()) {
                if (item.getValue().equals(String.valueOf( data.getInt("cat_id")))) {
                    Cate_combo.setValue(item);
                    break;
                }
            }
            label_textField.setText(data.getString("doc_libelle"));
            data.close();
        }
//        this.Image_view.setImage(img);
        refreshdata();
    }
    public void refreshdata() throws SQLException, ClassNotFoundException {
        Label label1 = new Label( "Etudiant");
        label1.setMaxWidth(MAX_VALUE);
        label1.setAlignment(Pos.CENTER);
        Label label2 = new Label( "Staff");
        label2.setMaxWidth(MAX_VALUE);
        label2.setAlignment(Pos.CENTER);
        Label label3 = new Label( "Date Emprunt");
        label3.setMaxWidth(MAX_VALUE);
        label3.setAlignment(Pos.CENTER);
        Label label4 = new Label( "Date Retour");
        label4.setMaxWidth(MAX_VALUE);
        label4.setAlignment(Pos.CENTER);

        dataGridList.add(label1,0,1);
        dataGridList.add(label3,1,1);
        dataGridList.add(label4,2,1);
        dataGridList.add(label2,3,1);
        int i =2;
        ResultSet rs =  sql.getBookEtudiants(this.id);
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
            Button staff_btn = new Button( rs.getString("cin"));
            staff_btn.setMaxWidth(MAX_VALUE);
            staff_btn.setCursor(Cursor.HAND);
            staff_btn.setId(rs.getString("staff_id"));
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
                stage.setTitle("Edite Etudiant");
                try {
                    controller.setDataEB(etudiant.getId(),stage,this);
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
                    FXMLLoader fxmlLoader = new FXMLLoader( Objects.requireNonNull(getClass().getResource("../resources/fxml/Editestaff.fxml")));
                    Parent root = null;
                    try {
                        root = (Parent) fxmlLoader.load();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    // Get the controller for the scene
                    EditeStaffController controller = fxmlLoader.getController();

                    // Pass data to the controller
                    Stage stage = new Stage();
                    stage.setTitle("Edite Document");
                    try {
                        controller.setDataEB(this.id , stage  ,this);
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
            Label staff_la = new Label( rs.getString("cin"));
            staff_la.setMaxWidth(MAX_VALUE);

            Label date1 = new Label( rs.getString("emp_d"));
            date1.setMaxWidth(MAX_VALUE);
            date1.setAlignment(Pos.CENTER);

            Label date2 = new Label( rs.getString("return_d"));
            date2.setMaxWidth(MAX_VALUE);
            date2.setAlignment(Pos.CENTER);

            if(Main.isAdmin){
                dataGridList.add(etudiant,0,i);
                dataGridList.add(date1,1,i);
                dataGridList.add(date2,2,i);
                dataGridList.add(staff_btn,3,i);
            }else {
                dataGridList.add(etudiant,0,i);
                dataGridList.add(date1,1,i);
                dataGridList.add(date2,2,i);
                dataGridList.add(staff_la,3,i);
            }
            i++;
        }
    }
    public void fillcombos() throws SQLException, ClassNotFoundException {
        Type_combo.getItems().clear();
        Cate_combo.getItems().clear();
        ResultSet types_list = sql.displayTypes();
        ResultSet catego_list = sql.displayCategories();
        ObservableList<TyCat> ty_ls = Type_combo.getItems();
        ObservableList<TyCat> ca_ls = Cate_combo.getItems();
        // Iterate through the result set and create objects for each record
        try {
            while (types_list.next()) {
                String id_in = String.valueOf(types_list.getInt("type_id"));
                String name = types_list.getString("ty_libelle");
                ty_ls.add(new TyCat(id_in,name));
            }
            while (catego_list.next()) {
                String id_in = String.valueOf(catego_list.getInt("cat_id"));
                String name = catego_list.getString("cat_libelle");
                ca_ls.add(new TyCat(id_in,name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteHandler(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        int num = sql.deleteDoc(this.id);
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
        if(File_image != null && image != null){
            String path =  new File("../database/test1.db").getAbsolutePath();
            path = path.substring(0, path.length() - 20);
            String new_name = "IMG-"+String.valueOf(Math.random()).substring(2) +"."+getFileExtension(File_image).toLowerCase();
            try (FileInputStream in = new FileInputStream(File_image.getAbsolutePath());
                 FileOutputStream out = new FileOutputStream(path+"resources\\img\\"+new_name)) {

                // Use a buffer to copy the file
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                out.close();
                in.close();
                int num = sql.updateDoc(this.id,label_textField.getText().trim() ,Type_combo.getValue().getValue() , Cate_combo.getValue().getValue(),path+"resources\\img\\"+new_name);
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
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else {
            int num = sql.updateDoc_keep_image(this.id,label_textField.getText().trim() ,Type_combo.getValue().getValue() , Cate_combo.getValue().getValue());
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
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

}
