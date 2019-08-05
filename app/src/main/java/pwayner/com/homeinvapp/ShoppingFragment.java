package pwayner.com.homeinvapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoppingFragment extends Fragment {

    RecyclerView itemRecycler, itemRecycler2;
    private FirebaseDatabase database;
    private DatabaseReference tableItems;
    private ArrayList<ShoppingList> shopList, shopTotals;
    private MyAdapter3 adapter3, adapter4;
    double total1 = 0.0, total2 = 0.0;

    public ShoppingFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopping_fragment, container, false);

        database = FirebaseDatabase.getInstance();
        tableItems = database.getInstance().getReference("shoppinglist");

        shopList = new ArrayList<>();
        shopTotals = new ArrayList<>();
        itemRecycler = view.findViewById(R.id.recyclerId3);
        itemRecycler.setLayoutManager(new GridLayoutManager(getContext(), 1));
        itemRecycler2 = view.findViewById(R.id.recyclerId4);
        itemRecycler2.setLayoutManager(new GridLayoutManager(getContext(), 1));

        fillList();

        adapter3 = new MyAdapter3(shopList);
        adapter4 = new MyAdapter3(shopTotals);

        adapter3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int id = itemRecycler.getChildAdapterPosition(view) + 1;

                tableItems = database.getReference("/shoppinglist/" + id);
                tableItems.setValue(null);

            }
        });
        itemRecycler.setAdapter(adapter3);
        itemRecycler2.setAdapter(adapter4);

        return view;
    }

    private void fillList() {


        tableItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shopList.clear();
                shopTotals.clear();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    ShoppingList item = itemSnapshot.getValue(ShoppingList.class);
                    total1 = total1 + item.getPriceW();
                    total2 = total2 + item.getPriceC();
                    shopList.add(item);
                }
                ShoppingList totals = new ShoppingList("Total", total2, total1);
                shopTotals.add(totals);
                adapter3.notifyDataSetChanged();
                adapter4.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Something goes wrong!!!", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
