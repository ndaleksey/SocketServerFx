package server.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller {
    private static final int PORT = 4321;

    @FXML
    private Button startServerButton;

    @FXML
    private ListView<String> logsListView;

    @FXML
    protected void initialize() {
        startServerButton.setDefaultButton(true);
        startServerButton.setOnAction(e -> startServerClick());
    }

    private void startServerClick() {
        try {
            startServerButton.setText("Стоп");

            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket clientSocket = serverSocket.accept();

            logsListView.getItems().add("Подключился новый клиент");
            logsListView.getItems().add("");

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            String text;

            while ((text = in.readUTF()) != null)
                logsListView.getItems().add(text);

            if (clientSocket.isClosed()) {
                logsListView.getItems().add("");
                logsListView.getItems().add("Клиент отключился");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
