package app.cryptohotel;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import be.appfoundry.nfclibrary.activities.NfcActivity;

public class MainActivity extends NfcActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView TextView = (TextView) findViewById(R.id.precoTotal);
        final TextView SaldoAtual = (TextView) findViewById(R.id.saldoAtual);
        SaldoAtual.setText("50");

        //Edit Text Methods
        final EditText dias = (EditText) findViewById(R.id.editText4);
        dias.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView.setText(dias.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        for (String message : getNfcMessages()){
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(MainActivity.this, Rent.class);
        startActivity(intent);
    }
}
