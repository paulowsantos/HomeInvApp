package pwayner.com.homeinvapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Popup extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference tableItems;

    Button btn_plus, btn_minus, btn_cat, btn_save, btn_cancel;
    TextView txt_qtt, txt_name;
    EditText txt_qtt_min;
    int id, qtt, qtt_min;
    String txt_cat, txt_nameS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        database = FirebaseDatabase.getInstance();
        tableItems = database.getInstance().getReference("items");

        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        btn_cat = (Button) findViewById(R.id.btn_cat);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        txt_qtt = (TextView) findViewById(R.id.txt_qtt);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_qtt_min = (EditText) findViewById(R.id.txt_qtt_min);

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
                int qttS, qtt_minS;
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

                //checkShoppingList();

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

}
