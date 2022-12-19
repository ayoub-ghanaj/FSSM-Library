package src;

public class TyCat {
    private String id;
    private String labelle;
    public TyCat(String in_id , String name){
        this.id =  in_id;
        this.labelle = name;
    }
    public String getText() {
        return this.labelle;
    }

    public String getValue() {
        return this.id;
    }
    @Override
    public String toString() {
        return this.id;
    }
}
