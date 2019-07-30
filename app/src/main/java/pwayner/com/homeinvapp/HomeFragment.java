package pwayner.com.homeinvapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView itemRecycler;
    ArrayList<Items> itemList;
    private FirebaseDatabase database;
    private DatabaseReference tableItems;
    private MyAdapter adapter;

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        FloatingActionButton floatingButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingButton.setAlpha(0.5f);

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "fab", Toast.LENGTH_SHORT).show();
            }
        });

        database = FirebaseDatabase.getInstance();
        tableItems = database.getInstance().getReference("items");

        itemList = new ArrayList<>();
        itemRecycler = view.findViewById(R.id.recyclerId);
        itemRecycler.setLayoutManager(new GridLayoutManager(getContext(), 1));

        fillList();

        adapter = new MyAdapter(itemList);

        adapter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), itemList.get(itemRecycler.getChildAdapterPosition(view)).getName(), Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(getContext(), reservation.class);

                //intent.putExtra("Name", itemList.get(itemRecycler.getChildAdapterPosition(view)).getName());

                //startActivity(intent);
            }
        });
        itemRecycler.setAdapter(adapter);

        return view;
    }

    private void fillList() {

        tableItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Items item = itemSnapshot.getValue(Items.class);
                    itemList.add(item);
                }
                for (Items it : itemList){
                    Log.d("teste", it.getName());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Something goes wrong!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
