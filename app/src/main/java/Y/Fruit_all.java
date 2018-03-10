package Y;

/**
 * Created by sp on 2018/3/9.
 */

public class Fruit_all {
    private String bookname,bookauther,bookstyle,bookpub,bookpubdate;
    public Fruit_all(String bookname,String bookauther,String bookstyle,String bookpub,String bookpubdate){
        this.bookname =bookname;
        this.bookauther =bookauther;
        this.bookstyle=bookstyle;
        this.bookpub=bookpub;
        this.bookpubdate=bookpubdate;
    }

    public String getBookname(){

        return bookname;
    }
    public String getBookauther() {
        return bookauther;
    }
    public String getBookstyle(){

        return bookstyle;
    }
    public String getBookpub(){
        return bookpub;
    }
    public String getBookpubdate(){
        return bookpubdate;
    }
}
