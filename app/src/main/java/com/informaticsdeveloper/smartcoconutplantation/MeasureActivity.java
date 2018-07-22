package com.informaticsdeveloper.smartcoconutplantation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import net.simplifiedcoding.navigationdrawerexample.HomeMenu;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MeasureActivity extends AppCompatActivity {

    BluetoothAdapter myBTAdapter;
    SendReceive sendReceive;
    BluetoothDevice[] btArray;
    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String APP_NAME = "PANGANDARAN APP";

    Button btnShow;
    Button btnSend;
    ListView listView;
    TextView tvMessage;
    TextView tvStatus;
    EditText etPesan;
    Intent btEnablingIntent;
    int REQ_ENABLE_BLUETOOTH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        init();

        myBTAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!myBTAdapter.isEnabled()) {
            Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(btIntent, REQ_ENABLE_BLUETOOTH);
        }

        bluetoothShow();
        bluetoothListViewClick();
        bluetoothSend();
    }

    private void bluetoothSend() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = etPesan.getText().toString();
                sendReceive.write(string.getBytes());
            }
        });
    }

    private void bluetoothListViewClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client = new Client(btArray[position]);
                client.start();

                tvStatus.setText("Connecting");
            }
        });
    }

    private class SendReceive extends Thread {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive(BluetoothSocket socket) {
            bluetoothSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = bluetoothSocket.getInputStream();
                tmpOut = bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream = tmpIn;
            outputStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = inputStream.read(buffer);
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case STATE_LISTENING:
                    tvStatus.setText("Listening");
                    break;
                case STATE_CONNECTING:
                    tvStatus.setText("Connecting");
                    break;
                case STATE_CONNECTED:
                    tvStatus.setText("Connected");
                    break;
                case STATE_CONNECTION_FAILED:
                    tvStatus.setText("Connection Failed");
                    break;
                case STATE_MESSAGE_RECEIVED:
                    byte[] readBuffer = (byte[]) msg.obj;
                    String tempMsg = null;
                    try {
                        tempMsg = new String(readBuffer, 0, msg.arg1, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String message = String.valueOf(msg.arg1) +
                            " bytes received:\n" + String.valueOf(readBuffer) + "\n"
                            + tempMsg;
                    tvMessage.setText(message);
                    break;
            }

            return true;
        }
    });

    private class Server extends Thread {
        private BluetoothServerSocket serverSocket;

        public Server() {
            try {
                serverSocket = myBTAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            BluetoothSocket socket = null;

            while (socket == null) {
                try {
                    Message msg = Message.obtain();
                    msg.what = STATE_CONNECTING;
                    handler.sendMessage(msg);
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = STATE_CONNECTION_FAILED;
                    handler.sendMessage(msg);
                }

                if (socket != null) {
                    Message msg = Message.obtain();
                    msg.what = STATE_CONNECTED;
                    handler.sendMessage(msg);

                    sendReceive = new SendReceive(socket);
                    sendReceive.start();
                    break;
                }
            }
        }
    }

    private class Client extends Thread {
        private BluetoothDevice device;
        private BluetoothSocket socket;

        public Client(BluetoothDevice device1) {
            device = device1;
            try {
                socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                socket.connect();
                Message msg = Message.obtain();
                msg.what = STATE_CONNECTED;
                handler.sendMessage(msg);

                sendReceive = new SendReceive(socket);
                sendReceive.start();
            } catch (IOException e) {
                e.printStackTrace();
                Message msg = Message.obtain();
                msg.what = STATE_CONNECTION_FAILED;
                handler.sendMessage(msg);
            }
        }
    }

    private void init() {
        tvStatus = findViewById(R.id.txtStatus);
        btnShow = findViewById(R.id.btnShow);
        btnSend = findViewById(R.id.btnSend);
        etPesan = findViewById(R.id.etMessage);
        listView = findViewById(R.id.listview);
        tvMessage = findViewById(R.id.txtMessage);
    }

    private void bluetoothShow() {
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<BluetoothDevice> bt = myBTAdapter.getBondedDevices();
                btArray = new BluetoothDevice[bt.size()];
                String[] strings = new String[bt.size()];
                int index = 0;

                if (bt.size() > 0) {
                    for (BluetoothDevice device : bt) {
                        btArray[index] = device;
                        strings[index] = device.getName();
                        index++;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HomeMenu.class);
        startActivity(intent);
    }
}