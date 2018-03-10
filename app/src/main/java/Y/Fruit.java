package Y;

/**
 * Created by sp on 2018/3/8.
 */

public class Fruit {
    private String bookname,bookid,bookauther,bookstyle;
    public Fruit(String bookid,String bookname,String bookauther,String bookstyle){
        this.bookid=bookid;
        this.bookname =bookname;
        this.bookauther =bookauther;
        this.bookstyle=bookstyle;
    }
    public String getBookid(){
        return bookid;
    }
    public String getBookname(){
        return bookname;
    }public String getBookauther(){
        return bookauther;
    }public String getBookstyle(){
        return bookstyle;
    }
}
