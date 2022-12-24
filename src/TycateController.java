package src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Long.MAX_VALUE;

public class TycateController {

    @FXML
    private TextField new_type;

    @FXML
    private Label err_label;

    @FXML
    private Button Save_btn1;

    @FXML
    private GridPane types_list;

    @FXML
    private GridPane cate_list;

    @FXML
    private TextField new_cate;

    @FXML
    private Button Save_btn;


    private Stage father;
    final private SQLiteTools sql = new SQLiteTools();


    private  Books_page_controller home_controller;

    @FXML
    void closeitup(ActionEvent event) throws SQLException, ClassNotFoundException {
        home_controller.fillcombos();
        father.close();
    }

    public void Refresh(){
        try {
            ResultSet rs = sql.displayTypes();
            types_list.getChildren().clear();
            cate_list.getChildren().clear();
            int i =0;
            while (rs.next()){
                Label libelle = new Label(rs.getString("ty_libelle"));
                libelle.setAlignment(Pos.CENTER);
                libelle.setMaxWidth(MAX_VALUE);
                Button delete = new Button("Delete");
                delete.setAlignment(Pos.CENTER);
                delete.setMaxWidth(MAX_VALUE);
                delete.setStyle("  -fx-text-fill: rgb(240, 240, 240);" +
                        "  -fx-background-color: rgb(51, 51, 51);" +
                        "  -fx-border-color: rgb(12, 12, 12);" +
                        "  -fx-border-width: 0px;" +
                        "  -fx-border-radius: 1px;" +
                        "  -fx-font-size: 11px;" +
                        "  -fx-font-weight: bold;");
                delete.setId(rs.getString("type_id"));
                delete.setOnAction(e->{

                    try {
                        int v = sql.deletetype(delete.getId());
                    Refresh();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                types_list.add(libelle , 0,i);
                types_list.add(delete , 1,i);
                i++;
            }
            i=0;
            ResultSet rs2  = sql.displayCategories();
            while (rs2.next()){
                Label libelle = new Label(rs2.getString("cat_libelle"));
                libelle.setAlignment(Pos.CENTER);
                libelle.setMaxWidth(MAX_VALUE);
                Button delete = new Button("Delete");
                delete.setAlignment(Pos.CENTER);
                delete.setMaxWidth(MAX_VALUE);
                delete.setId(rs2.getString("cat_id"));
                delete.setStyle("  -fx-text-fill: rgb(240, 240, 240);" +
                        "  -fx-background-color: rgb(51, 51, 51);" +
                        "  -fx-border-color: rgb(12, 12, 12);" +
                        "  -fx-border-width: 0px;" +
                        "  -fx-border-radius: 1px;" +
                        "  -fx-font-size: 11px;" +
                        "  -fx-font-weight: bold;");
                delete.setOnAction(e->{

                    try {
                        int v = sql.deletetype(delete.getId());
                        Refresh();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                cate_list.add(libelle , 0,i);
                cate_list.add(delete , 1,i);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void saveHandler_type(ActionEvent event) throws SQLException {
        sql.addType(new_type.getText().trim());
        Refresh();
    }
    @FXML
    void saveHandler_cate(ActionEvent event) throws SQLException {
        sql.addCate(new_cate.getText().trim());
        Refresh();
    }

    public void setData(Stage stage, Books_page_controller controller) {
        Refresh();
        father=stage;
        home_controller  = controller;

    }
}
