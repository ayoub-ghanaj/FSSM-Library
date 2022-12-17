package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application  {
    private double xOffset = 0;
    private double yOffset = 0;
    Stage window ;

    public String  staff_id;
    Scene login ;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

//        SQLiteTools sql = new SQLiteTools();
        window = stage;
        window.setResizable(false);
        //create login layout
//        login = new Scene(new Login_Layout(window,sql),1024,666);
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../resources/fxml/books.fxml")));
        Parent login_fxml  = loader.load();
        Books_page_controller controller = loader.getController();
        controller.setFather(window);
        login = new Scene(login_fxml);
        //resizable/movable
        login.setOnMousePressed(e-> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();

        });
        login.setOnMouseDragged(e->{
            window.setX(e.getScreenX() - xOffset);
            window.setY(e.getScreenY() - yOffset);

        });
        //set css
//        login.getStylesheets().add(Objects.requireNonNull(getClass().getResource("../resources/css/books.css")).toExternalForm());
//        W 223  H 139

//        window.setMaxWidth(470);
//        window.setMaxHeight(240);
//        window.setMinWidth(420);
//        window.setMinHeight(220);

        // Delete Default Boreder
        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(login);
        window.show();
        window.setTitle("Login");
        window.show();

    }


}
