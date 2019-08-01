package pwayner.com.homeinvapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Popup2 extends Activity {

    private FirebaseDatabase database;
    private DatabaseReference tableItems;

    private long message;
    Button btn_plus, btn_minus, btn_cat, btn_save, btn_cancel;
    TextView txt_qtt;
    EditText txt_qtt_min, txt_name;
    private int id, qtt, qtt_min;
    private String txt_cat, txt_nameS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow2);

        database = FirebaseDatabase.getInstance();
        tableItems = database.getInstance().getReference();
        final Query lastQuery = tableItems.child("items").orderByKey();


        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        btn_cat = (Button) findViewById(R.id.btn_cat);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        txt_qtt = (TextView) findViewById(R.id.txt_qtt);
        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_qtt_min = (EditText) findViewById(R.id.txt_qtt_min);

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


        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qtt++;
                txt_qtt.setText(qtt + "");
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qtt--;
                if (qtt < 0){
                    qtt = 0;
                }
                txt_qtt.setText(qtt + "");
            }
        });

        btn_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loadFragment(new CatFragment());
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = (int) message;
                qtt = Integer.parseInt(txt_qtt.getText().toString());
                qtt_min = Integer.parseInt(txt_qtt_min.getText().toString());
                txt_cat = btn_cat.getText().toString();
                txt_nameS = txt_name.getText().toString();

                Items tempItem = new Items(txt_nameS, txt_cat, id, qtt, qtt_min);

                tableItems = database.getReference("/items/" + message + "/name");
                tableItems.setValue(tempItem.getName());
                tableItems = database.getReference("/items/" + message + "/id");
                tableItems.setValue(tempItem.getId());
                tableItems = database.getReference("/items/" + message + "/category");
                tableItems.setValue(tempItem.getCategory());
                tableItems = database.getReference("/items/" + message + "/qtt");
                tableItems.setValue(tempItem.getQtt());
                tableItems = database.getReference("/items/" + message + "/qtt_min");
                tableItems.setValue(tempItem.getQtt_min());

                finish();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.6));
    }

    /*public void loadFragment(Fragment fragment){
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        mTransaction.replace(R.id.frameLayout, fragment);
        mTransaction.addToBackStack(null);
        mTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
        mTransaction.commit();
    }*/
}
