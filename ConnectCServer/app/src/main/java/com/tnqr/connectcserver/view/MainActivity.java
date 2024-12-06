package com.tnqr.connectcserver.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tnqr.connectcserver.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String SERVER_IP = "192.168.15.232"; // Sunucunun IP adresi
    private static final int SERVER_PORT = 8081;               // Sunucunun portu

    private static MainActivity instance;  // Singleton örneği

    private ActivityMainBinding binding;
    private Socket socket;
    private PrintWriter clientOut;
    private BufferedReader clientIn;
    private ArrayList<String> arrayList = new ArrayList<>();
    private boolean isConnected = false;

    // tek bir örnek oluşturup bu örneğe erişim sağlıyoruz
    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this; // singelton ile burada örneğe işliyoruz

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createClient();

    }

    private void createClient() {
        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                clientOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                clientIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                isConnected = true;
                System.out.println("Sunucuya bağlandı.");
            } catch (IOException e) {
                System.out.println("Sunucuya bağlanırken hata oluştu.");
                e.printStackTrace();
            }
        }).start();
    }

    public void sendCommandToServer(String command) {
        new Thread(() -> {
            try {

                clientOut.println(command);
                String response = clientIn.readLine();
                System.out.println("response:");
                runOnUiThread(() -> handleServerResponse(response,command));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleServerResponse(String message,String command) {
        System.out.println("message:" + message);

        if (command.startsWith("R"))// masa rezerv etme komutu alındı
        {
            if(message.equals("True"))
                Toast.makeText(MainActivity.this, "Masa Rezerv Edildi", Toast.LENGTH_SHORT).show();
            else if (message.equals("False"))
                Toast.makeText(MainActivity.this, "Masa Rezerv Edilemedi", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();

        }
        else if(command.equals("CHECK")){ // masa check etme komutu alındı

            handleTableStatus_goToLibrary(message); // masa durum bilgilerini al ve parçalayarak gönder
            Intent intent = new Intent(getApplicationContext(), LibraryActivity.class);
            intent.putStringArrayListExtra("table_status", arrayList); //arrayList ile parçalanan diziyi tut ve diğer aktiviteye gönder
            startActivity(intent);// diğer sayfa geçişi
        }
        else if(command.startsWith("L")){ // masa bırakılma komutu alındı
            if(message.equals("True"))
                Toast.makeText(MainActivity.this, "Masa Terk Edildi", Toast.LENGTH_SHORT).show();
            else if (message.equals("False"))
                Toast.makeText(MainActivity.this, "Masa Terk Edildi", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }
        else{
            System.out.println("Geçersiz komut!!");
        }
    }

    private void handleTableStatus_goToLibrary(String tableStatuses) {
        if (tableStatuses != null && !tableStatuses.isEmpty()) {
            String[] tablesStatus = tableStatuses.split(" ");
            arrayList.clear();
            for (String status : tablesStatus) {
                arrayList.add(status);
            }
        } else {
            System.out.println("Masa durumları alınamadı.");
        }
    }

    public void enterButton(View view) {
        if (isConnected) {
            sendCommandToServer("CHECK");
        } else {
            Toast.makeText(this, "Bağlantı kurulamadı.", Toast.LENGTH_SHORT).show();
        }
    }
}
