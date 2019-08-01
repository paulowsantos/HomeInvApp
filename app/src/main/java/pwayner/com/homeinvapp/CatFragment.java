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

public class CatFragment extends Fragment {

    RecyclerView categoryRecycler;
    ArrayList<Category> categoryList;
    private FirebaseDatabase database;
    private DatabaseReference tableCategory;
    private MyAdapter2 adapter;

    public CatFragment() {
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
        View view = inflater.inflate(R.layout.cat_fragment, container, false);


        database = FirebaseDatabase.getInstance();
        tableCategory = database.getInstance().getReference("category");

        categoryList = new ArrayList<>();
        categoryRecycler = view.findViewById(R.id.recyclerId2);
        categoryRecycler.setLayoutManager(new GridLayoutManager(getContext(), 1));

        fillList();

        adapter = new MyAdapter2(categoryList);

        adapter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String pass = "";
                Popup pops = new Popup();
                pass = categoryList.get(categoryRecycler.getChildAdapterPosition(view)).getName();
                Log.d("teste", pass);
                pops.getCat(pass);
            }
        });
        categoryRecycler.setAdapter(adapter);

        return view;
    }

    private void fillList() {


        tableCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoryList.clear();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Category catt = itemSnapshot.getValue(Category.class);
                    categoryList.add(catt);
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
