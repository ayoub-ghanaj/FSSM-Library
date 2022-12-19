package src;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class    SQLiteTools {

    private static Connection con;
    private static boolean hasData = false ;

    public int[] login(String username , String password) throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        int val = 0 ;
        int id = 0 ;
        PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM staff WHERE status =1 AND cin = ? AND pass = ? LIMIT 1;");
        prepStmt.setString(1,username);
        prepStmt.setString(2,password);
        ResultSet rs = prepStmt.executeQuery();
        while (rs.next()){
            id = rs.getInt("staff_id");
            val = rs.getInt("role");
            Main.staff_name = rs.getString("prenom") +" "+ rs.getString("nom");
        }
        return new int[]{val, id};
    }

    public ResultSet displayCategories() throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM categories WHERE status =1;");
        return rs;
    }
    public ResultSet getBookEtudiants(String id_doc )throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT b.cne , c.cin , a.emp_d , a.return_d , a.etud_id , a.staff_id From emprunts a ,etudiant b , staff c WHERE a.etud_id = b.etud_id  AND a.staff_id = c.staff_id AND a.status =1 AND a.doc_id = "+id_doc+"  ORDER BY a.emp_id desc ;;");
        return rs;
    }
    public ResultSet getEtudiantsBook(String id_etu) throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT b.cne , c.cin , a.emp_d , a.return_d , a.etud_id , a.staff_id From emprunts a ,etudiant b , staff c WHERE a.etud_id = b.etud_id  AND a.staff_id = c.staff_id AND a.status =1 AND a.etud_id = "+id_etu+"  ORDER BY a.emp_id desc ;");
        return rs;
    }


    public ResultSet tot_mat() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT count(*) as data FROM docs a ,types b , categories c WHERE a.type_id = b.type_id AND c.cat_id = a.cat_id AND a.doc_id NOT IN (SELECT b.doc_id FROM emprunts b WHERE b.emp_state = 1  AND b.status = 1) AND  a.status =1  ORDER BY a.doc_id desc;");
        return rs;
    }
    public ResultSet tot_etu() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT count(*) as data FROM etudiant a WHERE  a.status =1;");
        return rs;
    }
    public ResultSet tot_emp() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT count(*) data FROM docs a ,types b , categories c WHERE a.type_id = b.type_id AND c.cat_id = a.cat_id AND  a.doc_id IN (SELECT b.doc_id FROM emprunts b WHERE b.emp_state = 1  AND b.status = 1) AND  a.status =1 ORDER BY a.doc_id desc ;");
        return rs;
    }
    public ResultSet stat_user() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT c.cne  , count(a.emp_id) as cnecount FROM emprunts a ,etudiant c WHERE a.etud_id = c.etud_id AND a.status =1 GROUP BY a.etud_id;");
        return rs;
    }
    public ResultSet stat_books() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT b.doc_libelle , count(a.emp_id) doccount FROM emprunts a , docs b WHERE a.doc_id = b.doc_id AND a.status =1 GROUP BY a.doc_id;");
        return rs;
    }
    public ResultSet displayTypes() throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM types WHERE status =1;");
        return rs;
    }

    public ResultSet displayDocsInavailable( String  like_clau , String catego , String typ) throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        String cate = "";
        String type = "";
        String like = "";
        Statement stmt = con.createStatement();
        if(!catego.equals("0")){
            cate = " a.cat_id = "+catego+" AND ";
        }
        if(!typ.equals("0")){
            type = " a.type_id = "+typ+" AND ";
        }
        if(like_clau.length()>0){
            like = " a.doc_libelle LIKE '%"+like_clau+"%' AND ";
        }
        ResultSet rs = stmt.executeQuery("SELECT a.doc_id , a.doc_libelle , b.ty_libelle , c.cat_libelle , a.image FROM docs a ,types b , categories c WHERE a.type_id = b.type_id AND c.cat_id = a.cat_id AND   "+like+type+cate+" a.doc_id IN (SELECT b.doc_id FROM emprunts b WHERE b.emp_state = 1  AND b.status = 1) AND  a.status =1 ORDER BY a.doc_id desc ;");
        return rs;
    }
    public ResultSet displayDocsAvailable( String  like_clau , String catego , String typ) throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        String cate = "";
        String type = "";
        String like = "";
        Statement stmt = con.createStatement();
        if(!catego.equals("0")){
            cate = " a.cat_id = "+catego+" AND ";
        }
        if(!typ.equals("0")){
            type = " a.type_id = "+typ+" AND ";
        }
        if(like_clau.length()>0){
            like = " a.doc_libelle LIKE '%"+like_clau+"%' AND ";
        }
        ResultSet rs = stmt.executeQuery("SELECT a.doc_id , a.doc_libelle , b.ty_libelle , c.cat_libelle , a.image FROM docs a ,types b , categories c WHERE a.type_id = b.type_id AND c.cat_id = a.cat_id AND   "+like+type+cate+" a.doc_id NOT IN (SELECT b.doc_id FROM emprunts b WHERE b.emp_state = 1  AND b.status = 1) AND  a.status =1  ORDER BY a.doc_id desc;");
        return rs;
    }
    public ResultSet displayStudents() throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM etudiant WHERE status =1;");
        return rs;
    }

    ///// docs
    public int updateDoc(String id , String name , String type_id, String cate_id , String img) throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        return  stmt.executeUpdate("UPDATE docs  SET  doc_libelle = '"+name+"' , image = '"+img+"', cat_id = "+cate_id+" , type_id = "+type_id+" WHERE doc_id = "+id+";");
    }
    public int deleteDoc(String id ) throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        stmt.executeUpdate("UPDATE emprunts set status = 0 WHERE doc_id = "+id+";");
        return  stmt.executeUpdate("UPDATE docs  SET  status = 0  WHERE doc_id = "+id+";");
    }
    public int updateDoc_keep_image(String id , String name , String type_id, String cate_id ) throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        return  stmt.executeUpdate("UPDATE docs  SET  doc_libelle = '"+name+"' , cat_id = "+cate_id+" , type_id = "+type_id+" WHERE doc_id = "+id+";");
    }


    public ResultSet displayDoc(int id) throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM docs WHERE status =1 AND doc_id = ?;");
        prepStmt.setInt(1,id);
        ResultSet rs = prepStmt.executeQuery();
        return rs;
    }
    public ResultSet displayDocs() throws SQLException, ClassNotFoundException {

        if (con == null) {
            getConnection();
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM docs WHERE status =1;");
        return rs;
    }

    public void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:database/test1.db");
        initialize();
    }

    public void initialize() throws SQLException {
        if(!hasData){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master  where  type='table' AND name='types'");
            if(!rs.next()){
                System.out.println("Building table");
                stmt =con.createStatement();
                stmt.execute("CREATE TABLE types (\n" +
                        "type_id INTEGER Primary key AUTOINCREMENT,\n" +
                        "ty_libelle TEXT,\n" +
                        "status INTEGER DEFAULT 1\n" +
                        ");");
                PreparedStatement prepStmt = con.prepareStatement("INSERT INTO types(ty_libelle) values(?);");
                prepStmt.setString(1,"typ1");
                prepStmt.execute();
                PreparedStatement prepStmt1 = con.prepareStatement("INSERT INTO types(ty_libelle) values(?);");
                prepStmt1.setString(1,"typ2");
                prepStmt1.execute();
            }

            rs = stmt.executeQuery("SELECT name FROM sqlite_master  where  type='table' AND name='categories'");
            if(!rs.next()){
                System.out.println("Building table");
                stmt =con.createStatement();
                stmt.execute("CREATE TABLE categories (" +
                        "cat_id INTEGER Primary key AUTOINCREMENT, " +
                        "cat_libelle TEXT, " +
                        "status INTEGER DEFAULT 1" +
                        ");");
                PreparedStatement prepStmt = con.prepareStatement("INSERT INTO categories(cat_libelle) values(?);");
                prepStmt.setString(1,"cat1");
                prepStmt.execute();
                PreparedStatement prepStmt1 = con.prepareStatement("INSERT INTO categories(cat_libelle) values(?);");
                prepStmt1.setString(1,"cat2");
                prepStmt1.execute();
            }

            rs = stmt.executeQuery("SELECT name FROM sqlite_master  where  type='table' AND name='docs'");
            if(!rs.next()){
                System.out.println("Building table");
                stmt =con.createStatement();
                stmt.execute("CREATE TABLE docs (\n" +
                        "doc_id INTEGER Primary key AUTOINCREMENT,\n" +
                        "doc_libelle TEXT,\n" +
                        "status INTEGER DEFAULT 1,\n" +
                        "image TEXT,\n"+
                        "cat_id INTEGER,\n" +
                        "type_id INTEGER,\n" +
                        "constraint fk_cat foreign key (cat_id) references categories(cat_id ) ON DELETE CASCADE,\n" +
                        "constraint fk_type foreign key (type_id) references types(type_id ) ON DELETE CASCADE\n" +
                        ");");
                PreparedStatement prepStmt = con.prepareStatement("INSERT INTO docs(doc_libelle,cat_id,type_id,image) values(?,1,1,?);");
                prepStmt.setString(1,"book1");
                prepStmt.setString(2,"C:\\Users\\HP\\Desktop\\FSSMLIB\\resources\\img\\book2.png");
                prepStmt.execute();
                PreparedStatement prepStmt1 = con.prepareStatement("INSERT INTO docs(doc_libelle,cat_id,type_id,image) values(?,1,1,?);");
                prepStmt1.setString(1,"book2");
                prepStmt1.setString(2,"C:\\Users\\HP\\Desktop\\FSSMLIB\\resources\\img\\book2.png");
                prepStmt1.execute();
                PreparedStatement prepStmt2 = con.prepareStatement("INSERT INTO docs(doc_libelle,cat_id,type_id,image) values(?,1,1,?);");
                prepStmt2.setString(1,"book3");
                prepStmt2.setString(2,"C:\\Users\\HP\\Desktop\\FSSMLIB\\resources\\img\\book2.png");
                prepStmt2.execute();
                PreparedStatement prepStmt3 = con.prepareStatement("INSERT INTO docs(doc_libelle,cat_id,type_id,image) values(?,1,1,?);");
                prepStmt3.setString(1,"book4");
                prepStmt3.setString(2,"C:\\Users\\HP\\Desktop\\FSSMLIB\\resources\\img\\book2.png");
                prepStmt3.execute();
            }

            rs = stmt.executeQuery("SELECT name FROM sqlite_master  where  type='table' AND name='etudiant'");
            if(!rs.next()){
                System.out.println("Building table");
                stmt =con.createStatement();
                stmt.execute("CREATE TABLE etudiant (\n" +
                        "etud_id INTEGER Primary key AUTOINCREMENT,\n" +
                        "cne TEXT NOT NULL UNIQUE,\n" +
                        "nom TEXT,\n" +
                        "prenom TEXT,\n" +
                        "image TEXT,\n" +
                        "phone TEXT NOT NULL UNIQUE,\n" +
                        "email TEXT UNIQUE,\n" +
                        "status INTEGER DEFAULT 1\n" +
                        ");");
                PreparedStatement prepStmt = con.prepareStatement("INSERT INTO etudiant(cne,nom,prenom,image,phone,email) values(?,?,?,?,?,?);");
                prepStmt.setString(1,"cne123");
                prepStmt.setString(2,"ahmed");
                prepStmt.setString(3,"ali");
                prepStmt.setString(4,"../resources/img/user.png");
                prepStmt.setString(5,"0677090909");
                prepStmt.setString(6,"ali@gmail.com");
                prepStmt.execute();
            }

            rs = stmt.executeQuery("SELECT name FROM sqlite_master  where  type='table' AND name='staff'");
            if(!rs.next()){
                System.out.println("Building table");
                stmt =con.createStatement();
                stmt.execute("CREATE TABLE staff (\n" +
                        "staff_id INTEGER primary key AUTOINCREMENT ,\n" +
                        "cin TEXT NOT NULL UNIQUE ,\n" +
                        "nom TEXT  ,\n" +
                        "prenom TEXT  ,\n" +
                        "phone TEXT  ,\n" +
                        "role INTEGER DEFAULT 2 ,\n" +
                        "pass TEXT NOT NULL ,\n" +
                        "status INTEGER DEFAULT 1\n" +
                        ");");

                PreparedStatement prepStmt = con.prepareStatement("INSERT INTO staff(cin,nom,prenom,phone,pass,role) values(?,?,?,?,?,?);");
                prepStmt.setString(1,"admin");
                prepStmt.setString(2,"admin");
                prepStmt.setString(3,"admin");
                prepStmt.setString(4,"admin");
                prepStmt.setString(5,"admin");
                prepStmt.setInt(6,1);
                prepStmt.execute();
            }
            rs = stmt.executeQuery("SELECT name FROM sqlite_master  where  type='table' AND name='emprunts'");
            if(!rs.next()){
                System.out.println("Building table");
                stmt =con.createStatement();
                stmt.execute("CREATE TABLE emprunts (\n" +
                        "emp_id INTEGER primary key AUTOINCREMENT ,\n" +
                        "doc_id INTEGER,\n" +
                        "etud_id INTEGER ,\n" +
                        "staff_id INTEGER,\n" +
                        "emp_state INTEGER DEFAULT 1,\n" +
                        "emp_d TEXT,\n" +
                        "return_d TEXT,\n" +
                        "status INTEGER DEFAULT 1\n," +
                        "constraint fk_doc foreign key (doc_id) references docs(doc_id ) ON DELETE CASCADE,\n" +
                        "constraint fk_etudiant foreign key (etud_id) references etudiant(etud_id ) ON DELETE CASCADE,\n" +
                        "constraint fk_staff foreign key (staff_id) references staff(staff_id ) ON DELETE CASCADE" +
                        ");");
                PreparedStatement prepStmt = con.prepareStatement("INSERT INTO emprunts(doc_id,etud_id,staff_id,emp_d,return_d,emp_state) values(?,?,?,?,?,1);");
                prepStmt.setInt(1,1);
                prepStmt.setInt(2,1);
                prepStmt.setInt(3,1);
                prepStmt.setString(4,"11/11/2002");
                prepStmt.setString(5,"-");
                prepStmt.execute();
                PreparedStatement prepStmt2 = con.prepareStatement("INSERT INTO emprunts(doc_id,etud_id,staff_id,emp_d,return_d,emp_state) values(?,?,?,?,?,0);");
                prepStmt2.setInt(1,2);
                prepStmt2.setInt(2,1);
                prepStmt2.setInt(3,1);
                prepStmt2.setString(4,"11/11/2002");
                prepStmt2.setString(5,"11/12/2012");
                prepStmt2.execute();
                PreparedStatement prepStmt4 = con.prepareStatement("INSERT INTO emprunts(doc_id,etud_id,staff_id,emp_d,return_d,emp_state) values(?,?,?,?,?,0);");
                prepStmt4.setInt(1,2);
                prepStmt4.setInt(2,1);
                prepStmt4.setInt(3,1);
                prepStmt4.setString(4,"11/11/2002");
                prepStmt4.setString(5,"11/12/2012");
                prepStmt4.execute();
                PreparedStatement prepStmt3 = con.prepareStatement("INSERT INTO emprunts(doc_id,etud_id,staff_id,emp_d,return_d,emp_state) values(?,?,?,?,?,0);");
                prepStmt3.setInt(1,4);
                prepStmt3.setInt(2,1);
                prepStmt3.setInt(3,1);
                prepStmt3.setString(4,"11/11/2002");
                prepStmt3.setString(5,"11/12/2012");
                prepStmt3.execute();
            }
            rs = stmt.executeQuery("SELECT name FROM sqlite_master  where  type='table' AND name='trace'");
            if(!rs.next()){
                System.out.println("Building table");
                stmt =con.createStatement();
                stmt.execute("CREATE TABLE trace (\n" +
                        "trace INTEGER primary key AUTOINCREMENT ,\n" +
                        "date TEXT,\n" +
                        "info TEXT ,\n" +
                        "staff_id INTEGER,\n" +
                        "constraint fk_staff foreign key (staff_id) references staff(staff_id ) ON DELETE CASCADE" +
                        ");");

                PreparedStatement prepStmt1 = con.prepareStatement("INSERT INTO trace(staff_id,info,date) values(?,?,?);");
                prepStmt1.setInt(1,1);
                prepStmt1.setString(2,"admin");
                prepStmt1.setString(3,"11/11/2002");
                prepStmt1.execute();
            }



            hasData = true;
        }
    }
    public void  addDoc(String labell , int catId , int typeId , String Image_path  ) throws SQLException, ClassNotFoundException {
        if(con == null){
            getConnection();
        }
        PreparedStatement prepStmt = con.prepareStatement("INSERT INTO docs(doc_libelle,cat_id,type_id,image) values(?,?,?,?);");
        prepStmt.setString(1,labell);
        prepStmt.setInt(2,catId);
        prepStmt.setInt(3,typeId);
        prepStmt.setString(4,Image_path);
        prepStmt.execute();
    }
    public void  addlog(int staff_id , String text) throws SQLException, ClassNotFoundException {
        if(con == null){
            getConnection();
        }
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Format the date and time as a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = now.format(formatter);
        PreparedStatement prepStmt1 = con.prepareStatement("INSERT INTO trace(staff_id,info,date) values(?,?,?);");
        prepStmt1.setInt(1,1);
        prepStmt1.setString(2,dateString);
        prepStmt1.setString(3,text);
        prepStmt1.execute();
    }
    public void addEtudiant(String cne ,String nom ,String prenom ,String email , String phone ,String image ) throws SQLException, ClassNotFoundException {
        if(con == null){
            getConnection();
        }
        PreparedStatement prepStmt = con.prepareStatement("INSERT INTO etudiant(cne,nom,prenom,phone,email,image,status) values(?,?,?,?,?,?,1);");
        prepStmt.setString(1,cne);
        prepStmt.setString(2,nom);
        prepStmt.setString(3,prenom);
        prepStmt.setString(4,phone);
        prepStmt.setString(5,email);
        prepStmt.setString(6,image);
        prepStmt.execute();

    }
}