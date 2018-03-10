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
 * Created by Administrator on 2018/3/10.
 */

public class Re_FruitAdapter extends ArrayAdapter<Re_Fruit> {
    private int resourceID;
    public Re_FruitAdapter(Context context, int textViewReasourceID, List<Re_Fruit> objects){
        super(context,textViewReasourceID,objects);
        resourceID = textViewReasourceID;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Re_Fruit fruit =getItem(position);
        View view;
        Re_FruitAdapter.ViewHolder viewHolder;
        if(convertView ==null){
            view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
            viewHolder =new Re_FruitAdapter.ViewHolder();
            viewHolder.textView1 = (TextView)view.findViewById(R.id.bookid);
            viewHolder.textView2 = (TextView)view.findViewById(R.id.bookname);
            viewHolder.textView3 = (TextView)view.findViewById(R.id.borrowdate);
            view.setTag(viewHolder);
        }else {
            view =convertView;
            viewHolder = (Re_FruitAdapter.ViewHolder)view.getTag();
        }
        viewHolder.textView1.setText(fruit.getId());
        viewHolder.textView2.setText(fruit.getBookname());
        viewHolder.textView3.setText(fruit.getRe_borrowdate());

        return view;
    }
    class ViewHolder{
        TextView textView1;
        TextView textView2;
        TextView textView3;
    }
}
