package src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class EtudiantCardController implements Initializable {

    @FXML
    private Label etu_prenom;

    @FXML
    private Button edite_button;

    @FXML
    private ImageView card_img;

    @FXML
    private Label etu_nom;

    @FXML
    private Label etu_cne;

    @FXML
    private Button emprunt_button;

    @FXML
    void handleEditeClick(ActionEvent event) {

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

    @FXML
    void handleEmpruntClick(ActionEvent event) throws SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader( Objects.requireNonNull(getClass().getResource("../resources/fxml/Emprunt.fxml")));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // Get the controller for the scene
        Emprunt controller = fxmlLoader.getController();

        // Pass data to the controller
        Stage stage = new Stage();
        stage.setTitle(" Emprunt");
        controller.setDataEtu(Integer.parseInt(this.id),stage,home_controller.getStaffId(),home_controller);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    private Stage father ;
    private double xOffset = 0;
    private double yOffset = 0;
    Etudiants_page_controller home_controller;
    private String id;
    public void setData(String id , String cne , String name , String prename , Stage stg,Etudiants_page_controller home,String image){
        this.father = stg;
        Image src_image = new Image(image);
        this.card_img.setImage(src_image); ;

        this.home_controller = home;
        this.id = id;
        this.etu_nom.setText(name);
        this.etu_cne.setText(cne);
        this.etu_prenom .setText(prename);
    }


}
