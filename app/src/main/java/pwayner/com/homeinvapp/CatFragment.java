package pwayner.com.homeinvapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CatFragment extends Fragment {

    RecyclerView categoryRecycler;
    ArrayList<Category> categoryList;
    private FirebaseDatabase database;
    private DatabaseReference tableCategory;
    private MyAdapter2 adapter;
    Button btn_add;
    EditText new_cat;
    String txt_new_cat;
    long message;
    int id;

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
        //tableCategory = database.getInstance().getReference("category");
        tableCategory = database.getInstance().getReference();
        final Query lastQuery = tableCategory.child("category").orderByKey();

        categoryList = new ArrayList<>();
        categoryRecycler = view.findViewById(R.id.recyclerId2);
        categoryRecycler.setLayoutManager(new GridLayoutManager(getContext(), 1));

        btn_add = (Button) view.findViewById(R.id.btn_add);
        new_cat = (EditText) view.findViewById(R.id.new_cat);

        fillList();

        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                message = dataSnapshot.getChildrenCount() + 1;
                Log.d("teste", message + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Handle possible errors.
            }
        });

        adapter = new MyAdapter2(categoryList);

        adapter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String pass = "";
                Popup_Cat pops = (Popup_Cat) getActivity();
                pass = categoryList.get(categoryRecycler.getChildAdapterPosition(view)).getName();
                Log.d("teste", pass);
                pops.getCat1(pass);
            }
        });
        categoryRecycler.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_new_cat = new_cat.getText().toString();

                if (txt_new_cat.trim().equals("")){
                    Toast.makeText(getContext(), "Please insert a valid name.", Toast.LENGTH_LONG).show();
                } else {
                    id = (int) message;
                    txt_new_cat = txt_new_cat.trim();

                    Category tempCat = new Category(txt_new_cat, id);

                    tableCategory = database.getReference("/category/" + message + "/name");
                    tableCategory.setValue(tempCat.getName());
                    tableCategory = database.getReference("/category/" + message + "/id");
                    tableCategory.setValue(tempCat.getId());

                    message++;

                }
            }
        });

        return view;
    }

    private void fillList() {

        tableCategory = database.getInstance().getReference("category");
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
