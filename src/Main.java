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

    public static int  staff_id;
    public static String  staff_name ;
    public void setStaffId(int staffid, String staff_nam){
        staff_id = staffid;
        staff_name =  staff_nam;
    }
    public static boolean  isAdmin = false;

    Scene login ;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setResizable(false);

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../resources/fxml/login.fxml")));
        Parent login_fxml  = loader.load();
        Login_controller controller = loader.getController();
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


        // Delete Default Boreder
        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(login);
        window.setTitle("FSLib");
        window.show();

    }

}
