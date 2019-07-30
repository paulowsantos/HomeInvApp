package pwayner.com.homeinvapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> implements View.OnClickListener{

    ArrayList<Items> itemsList;
    private View.OnClickListener listener;

    public MyAdapter(ArrayList<Items> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public MyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);

        view.setOnClickListener(this);

        return new MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapterViewHolder holder, final int position) {//ON BIND VIEW HOLDER
        holder.txtName.setText(itemsList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
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
            txtName = (TextView) itemView.findViewById(R.id.idName);
        }
    }
}
