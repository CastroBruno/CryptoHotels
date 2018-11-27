package app.cryptohotel;

import android.app.Activity;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.nio.charset.Charset;

import be.appfoundry.nfclibrary.activities.NfcActivity;
import be.appfoundry.nfclibrary.utilities.interfaces.NfcMessageUtility;
import be.appfoundry.nfclibrary.utilities.sync.NfcMessageUtilityImpl;
import static android.nfc.NdefRecord.createMime;

public class Rent extends NfcActivity {

    private static final int BEAM_BEAMED = 0x1001;
    public static final String MIMETYPE = "app.cryptohotel";
    private String messageToBeam = "Room001";
    private String mStatusText = "Normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        NdefMessage message = new NfcMessageUtilityImpl().createText("Room001");


        //getNfcAdapter().setNdefPushMessage(message,this,this);
        //getNfcAdapter().setNdefPushMessageCallback(this,this);
        //getNfcAdapter().setOnNdefPushCompleteCallback((NfcAdapter.OnNdefPushCompleteCallback) this,this);
        //enableBeam();

        getNfcAdapter().setNdefPushMessageCallback(new NfcAdapter.CreateNdefMessageCallback() {
            @Override
            public NdefMessage createNdefMessage(NfcEvent event) {
                //the variable message is from the EditText field
                String message = messageToBeam;
                String text = (message);
                byte[] mime = MIMETYPE.getBytes(Charset.forName("US-ASCII"));
                NdefRecord mimeMessage = new NdefRecord(
                        NdefRecord.TNF_MIME_MEDIA, mime, new byte[0], text
                        .getBytes());
                NdefMessage msg = new NdefMessage(
                        new NdefRecord[]{
                                mimeMessage,
                                NdefRecord
                                        .createApplicationRecord("app.cryptohotel")});
                return msg;
            }
        }, this);

        getNfcAdapter().setOnNdefPushCompleteCallback(
                new NfcAdapter.OnNdefPushCompleteCallback() {

                    @Override
                    public void onNdefPushComplete(NfcEvent event) {
                        mHandler.obtainMessage(BEAM_BEAMED).sendToTarget();
                    }
                }, this);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case BEAM_BEAMED:
                    mStatusText="Your message has been beamed";
                    break;
            }
        }
    };
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Toast.makeText(this,"New Intent",Toast.LENGTH_SHORT).show();
    }


    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return (new NfcMessageUtilityImpl()).createText("Teste001");
    }
}
