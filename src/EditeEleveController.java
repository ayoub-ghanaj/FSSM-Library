package src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class EditeEleveController {

    @FXML
    private GridPane dataGridList;

    @FXML
    private Button Save_btn1;

    @FXML
    private Button close_btn;

    @FXML
    private TextField phone_textField;

    @FXML
    private ImageView Image_view;

    @FXML
    private TextField email_textField;

    @FXML
    private TextField prenom_textField1;

    @FXML
    private TextField cne_field;

    @FXML
    private TextField nom_textField;

    @FXML
    private TextField id_textField;

    @FXML
    private Button Save_btn;




    @FXML
    void saveHandler(ActionEvent event) {
//        int num = sql.deleteDoc(this.id);
//        if(num>0){
//            home_controller.search_handler_func();
//            father.close();
//        }
    }

    @FXML
    void deleteHandler(ActionEvent event) {

    }

}
