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

public class FruitAllAdapter extends ArrayAdapter<Fruit_all> {
    private int resourceID;

    public FruitAllAdapter(Context context, int textViewReasourceID, List<Fruit_all> objects) {
        super(context, textViewReasourceID, objects);
        resourceID = textViewReasourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit_all fruit_all = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceID, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView1 = (TextView) view.findViewById(R.id.dialog_name);
            viewHolder.textView2 = (TextView) view.findViewById(R.id.dialog_auther);
            viewHolder.textView3 = (TextView) view.findViewById(R.id.dialog_style);
            viewHolder.textView4 = (TextView) view.findViewById(R.id.dialog_pub);
            viewHolder.textView5 = (TextView) view.findViewById(R.id.dialog_pubdate);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView1.setText(fruit_all.getBookname());
        viewHolder.textView2.setText(fruit_all.getBookauther());
        viewHolder.textView3.setText(fruit_all.getBookstyle());
        viewHolder.textView4.setText(fruit_all.getBookpub());
        viewHolder.textView5.setText(fruit_all.getBookpubdate());
        return view;
    }

    class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
    }
}
