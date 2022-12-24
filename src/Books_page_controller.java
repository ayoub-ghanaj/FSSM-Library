package src;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class    Books_page_controller implements Initializable {

    @FXML
    private Button ADD;

    @FXML
    private Label Lib2home2;

    @FXML
    private Label Lib2home;

    @FXML
    private ComboBox<TyCat> types;

    @FXML
    private Button refresh;

    @FXML
    private GridPane booksDisplay;

    @FXML
    private ComboBox<TyCat> categories;

    @FXML
    private Button Home;

    @FXML
    private Button search_btn;


    @FXML
    private Text fullname_labelle;

    @FXML
    private HBox staff_btn;

    @FXML
    private TextField search_text;

    private String id;

    @FXML
    private RadioButton invalide;

    private ToggleGroup group = new ToggleGroup();
    @FXML
    private RadioButton valide;
    private Stage father ;
    private int staff_id ;
    private String  staff_name ;
    public void setStaffId(int staffid){
        this.staff_id = staffid;
    }
    public int getStaffId(){
        return this.staff_id ;
    }
    public void setStaffName( String staff_nam){
        this.staff_name =  staff_nam;
    }
    private double xOffset = 0;
    private double yOffset = 0;
    private double xOffsetadd = 0;
    private double yOffsetadd = 0;
    private boolean staff_is_admin ;
    public void setFather(Stage stg){
        this.father = stg;
    }

    final private SQLiteTools sql = new SQLiteTools();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int row =1;
        int col =0;
        staff_btn.setVisible(Main.isAdmin);
        try {
            valide.setToggleGroup(group);
            invalide.setToggleGroup(group);
            search_handler_func();


        }catch (IOException ex){
            ex.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setData(int staf_id , boolean  admin){
        this.staff_id =staf_id ;
        this.staff_is_admin = admin;
//        father.setTitle("documents list");
        fullname_labelle.setText(Main.staff_name);

    }
    @FXML
    public void Add_book_handler(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader( Objects.requireNonNull(getClass().getResource("../resources/fxml/addbook.fxml")));
            Parent root = (Parent) fxmlLoader.load();

            // Get the controller for the scene
            AddBookController controller = fxmlLoader.getController();

            // Pass data to the controller
            Stage stage = new Stage();
            stage.setTitle("Edite Document");
            controller.setData(stage  ,this);
            // Create the scene and the stage
            Scene scene = new Scene(root);
            scene.setOnMousePressed(e-> {
                xOffsetadd = e.getSceneX();
                yOffsetadd = e.getSceneY();

            });
            scene.setOnMouseDragged(e->{
                stage.setX(e.getScreenX() - xOffsetadd);
                stage.setY(e.getScreenY() - yOffsetadd);

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
    public void handleExitClick(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void handleMiniClick(ActionEvent event) {
        father.setIconified(true);
    }

    public void fillcombos() throws SQLException, ClassNotFoundException {
        types.getItems().clear();
        categories.getItems().clear();
        ResultSet types_list = sql.displayTypes();
        ResultSet catego_list = sql.displayCategories();
        ObservableList<TyCat> ty_ls = types.getItems();
        ObservableList<TyCat> ca_ls = categories.getItems();
        // Iterate through the result set and create objects for each record
        try {
            while (types_list.next()) {
                String id_in = String.valueOf(types_list.getInt("type_id"));
                String name = types_list.getString("ty_libelle");
                ty_ls.add(new TyCat(id_in,name));
            }
            while (catego_list.next()) {
                String id_in = String.valueOf(catego_list.getInt("cat_id"));
                String name = catego_list.getString("cat_libelle");
                ca_ls.add(new TyCat(id_in,name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @FXML
    public void search_handler(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        search_handler_func();
    }

    @FXML
    public void search_handler_type(ActionEvent event) throws SQLException, ClassNotFoundException, IOException  {
        search_handler_func();
    }

    @FXML
    public void search_handler_cat(ActionEvent event) throws SQLException, ClassNotFoundException, IOException  {
        search_handler_func();
    }
    @FXML
    public void search_handler_invalide(ActionEvent event) throws SQLException, ClassNotFoundException, IOException  {
        search_handler_func();
    }
    @FXML
    public void search_handler_refresh(ActionEvent event) throws SQLException, ClassNotFoundException, IOException  {
        search_handler_func();
    }
    @FXML
    public void search_btn_handler(ActionEvent event) throws SQLException, ClassNotFoundException, IOException  {
        search_handler_func();
    }
    @FXML
    public void search_handler_valide(ActionEvent event) throws SQLException, ClassNotFoundException, IOException  {
        search_handler_func();
    }

    public void search_handler_func() throws SQLException, ClassNotFoundException, IOException {
        String like = search_text.getText();
        TyCat selected_type = types.getValue();
        TyCat selected_cat = categories.getValue();
        String type_id ;
        String cat_id ;
        if (selected_type == null) {
            type_id = "0";
        } else {
            type_id = selected_type.getValue();
        }
        if (selected_cat == null) {
            cat_id = "0";
        } else {
            cat_id = selected_cat.getValue();
        }
        ResultSet rs ;
        boolean emprable = true;
        if(valide.isSelected()){
            rs = sql.displayDocsAvailable(like,cat_id,type_id);
        }else {
            emprable = false;
            rs = sql.displayDocsInavailable(like,cat_id,type_id);
        }
        int col = 0;
        int row = 1;
        this.booksDisplay.getChildren().clear();
        while (rs.next()){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Objects.requireNonNull(getClass().getResource("../resources/fxml/BookCard.fxml")));
            HBox bookCard = fxmlLoader.load();
            BookCardController cardController = fxmlLoader.getController();
            cardController.setData(rs.getString("doc_id"),rs.getString("doc_libelle"),rs.getString("cat_libelle"),rs.getString("ty_libelle"),this.father,this, rs.getString("image"),emprable);
            if(col == 3){
                col = 0;
                ++row;
            }
            booksDisplay.add(bookCard,col++,row);
        }
        rs.close();
        fillcombos();
    }

    @FXML
    public void logout_controller(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
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
    public void switch_dashboard_txt() throws IOException, SQLException, ClassNotFoundException {
        switch_dash();
    }


    private void switch_dash() throws IOException, SQLException, ClassNotFoundException {
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
    void Tycat_handler(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader( Objects.requireNonNull(getClass().getResource("../resources/fxml/addtycat.fxml")));
            Parent root = (Parent) fxmlLoader.load();

            // Get the controller for the scene
            TycateController controller = fxmlLoader.getController();

            // Pass data to the controller
            Stage stage = new Stage();
            stage.setTitle("Edite Document");
            controller.setData(stage  ,this);
            // Create the scene and the stage
            Scene scene = new Scene(root);
            scene.setOnMousePressed(e-> {
                xOffsetadd = e.getSceneX();
                yOffsetadd = e.getSceneY();

            });
            scene.setOnMouseDragged(e->{
                stage.setX(e.getScreenX() - xOffsetadd);
                stage.setY(e.getScreenY() - yOffsetadd);

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
    void switch_etudiant_txt(ActionEvent event) throws IOException {
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
    void switch_staff_txt(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../resources/fxml/Staff.fxml")));
        Parent books_fxml  = loader.load();
        Staff_page_controller controller = loader.getController();
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
}
