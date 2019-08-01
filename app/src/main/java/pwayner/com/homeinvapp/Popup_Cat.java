package pwayner.com.homeinvapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Popup_Cat extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference tableItems;
    String result1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow_cat);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.6));

        Fragment frag = new CatFragment();
        loadFragment(frag);


    }

    public void loadFragment(Fragment fragment){
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        mTransaction.replace(R.id.relativeLayout, fragment);
        mTransaction.addToBackStack(null);
        mTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
        mTransaction.commit();
    }

    public void getCat1(String cat1){
        result1 = cat1;
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",result1);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
