package pwayner.com.homeinvapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ShoppingFragment extends Fragment {

    TextView txtResult;

    private DBConnectivity receiver;

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

        Button b_add = (Button) view.findViewById(R.id.btnAdd);

        txtResult = (TextView) view.findViewById(R.id.txtResult);
        final Spinner spin = (Spinner) view.findViewById(R.id.spinner);
        final TableLayout tbl = (TableLayout) view.findViewById(R.id.tblResult);

        receiver = new DBConnectivity(txtResult,tbl,getContext());

        b_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),DownloadService.class);
                i.putExtra("state",spin.getSelectedItem().toString());
                txtResult.setText("About to do background service");
                getActivity().startService(i);
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        // Unregister since the activity is paused.
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        // An IntentFilter can match against actions, categories, and data
        IntentFilter filter = new IntentFilter(DBConnectivity.STATUS_DONE);
          /*
        Intent registerReceiver (BroadcastReceiver receiver, IntentFilter filter)
        Register a BroadcastReceiver to be run in the main activity thread.
        The receiver will be called with any broadcast Intent that matches filter,
        in the main application thread.
         */

        getActivity().registerReceiver(receiver,filter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
