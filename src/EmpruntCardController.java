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

public class EmpruntCardController {

    @FXML
    private Label book_type;

    @FXML
    private ImageView card_img;

    @FXML
    private Label book_name;

    @FXML
    private Label book_cate;

    @FXML
    private Button emprunt_button;

    private final SQLiteTools sql = new SQLiteTools();

    @FXML
    void handleEmpruntClick(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        sql.returnEmprunt(Integer.parseInt(this.id));
        home_controller.search_handler_func();
    }


    private Stage father ;
    private double xOffset = 0;
    private double yOffset = 0;
    Emprunts_page_controller home_controller;
    private String id;
    public void setData(String id , String cne , String name , String date , Stage stg,Emprunts_page_controller home,String image){
        this.father = stg;
        Image src_image = new Image(image);
        this.card_img.setImage(src_image); ;

        this.home_controller = home;
        this.id = id;
        this.book_name.setText(name);
        this.book_cate.setText(cne);
        this.book_type .setText(date);
    }


}
