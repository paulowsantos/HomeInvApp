package pwayner.com.homeinvapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyAdapterViewHolder> implements View.OnClickListener{

    ArrayList<ShoppingList> shopList;
    private View.OnClickListener listener;

    public MyAdapter3(ArrayList<ShoppingList> shopList) {
        this.shopList = shopList;
    }

    @Override
    public MyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list3, null, false);

        view.setOnClickListener(this);

        return new MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapterViewHolder holder, final int position) {//ON BIND VIEW HOLDER
        DecimalFormat df2 = new DecimalFormat("#.##");
        String tPriceW = String.format("%.2f", shopList.get(position).getPriceW());
        String tPriceC = String.format("%.2f", shopList.get(position).getPriceC());

        holder.txtName.setText(shopList.get(position).getName());
        holder.txtPriceW.setText(tPriceW);
        holder.txtPriceC.setText(tPriceC);
        if (shopList.get(position).getPriceW() > shopList.get(position).getPriceC()) {
            holder.txtPriceC.setBackgroundColor(Color.parseColor("#00FF00"));
            holder.txtPriceW.setBackgroundColor(Color.parseColor("#FF0000"));
        } else if (shopList.get(position).getPriceW() < shopList.get(position).getPriceC()){
            holder.txtPriceC.setBackgroundColor(Color.parseColor("#FF0000"));
            holder.txtPriceW.setBackgroundColor(Color.parseColor("#00FF00"));
        }
    }

    @Override
    public int getItemCount() {
        return shopList.size();
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
        TextView txtName, txtPriceW, txtPriceC;

        public MyAdapterViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.idName3);
            txtPriceW = (TextView) itemView.findViewById(R.id.idPriceW);
            txtPriceC = (TextView) itemView.findViewById(R.id.idPriceC);
        }
    }
}
