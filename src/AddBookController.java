package src;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {

    @FXML
    private Button Save_btn1;
    @FXML
    private Label err_label;

    @FXML
    private TextField label_textField;

    @FXML
    private ComboBox<TyCat> Type_combo;

    @FXML
    private ComboBox<TyCat> Cate_combo;

    @FXML
    private ImageView Image_view;

    @FXML
    private Button Save_btn;

    private Stage father;

    final private SQLiteTools sql = new SQLiteTools();

    private File File_image =null ;
    private Image image = null;
    private  Books_page_controller home_controller;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.Image_view.setOnMouseClicked(e->{
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Image");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
                );
                File_image = fileChooser.showOpenDialog(father);
                if (File_image != null) {
                    image = new Image(File_image.toURI().toString());
                    Image_view.setImage(image);
                }
            });
        }catch (Exception e){

        }


    }

    @FXML
    void saveHandler(ActionEvent event) {
        if(File_image != null && image != null && Type_combo.getValue() != null &&Cate_combo.getValue() != null && label_textField.getText().trim().length() >=0){
            err_label.setVisible(false);
            String path =  new File("../database/test1.db").getAbsolutePath();
            path = path.substring(0, path.length() - 20);
            String new_name = "IMG-"+String.valueOf(Math.random()).substring(2) +"."+getFileExtension(File_image).toLowerCase();
            try (FileInputStream in = new FileInputStream(File_image.getAbsolutePath());
                 FileOutputStream out = new FileOutputStream(path+"resources\\img\\"+new_name)) {

                // Use a buffer to copy the file
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                out.close();
                in.close();
                sql.addDoc(label_textField.getText().trim() , Integer.valueOf(Type_combo.getValue().getValue()) , Integer.valueOf(Cate_combo.getValue().getValue()),path+"resources\\img\\"+new_name);
                home_controller.search_handler_func();
                father.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else {
            err_label.setVisible(true);
        }
    }

    @FXML
    void deleteHandler(ActionEvent event) {
        father.close();
    }

    public void fillcombos() throws SQLException, ClassNotFoundException {
        Type_combo.getItems().clear();
        Cate_combo.getItems().clear();
        ResultSet types_list = sql.displayTypes();
        ResultSet catego_list = sql.displayCategories();
        ObservableList<TyCat> ty_ls = Type_combo.getItems();
        ObservableList<TyCat> ca_ls = Cate_combo.getItems();
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

    public void setData(Stage father_in , Books_page_controller  home_con) throws SQLException, ClassNotFoundException {
        this.home_controller = home_con;
        fillcombos();
        this.father = father_in;
    }
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }
}
