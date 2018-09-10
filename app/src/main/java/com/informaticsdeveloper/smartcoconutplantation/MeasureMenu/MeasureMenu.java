package com.informaticsdeveloper.smartcoconutplantation.MeasureMenu;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.informaticsdeveloper.smartcoconutplantation.HistoryMenu.ListAdapter;
import com.informaticsdeveloper.smartcoconutplantation.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Created by Belal on 18/09/16.
 */


public class MeasureMenu extends Fragment {


//    get data by flag exp:
//    pH123#
//            if (pH):
//            123
//            else if (Mo):
//            123
//            ...
//
//    revisi:
//
//    pilihan save ukuran
//    database lokal
//    parsing

    //PARSING GET DATA HARDWARE: pH123#Mo123#Fe123#Li123#

    BluetoothAdapter myBTAdapter;
    SendReceive sendReceive;
    BluetoothDevice[] btArray;
    static final int STATE_LISTENING = 1;
    int DATA_SEND;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String APP_NAME = "PANGANDARAN APP";
    StringBuffer recDataString;
    //    MeasureAdapter adapter;
//    ArrayList<ButtonM> data = new ArrayList<>();
    Button btnShow, btnGetAll;
    ImageButton btnPH, btnMoisture, btnFerlility, btnLight;
    ListView listView;
    TextView tvMessage, tvMessage2, tvMessage3, tvValue, tvCondition;
    TextView tvStatus;
    EditText etPesan;
    Intent btEnablingIntent;
    int REQ_ENABLE_BLUETOOTH = 1;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file

        pref = getActivity().getSharedPreferences("measure", 0);
        return inflater.inflate(R.layout.fragment_menu_3, container, false);
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.main, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Hasil Ukur");
        setHasOptionsMenu(true);

        recDataString = new StringBuffer("");
        tvStatus = view.findViewById(R.id.txtStatus);
        tvCondition = view.findViewById(R.id.tvCondition);
        tvValue = view.findViewById(R.id.tvValue);
        btnShow = view.findViewById(R.id.btnShow);
        btnPH = view.findViewById(R.id.btnPH);
        btnMoisture = view.findViewById(R.id.btnMoisture);
        btnFerlility = view.findViewById(R.id.btnFertility);
        btnLight = view.findViewById(R.id.btnLight);
        btnGetAll = view.findViewById(R.id.btnGetAll);
        etPesan = view.findViewById(R.id.etMessage);
        listView = view.findViewById(R.id.listview);
        tvMessage = view.findViewById(R.id.txtMessage);
        tvMessage2 = view.findViewById(R.id.txtMessage2);
        tvMessage3 = view.findViewById(R.id.txtMessage3);


        myBTAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!myBTAdapter.isEnabled()) {
            Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(btIntent, REQ_ENABLE_BLUETOOTH);
        }

        //bluetoothShow();
        //bluetoothListViewClick();
        bluetoothPHSend();
        bluetoothGetAllSend();
        bluetoothFertilitySend();
        bluetoothLightSend();
        bluetoothMoistureSend();
    }

    private void bluetoothGetAllSend() {
        btnGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String string = "18801279";
//                string += "\r\n";
//                try {
//                    sendReceive.write(string.getBytes());
//                } catch (Exception e) {
//                    Log.e("btoff", String.valueOf(e));
//                    Toast.makeText(getActivity(), "nyalakan bluetooth dahulu", Toast.LENGTH_SHORT).show();
//                }
//
//                DATA_SEND = 5;
                Toast.makeText(getActivity(), "berhasil disimpan", Toast.LENGTH_SHORT).show();
                Log.e("clicked", "sendAllData");
            }
        });
    }

    public void bluetoothPHSend() {

        btnPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "06012741";
                string += "\r\n";
                try {
                    sendReceive.write(string.getBytes());
                } catch (Exception e) {
                    Log.e("btoff", String.valueOf(e));
                    Toast.makeText(getActivity(), "nyalakan bluetooth dahulu", Toast.LENGTH_SHORT).show();
                }

                DATA_SEND = 1;
                Toast.makeText(getActivity(), "mohon tunggu", Toast.LENGTH_SHORT).show();
                Log.e("clicked", "sendPH");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.bt_paired) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getLayoutInflater();
            View convertView = inflater.inflate(R.layout.list_bluetooth, null);
            alertDialog.setView(convertView);
            alertDialog.setTitle("List Bluetooth");
            ListView lv = convertView.findViewById(R.id.listView1);
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strings);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Client client = new Client(btArray[position]);
                        client.start();

                        tvStatus.setText("Connecting");
                    }
                });
                alertDialog.show();
            }

            return true;
        }
        return false;
    }

    public void bluetoothMoistureSend() {
        btnMoisture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "79201274";
                string += "\r\n";
                try {
                    sendReceive.write(string.getBytes());
                } catch (Exception e) {
                    Log.e("btoff", String.valueOf(e));
                    Toast.makeText(getActivity(), "nyalakan bluetooth dahulu", Toast.LENGTH_SHORT).show();
                }
                DATA_SEND = 2;
                Toast.makeText(getActivity(), "mohon tunggu", Toast.LENGTH_SHORT).show();
                Log.e("clicked", "sendMo");
            }
        });
    }

    private void bluetoothFertilitySend() {
        btnFerlility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "43401274";
                string += "\r\n";
                try {
                    sendReceive.write(string.getBytes());
                } catch (Exception e) {
                    Log.e("btoff", String.valueOf(e));
                    Toast.makeText(getActivity(), "nyalakan bluetooth dahulu", Toast.LENGTH_SHORT).show();
                }
                DATA_SEND = 3;
                Toast.makeText(getActivity(), "mohon tunggu", Toast.LENGTH_SHORT).show();
                Log.e("clicked", "sendFe");
            }
        });
    }

    private void bluetoothLightSend() {
        btnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "88501274";
                string += "\r\n";
                try {
                    sendReceive.write(string.getBytes());
                } catch (Exception e) {
                    Log.e("btoff", String.valueOf(e));
                    Toast.makeText(getActivity(), "nyalakan bluetooth dahulu", Toast.LENGTH_SHORT).show();
                }
                DATA_SEND = 4;
                Toast.makeText(getActivity(), "mohon tunggu", Toast.LENGTH_SHORT).show();
                Log.e("clicked", "sendLi");
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
                    recDataString.append(tempMsg);
                    String text = recDataString.toString();
                    String[] lines = text.split("\r\n");
                    String key = "";
                    int count = 1;
                    String kondisi;

                    if (DATA_SEND == 1) {
                        try {
                            Log.e("DATA_SEND_CODE", String.valueOf(DATA_SEND));
                            for (String line : lines) {
                                Log.e("data", "line " + count++ + " : " + line);

                                if ((count % 2) == 0) {
                                    key = line;
                                    Log.e("key", key);
                                }

                                if ((count % 3) == 0) {
                                    double value = Double.valueOf(line);
                                    Log.e("value", String.valueOf(value));
                                    if (value < 650) {
                                        double kalibrasi = (1023 - value) / 99.198;
                                        double result = Math.round(kalibrasi);
                                        editor = pref.edit();
                                        editor.putString("ph", String.valueOf(result));
                                        if (result > 7.5) {
                                            kondisi = "Basa";
                                            editor.putString("kondisi", kondisi);
                                        } else if ((result <= 7.5) && (result > 5.8)) {
                                            kondisi = "Netral";
                                            editor.putString("kondisi", kondisi);
                                        } else if ((result <= 5.7) && (result > 4.2)) { //BIKIN METHOD BUAT PERSINGKAT KODE
                                            kondisi = "Asam";
                                            editor.putString("kondisi", kondisi);
                                        } else {
                                            kondisi = "Terlalu Asam";
                                            editor.putString("kondisi", kondisi);
                                        }
                                        editor.apply();
                                        tvValue.setText(String.valueOf(result));
                                        tvCondition.setText(kondisi);
                                    } else if (value >= 650) {
                                        double kalibrasi = (value) / 99.198;
                                        double result = Math.round(kalibrasi);
                                        editor = pref.edit();
                                        editor.putString("ph", String.valueOf(result));
                                        if (result > 7.5) {
                                            kondisi = "Basa";
                                            editor.putString("kondisi", kondisi);
                                        } else if ((result <= 7.5) && (result > 5.8)) {
                                            kondisi = "Netral";
                                            editor.putString("kondisi", kondisi);
                                        } else if ((result <= 5.7) && (result > 4.2)) { //BIKIN METHOD BUAT PERSINGKAT KODE
                                            kondisi = "Asam";
                                            editor.putString("kondisi", kondisi);
                                        } else {
                                            kondisi = "Terlalu Asam";
                                            editor.putString("kondisi", kondisi);
                                        }
                                        editor.apply();
                                        tvValue.setText(String.valueOf(result));
                                        tvCondition.setText(kondisi);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Log.e("error", String.valueOf(e));
                            Toast.makeText(getActivity(), "mohon tunggu", Toast.LENGTH_SHORT).show();
                        }
                    } else if (DATA_SEND == 2) {
                        try {
                            Log.e("DATA_SEND_CODE", String.valueOf(DATA_SEND));
                            for (String line : lines) {
                                Log.e("data", "line " + count++ + " : " + line);

                                if ((count % 2) == 0) {
                                    key = line;
                                    Log.e("key", key);
                                }

                                if ((count % 3) == 0) {
                                    editor = pref.edit();
                                    double value = Double.valueOf(line);
                                    double kalibrasi = (value) / 102.337;
                                    double result = Math.round(kalibrasi);
                                    editor.putString("mo", String.valueOf(result));
                                    if (result > 7.94) {
                                        kondisi = "Basah";
                                        editor.putString("kondisi", kondisi);
                                    } else if ((result <= 7.93) && (result > 3.84)) {
                                        kondisi = "Ideal";
                                        editor.putString("kondisi", kondisi);
                                    } else {
                                        kondisi = "Kering";
                                        editor.putString("kondisi", kondisi);
                                    }
                                    editor.apply();
                                    tvValue.setText(String.valueOf(result));
                                    tvCondition.setText(kondisi);
                                    Log.e("value", String.valueOf(value));

                                }
                            }
                        } catch (Exception e) {
                            Log.e("error", String.valueOf(e));
                            Toast.makeText(getActivity(), "mohon tunggu", Toast.LENGTH_SHORT).show();
                        }
                    } else if (DATA_SEND == 3) {
                        try {
                            Log.e("DATA_SEND_CODE", String.valueOf(DATA_SEND));
                            for (String line : lines) {
                                Log.e("data", "line " + count++ + " : " + line);

                                if ((count % 2) == 0) {
                                    key = line;
                                    Log.e("key", key);
                                }

                                if ((count % 3) == 0) {
                                    editor = pref.edit();
                                    double value = Double.valueOf(line);
                                    double kalibrasi = (value) / 102.337;
                                    double result = Math.round(kalibrasi);
                                    editor.putString("Fe", String.valueOf(result));
                                    if (result > 7.94) {
                                        kondisi = "Berlebihan";
                                        editor.putString("kondisi", kondisi);
                                    } else if ((result <= 7.93) && (result > 3.84)) {
                                        kondisi = "Ideal";
                                        editor.putString("kondisi", kondisi);
                                    } else {
                                        kondisi = "Terlalu Kecil";
                                        editor.putString("kondisi", kondisi);
                                    }
                                    editor.apply();
                                    tvValue.setText(String.valueOf(result));
                                    tvCondition.setText(kondisi);
                                    Log.e("value", String.valueOf(value));
                                }
                            }
                        } catch (Exception e) {
                            Log.e("error", String.valueOf(e));
                            Toast.makeText(getActivity(), "mohon tunggu", Toast.LENGTH_SHORT).show();
                        }
                    } else if (DATA_SEND == 4) {
                        try {
                            Log.e("DATA_SEND_CODE", String.valueOf(DATA_SEND));
                            for (String line : lines) {
                                Log.e("data", "line " + count++ + " : " + line);

                                if ((count % 2) == 0) {
                                    key = line;
                                    Log.e("key", key);
                                }

                                if ((count % 3) == 0) {
                                    editor = pref.edit();
                                    double value = Double.valueOf(line);
                                    double kalibrasi = (value) / 102.3;
                                    double result = Math.round(kalibrasi);
                                    editor.putString("Li", String.valueOf(result));
                                    if (result > 7.5) {
                                        kondisi = "Cahaya Berlebih";
                                        editor.putString("kondisi", kondisi);
                                    } else if ((result <= 7.5) && (result > 4)) {
                                        kondisi = "Cahaya Cukup";
                                        editor.putString("kondisi", kondisi);
                                    } else {
                                        kondisi = "Cahaya Kurang";
                                        editor.putString("kondisi", kondisi);
                                    }
                                    editor.apply();
                                    tvValue.setText(String.valueOf(result));
                                    tvCondition.setText(kondisi);
                                    Log.e("value", String.valueOf(value));
                                }
                            }
                        } catch (Exception e) {
                            Log.e("error", String.valueOf(e));
                            Toast.makeText(getActivity(), "mohon tunggu", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Log.e("lineslen", String.valueOf(lines.length));
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
//    private void bluetoothShow() {
//        btnShow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Set<BluetoothDevice> bt = myBTAdapter.getBondedDevices();
//                btArray = new BluetoothDevice[bt.size()];
//                String[] strings = new String[bt.size()];
//                int index = 0;
//
//                if (bt.size() > 0) {
//                    for (BluetoothDevice device : bt) {
//                        btArray[index] = device;
//                        strings[index] = device.getName();
//                        index++;
//                    }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strings);
//                    listView.setAdapter(adapter);
//                }
//            }
//        });
//    }
}
