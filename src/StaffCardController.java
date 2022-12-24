package src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class StaffCardController {

    @FXML
    private Button edite_button;

    @FXML
    private Label staff_cin;

    @FXML
    private ImageView card_img;

    @FXML
    private Label staff_nom;

    @FXML
    private Label staff_pre;


    @FXML
    void handleEditeClick(ActionEvent event) {

//                System.out.println(etudiant.getId() );
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader( Objects.requireNonNull(getClass().getResource("../resources/fxml/EditeStaff.fxml")));
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
        stage.setTitle("Edite Etudiant");
        try {
            controller.setData(this.id,stage,this.home_controller);
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
    }

    private Stage father ;
    private double xOffset = 0;
    private double yOffset = 0;
    Staff_page_controller home_controller;
    private String id;
    public void setData(String id , String cin , String name , String prename , Stage stg,Staff_page_controller home,boolean adm){
        this.father = stg;
        Image src_image;
        if(adm){
            src_image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("../resources/img/admin.jpg")));
        }else {
            src_image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("../resources/img/book2.png")));
        }
        this.card_img.setImage(src_image); ;

        this.home_controller = home;
        this.id = id;
        this.staff_cin.setText(cin);
        this.staff_nom.setText(name);
        this.staff_pre .setText(prename);
    }

}
