package Y;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.library.R;

import java.util.List;

/**
 * Created by sp on 2018/3/8.
 */

public class FruitAdapter extends ArrayAdapter<Fruit>{
    private int resourceID;
    public FruitAdapter(Context context,int textViewReasourceID,List<Fruit> objects){
        super(context,textViewReasourceID,objects);
        resourceID = textViewReasourceID;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        Fruit fruit =getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView ==null){
            view =LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
            viewHolder =new ViewHolder();
            viewHolder.textView1 = (TextView)view.findViewById(R.id.bookid);
            viewHolder.textView2 = (TextView)view.findViewById(R.id.bookname);
            viewHolder.textView3 = (TextView)view.findViewById(R.id.bookauther);
            viewHolder.textView4 = (TextView)view.findViewById(R.id.bookstyle);
            view.setTag(viewHolder);
        }else {
            view =convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.textView1.setText(fruit.getBookid());
        viewHolder.textView2.setText(fruit.getBookname());
        viewHolder.textView3.setText(fruit.getBookauther());
        viewHolder.textView4.setText(fruit.getBookstyle());
        return view;
    }
    class ViewHolder{
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
    }

}