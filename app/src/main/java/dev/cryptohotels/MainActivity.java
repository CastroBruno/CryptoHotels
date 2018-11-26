package dev.cryptohotels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.bluetooth.*;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.button);
        final int REQUEST_ENABLE_BT = 1337;

        //Adaptador Bluetooth
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this,"Erro! O dispositivo não possui conexão bluetooth.",Toast.LENGTH_SHORT).show();

        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //ConnectThread thread = new ConnectThread(mBluetoothAdapter.listenUsingRfcommWithServiceRecord("Door", UUID.fromString("91b085a1-4179-40c5-8766-b894bca9ffa9")));
                ConnectThread thread = new ConnectThread(mBluetoothAdapter.getRemoteDevice("213"));
                thread.run();
                thread.write("Open".getBytes());
                thread.cancel();
            }
        });
    }
}
