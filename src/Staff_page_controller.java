package src;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Staff_page_controller {

    @FXML
    private ComboBox<TyCat> types;

    @FXML
    private Text fullname_labelle;

    @FXML
    private Button exit_btn;

    @FXML
    private HBox staff_btn;

    @FXML
    private GridPane booksDisplay;

    @FXML
    private Button minimixe_btn;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_text;
    private Stage father ;
    private int staff_id ;
    private String  staff_name ;
    public void setStaffId(int staffid){
        this.staff_id = staffid;
    }
    public void setStaffName( String staff_nam){
        this.staff_name =  staff_nam;
    }
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean staff_is_admin ;
    public void setFather(Stage stg){
        this.father = stg;
    }

    final private SQLiteTools sql = new SQLiteTools();




    @FXML
    void search_handler_rank(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        search_handler_func();
    }


    @FXML
    void switch_students_txt(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../resources/fxml/etudiants.fxml")));
        Parent books_fxml  = loader.load();
        Etudiants_page_controller controller = loader.getController();
        controller.setFather(father);
        controller.setStaffId(this.staff_id);
        controller.setStaffName(this.staff_name);
        controller.setData(this.staff_id,Main.isAdmin);
        Scene scene = new Scene(books_fxml);
        scene.setOnMousePressed(e-> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();

        });
        scene.setOnMouseDragged(e->{
            father.setX(e.getScreenX() - xOffset);
            father.setY(e.getScreenY() - yOffset);

        });
        father.setScene(scene);
    }



    @FXML
    void switch_emprunts_txt(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../resources/fxml/emprunts.fxml")));
        Parent books_fxml  = loader.load();
        Emprunts_page_controller controller = loader.getController();
        controller.setFather(father);
        controller.setStaffId(this.staff_id);
        controller.setStaffName(this.staff_name);
        controller.setData(this.staff_id,Main.isAdmin);
        Scene scene = new Scene(books_fxml);
        scene.setOnMousePressed(e-> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();

        });
        scene.setOnMouseDragged(e->{
            father.setX(e.getScreenX() - xOffset);
            father.setY(e.getScreenY() - yOffset);

        });
        father.setScene(scene);
    }


    @FXML
    void switch_dashboard_txt(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../resources/fxml/dashboard.fxml")));
        Parent books_fxml  = loader.load();
        Dashboard controller = loader.getController();
        controller.setFather(father);
        controller.setData(this.staff_id,true );
        Scene scene = new Scene(books_fxml);
        scene.setOnMousePressed(e-> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();

        });
        scene.setOnMouseDragged(e->{
            father.setX(e.getScreenX() - xOffset);
            father.setY(e.getScreenY() - yOffset);

        });
        father.setScene(scene);
    }
    @FXML
    void switch_docs_txt(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../resources/fxml/books.fxml")));
        Parent books_fxml  = loader.load();
        Books_page_controller controller = loader.getController();
        controller.setFather(father);
        controller.setStaffId(this.staff_id);
        controller.setStaffName(this.staff_name);
        controller.setData(this.staff_id,Main.isAdmin);
        Scene scene = new Scene(books_fxml);
        scene.setOnMousePressed(e-> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();

        });
        scene.setOnMouseDragged(e->{
            father.setX(e.getScreenX() - xOffset);
            father.setY(e.getScreenY() - yOffset);

        });
        father.setScene(scene);
    }



    @FXML
    void logout_controller(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        sql.addlog(this.staff_id,"Logged out");
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../resources/fxml/login.fxml")));
        Parent books_fxml  = loader.load();
        Login_controller controller = loader.getController();
        controller.setFather(father);
        Main.staff_id = 0;
        Main.staff_name = "";
        Main.isAdmin = false;
        Scene scene = new Scene(books_fxml);
        scene.setOnMousePressed(e-> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();

        });
        scene.setOnMouseDragged(e->{
            father.setX(e.getScreenX() - xOffset);
            father.setY(e.getScreenY() - yOffset);

        });
        father.setScene(scene);
    }

    @FXML
    void search_handler(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        search_handler_func();
    }

    @FXML
    void search_btn_handler(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        search_handler_func();
    }



    @FXML
    void Add_Staff_handler(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader( Objects.requireNonNull(getClass().getResource("../resources/fxml/addstaff.fxml")));
            Parent root = (Parent) fxmlLoader.load();

            // Get the controller for the scene
            AddStaffController controller = fxmlLoader.getController();

            // Pass data to the controller
            Stage stage = new Stage();
            stage.setTitle("Edite Document");
            controller.setData(stage  ,this);
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }



    @FXML
    void search_handler_refresh(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        search_handler_func();
    }

    @FXML
    public void handleExitClick(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void handleMiniClick(ActionEvent event) {
        father.setIconified(true);
    }


    public void setData(int staf_id , boolean  admin) throws SQLException, IOException, ClassNotFoundException {
        this.staff_id =staf_id ;
        this.staff_is_admin = admin;
        staff_btn.setVisible(admin);
//        father.setTitle("documents list");
        fullname_labelle.setText(Main.staff_name);
        search_handler_func();
        ObservableList<TyCat> ty_ls = types.getItems();
        ty_ls.add(new TyCat("1","admin"));
        ty_ls.add(new TyCat("2","staff"));


    }

    public void search_handler_func() throws SQLException, ClassNotFoundException, IOException {
        String like = search_text.getText();
        String typca = (types.getValue()!= null) ? types.getValue().getValue():  "0";
        ResultSet rs = sql.getStaff(like,typca );
        int col = 0;
        int row = 1;
        this.booksDisplay.getChildren().clear();
        while (rs.next()){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Objects.requireNonNull(getClass().getResource("../resources/fxml/StaffCard.fxml")));
            HBox bookCard = fxmlLoader.load();
            StaffCardController cardController = fxmlLoader.getController();
            boolean adm = rs.getInt("role") == 1;
            cardController.setData(rs.getString("staff_id"),rs.getString("cin"),rs.getString("nom"),rs.getString("prenom"),this.father,this,adm);
            if(col == 3){
                col = 0;
                ++row;
            }
            booksDisplay.add(bookCard,col++,row);
        }
        rs.close();
    }
}

