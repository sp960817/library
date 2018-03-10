package Y;

/**
 * Created by Administrator on 2018/3/10.
 */

public class Re_Fruit {
    private String bookname,id,re_borrowdate;
    public Re_Fruit(String id,String bookname,String re_borrowdate){
        this.id=id;
        this.bookname =bookname;
        this.re_borrowdate =re_borrowdate;
    }
    public String getId(){
        return id;
    }
    public String getBookname(){
        return bookname;
    }
    public String getRe_borrowdate(){return re_borrowdate;}
}

