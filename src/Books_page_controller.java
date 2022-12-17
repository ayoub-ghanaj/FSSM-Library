package src;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Books_page_controller implements Initializable {

    @FXML
    private Button ADD;

    @FXML
    private ComboBox<?> types;

    @FXML
    private Button refresh;

    @FXML
    private GridPane booksDisplay;

    @FXML
    private ComboBox<?> categories;

    @FXML
    private Button Home;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_text;

    private String id;

    private Stage father ;
    public void setFather(Stage stg){
        this.father = stg;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int row =1;
        int col =0;

        try {
            this.booksDisplay.getChildren().clear();
            for(int i = 0 ; i < 110   ; i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Objects.requireNonNull(getClass().getResource("../resources/fxml/BookCard.fxml")));
                HBox bookCard = fxmlLoader.load();
                BookCardController cardController = fxmlLoader.getController();
                cardController.setData(String.valueOf(i),"name"+i ,"cate"+col,"type"+row, "../resources/img/book_books_bookshelf_213921.jpg",this.father);
                if(col == 3){
                    col = 0;
                   ++row;
                }
                booksDisplay.add(bookCard,col++,row);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }


    @FXML
    public void handleExitClick(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void handleMiniClick(ActionEvent event) {
        father.setIconified(true);
    }

}
