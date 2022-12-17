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
import java.util.Objects;

public class BookCardController {

    private Stage father ;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Label book_type;

    @FXML
    private ImageView card_img;

    @FXML
    private Label book_name;


    @FXML
    private Button edite_button;
    @FXML
    private Button emprunt_button;

    @FXML
    private Label book_cate;

    @FXML

    private String id;
//    public void setFather(Stage stg){
//        this.father = stg;
//    }
    public void setData(String id , String name , String cate , String type , String image,Stage stg){
        this.father = stg;
        Image src_image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(image)));
        this.card_img.setImage(src_image);

        this.card_img.setScaleY(((138*4.8)/src_image.getHeight()));
        this.card_img.setScaleX(((92*4.8)/src_image.getWidth()));

        this.id = id;
        this.book_name.setText(name);
        this.book_cate.setText(String.valueOf((900/src_image.getHeight())));
        this.book_type.setText(String.valueOf((src_image.getWidth())));
    }


    @FXML
    public void handleEditeClick(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../resources/fxml/EditeBook.fxml")));
            Parent root = (Parent) fxmlLoader.load();

            // Get the controller for the scene
            EditeBookController controller = fxmlLoader.getController();

            // Pass data to the controller
            Stage stage = new Stage();
            stage.setTitle("Edite Document");
            controller.setData(this.id , stage,this.card_img.getImage());

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleEmpruntClick(ActionEvent event) {
    }

}

