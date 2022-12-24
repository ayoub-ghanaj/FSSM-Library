package src;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Login_controller implements Initializable {

    @FXML
    private PasswordField pass_field;

    @FXML
    private ImageView Image_show;

    @FXML
    private RadioButton rd_img_1;

    @FXML
    private TextField tb_login;

    @FXML
    private RadioButton rd_img_2;

    @FXML
    private RadioButton rd_img_3;

    @FXML
    private Label errorLabel;
    @FXML
    private Button login_btn;

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage father ;
    public void setFather(Stage stg){
        this.father = stg;
    }
    private  int i = 0 ;
    private final SQLiteTools sql = new SQLiteTools();
    private ToggleGroup group = new ToggleGroup();
    private String[] images = {"1.png","2.png","3.png"};
    private boolean flip = true;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rd_img_1.setToggleGroup(group);
        rd_img_2.setToggleGroup(group);
        rd_img_3.setToggleGroup(group);

        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("../resources/img/1.png")));
        // Set the initial image
        Image_show.setImage(image1);

        // Schedule a task to run every 3 seconds
        executor.scheduleAtFixedRate(() -> {
            if(flip){
                if(i == 0){
                    i=1;
                    rd_img_1.setSelected(true);
                    switchImages("2.png");
                }else if ( i == 1){
                    i=2;
                    rd_img_2.setSelected(true);
                    switchImages("3.png");
                }else {
                    i=0;
                    rd_img_3.setSelected(true);
                    switchImages("1.png");
                }
            }
            flip =true;
        }, 0, 8, TimeUnit.SECONDS);

    }

    private void switchImages(String image2_path){
        Image image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("../resources/img/"+image2_path)));


// Create a translate transition to animate the image sliding off the screen
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1),Image_show);
        translateTransition.setFromX(0);
        translateTransition.setToX(Image_show.getBoundsInParent().getWidth());

// Set the action to occur when the transition finishes
        translateTransition.setOnFinished(event -> {
            // Change the image and reset the translation
            Image_show.setTranslateX(0);
            Image_show.setImage(image2);
        });

// Start the transition
        translateTransition.play();
    }
    @FXML
    void rd_img_1_handler(ActionEvent event) throws InterruptedException {
        if(rd_img_1.isSelected()){
            i=0;
            switchImages("1.png");
        }else if(rd_img_2.isSelected()){
            i=1;
            switchImages("2.png");
        }else {
            i=2;
            switchImages("3.png");
        }
        flip = false;
    }

    @FXML
    void rd_img_2_handler(ActionEvent event) throws InterruptedException {
        if(rd_img_1.isSelected()){
            i=0;
            switchImages("1.png");
        }else if(rd_img_2.isSelected()){
            i=1;
            switchImages("2.png");
        }else {
            i=2;
            switchImages("3.png");
        }
        flip = false;

    }

    @FXML
    void rd_img_3_handler(ActionEvent event) throws InterruptedException {
        if(rd_img_1.isSelected()){
            i=0;
            switchImages("1.png");
        }else if(rd_img_2.isSelected()){
            i=1;
            switchImages("2.png");
        }else {
            i=2;
            switchImages("3.png");
        }
        flip = false;
    }
    @FXML
    void login_handler(ActionEvent event) {
        errorLabel.setVisible(false);
        String username = tb_login.getText().trim();
        String password = pass_field.getText().trim();
        if(username.length()>0 && password.length()>0 ){
            try {
                int[] arr = sql.login(username,password);
                if(arr[1]==0){
                    errorLabel.setVisible(true);
                }else if(arr[0]==1){
                    sql.addlog(arr[1],"Logged in");
                    Main.isAdmin = true;
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../resources/fxml/dashboard.fxml")));
                    Parent books_fxml  = loader.load();
                    Dashboard controller = loader.getController();
                    controller.setFather(this.father);
                    Main.staff_id = arr[0];
                    controller.setData(arr[0],true );
                    Scene scene = new Scene(books_fxml);
                    scene.setOnMousePressed(e-> {
                        xOffset = e.getSceneX();
                        yOffset = e.getSceneY();

                    });
                    scene.setOnMouseDragged(e->{
                        father.setX(e.getScreenX() - xOffset);
                        father.setY(e.getScreenY() - yOffset);

                    });
                    executor.close();
                    father.setScene(scene);
                }else{
                    sql.addlog(arr[1],"Logged in");
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../resources/fxml/dashboard.fxml")));
                    Parent books_fxml  = loader.load();
                    Dashboard controller = loader.getController();
                    controller.setFather(father);
                    Main.staff_id = arr[0];
                    Main.isAdmin = false;
                    controller.setData(arr[0],false );
                    Scene scene = new Scene(books_fxml);
                    scene.setOnMousePressed(e-> {
                        xOffset = e.getSceneX();
                        yOffset = e.getSceneY();

                    });
                    scene.setOnMouseDragged(e->{
                        father.setX(e.getScreenX() - xOffset);
                        father.setY(e.getScreenY() - yOffset);

                    });
                    executor.close();
                    father.setScene(scene);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @FXML
    void mini_handler(ActionEvent event) {
        father.setIconified(true);
    }

    @FXML
    void exit_handler(ActionEvent event) {
        executor.close();
        Platform.exit();
    }

}
