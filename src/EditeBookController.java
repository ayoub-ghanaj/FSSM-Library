package src;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EditeBookController implements Initializable {

    @FXML
    private TextField label_textField;

    @FXML
    private Button close_btn;

    @FXML
    private ComboBox<?> Type_combo;

    @FXML
    private ComboBox<?> Cate_combo;

    @FXML
    private ImageView Image_view;

    @FXML
    private TextField id_textField;

    @FXML
    private Button Save_btn;

    private String id;

    private Stage father;

   final private SQLiteTools sql = new SQLiteTools();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.Image_view.setOnMouseClicked(e->{
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Image");
                fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
                );
                File selectedFile = fileChooser.showOpenDialog(father);
                if (selectedFile != null) {
                    Image image = new Image(selectedFile.toURI().toString());
                    // do something with the image
                }
            });
            this.close_btn.setOnAction(e->{
                father.close();
            });

        }catch (Exception e){

        }
    }

    public void setData(String id_in , Stage father_in,Image img){
        this.id = id_in;
        id_textField.setText(id_in);

        this.father = father_in;
        this.Image_view.setImage(img);
    }
}
