package pwayner.com.homeinvapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class Popup extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference tableItems, tableItems2;
    long message;

    Button btn_plus, btn_minus, btn_cat, btn_save, btn_cancel;
    TextView txt_qtt, txt_name, txt_position;
    EditText txt_qtt_min;
    int id, qtt, qtt_min;
    String txt_cat, txt_nameS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        database = FirebaseDatabase.getInstance();

        tableItems = database.getInstance().getReference("items");
        tableItems2 = database.getInstance().getReference();

        final Query lastQuery2 = tableItems2.child("shoppinglist").orderByKey();

        lastQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                message = dataSnapshot.getChildrenCount() + 1;
                Log.d("TESTE1", message + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Handle possible errors.
            }
        });


        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        btn_cat = (Button) findViewById(R.id.btn_cat);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        txt_qtt = (TextView) findViewById(R.id.txt_qtt);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_qtt_min = (EditText) findViewById(R.id.txt_qtt_min);
        txt_position = new TextView(this);

        txt_cat = getIntent().getExtras().getString("Cat");
        txt_nameS = getIntent().getExtras().getString("Name");
        id = getIntent().getExtras().getInt("ID");
        qtt = getIntent().getExtras().getInt("Qtt");
        qtt_min = getIntent().getExtras().getInt("Qtt_min");

        txt_qtt.setText(qtt + "");
        txt_name.setText(txt_nameS);
        txt_qtt_min.setText(qtt_min + "");
        btn_cat.setText(txt_cat);


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

                Intent cat = new Intent(Popup.this, Popup_Cat.class);
                startActivityForResult(cat, 1);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categ, name;
                int qttS, qtt_minS, msg;
                msg = (int) message;
                Log.d("TESTE123", msg + "");
                categ = btn_cat.getText().toString();
                qttS = Integer.parseInt(txt_qtt.getText().toString());
                qtt_minS = Integer.parseInt(txt_qtt_min.getText().toString());
                name = txt_name.getText().toString();

                tableItems = database.getReference("/items/" + id + "/name");
                tableItems.setValue(name);

                tableItems = database.getReference("/items/" + id + "/category");
                tableItems.setValue(categ);

                tableItems = database.getReference("/items/" + id + "/qtt");
                tableItems.setValue(qttS);

                tableItems = database.getReference("/items/" + id + "/qtt_min");
                tableItems.setValue(qtt_minS);

                if (qttS <= qtt_minS){
                    addShoppingList(name, view.getContext());
                }


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                btn_cat = (Button) findViewById(R.id.btn_cat);
                btn_cat.setText(result);
            }
        }
    }

    public void addShoppingList(String name, Context context){

        double priceW = 0.0, priceC = 0.0;

        String json1 = loadJSONFromAsset(context);

        ArrayList<ShoppingList> price = processData(json1);

        for (ShoppingList a : price){
            if (a.getName().toLowerCase().contains(name.toLowerCase())){
                Log.d("teste1234", a.getName());
                priceW = a.getPriceW();
                priceC = a.getPriceC();
                break;
            }
        }

        ShoppingList temp = new ShoppingList(name, priceW, priceC);

        Log.d("TESTE4", message + "");

        tableItems = database.getReference("/shoppinglist/" + message + "/name");
        tableItems.setValue(temp.getName());

        tableItems = database.getReference("/shoppinglist/" + message + "/priceW");
        tableItems.setValue(temp.getPriceW());

        tableItems = database.getReference("/shoppinglist/" + message + "/priceC");
        tableItems.setValue(temp.getPriceC());

    }

    private ArrayList<ShoppingList> processData(String json) {
        String data = json;
        ArrayList<ShoppingList> temp = new ArrayList<>();
        try {
            JSONArray ar = new JSONArray(data);
            JSONObject element;
            ShoppingList p;
            for (int i=0 ; i < ar.length(); i++) {
                element = ar.getJSONObject(i);
                p = new ShoppingList();
                p.name = element.getString("name");
                p.priceW = element.getDouble("priceW");
                p.priceC = element.getDouble("priceC");
                temp.add(p);
            }
            return temp;
        } catch (JSONException e) {
            Log.d("MainActivity", e.getMessage());
        }

        return null;

    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("prices.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }



}
