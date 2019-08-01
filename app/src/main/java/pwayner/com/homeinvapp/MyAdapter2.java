package pwayner.com.homeinvapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyAdapterViewHolder> implements View.OnClickListener{

    ArrayList<Category> categoriesList;
    private View.OnClickListener listener;

    public MyAdapter2(ArrayList<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

    @Override
    public MyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list2, null, false);

        view.setOnClickListener(this);

        return new MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapterViewHolder holder, final int position) {//ON BIND VIEW HOLDER
        holder.txtName.setText(categoriesList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }

    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;

        public MyAdapterViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.idName2);
        }
    }
}
