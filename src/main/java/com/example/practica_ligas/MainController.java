package com.example.practica_ligas;

import com.example.practica_ligas.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, EventHandler<ActionEvent> {

    @FXML
    private MenuItem menuDetalle;

    @FXML
    private ComboBox<Temporadas> comboTemporada;

    @FXML
    private Button botonVer;

    @FXML
    private ListView<Clasificacion> listView;

    @FXML
    private ComboBox<Ligas> comboLiga;
    @FXML
    private ObservableList<Ligas> listaLigas= FXCollections.observableArrayList();
    @FXML
    private ObservableList<Temporadas> listaTemporadas = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Clasificacion> listaClasificacion = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acciones();
        instancias();
        metodoComboLigas();
        comboLiga.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                String idLiga = newValue.getIdLeague();
                metodoComboTemporadas(idLiga);
            }
        });
    }

    public void metodoComboLigas(){
        String urlString = "https://www.thesportsdb.com/api/v1/json/3/all_leagues.php";
        try {
            URL urlMovie = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) urlMovie.openConnection();
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(connection.getInputStream()));

            String linea = null;
            StringBuffer stringBuffer = new StringBuffer();
            while ((linea = reader.readLine()) != null) {
                stringBuffer.append(linea);
            }

            JSONObject response = new JSONObject(stringBuffer.toString());
            JSONArray results = response.getJSONArray("leagues");
            for (int i = 0; i < results.length(); i++) {
                JSONObject ligasJson = results.getJSONObject(i);
                String strLeague = ligasJson.getString("strLeague");
                String idLeague = ligasJson.getString("idLeague");
                Ligas ligas = new Ligas(strLeague, idLeague);

                listaLigas.add(ligas);
            }

            comboLiga.setItems(listaLigas);




        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void metodoComboTemporadas(String idLiga){
        String urlString = "https://www.thesportsdb.com/api/v1/json/3/search_all_seasons.php?id="+ idLiga;
        listaTemporadas.clear();
        try {
            URL urlMovie = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) urlMovie.openConnection();
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(connection.getInputStream()));

            String linea = null;
            StringBuffer stringBuffer = new StringBuffer();
            while ((linea = reader.readLine()) != null) {
                stringBuffer.append(linea);
            }

            JSONObject response = new JSONObject(stringBuffer.toString());
            JSONArray results = response.getJSONArray("seasons");
            for (int i = 0; i < results.length(); i++) {
                JSONObject temporadasJson = results.getJSONObject(i);
                String strSeason = temporadasJson.getString("strSeason");
                Temporadas temporadas = new Temporadas(strSeason);

                listaTemporadas.add(temporadas);
            }

            comboTemporada.setItems(listaTemporadas);




        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void acciones(){
        botonVer.setOnAction(this);
        menuDetalle.setOnAction(this);

    }

    private void instancias(){

    }


    @Override
    public void handle(ActionEvent actionEvent) {
        if (actionEvent.getSource() == botonVer) {
            Ligas ligaSeleccionada = comboLiga.getSelectionModel().getSelectedItem();
            Temporadas temporadaSeleccionada = comboTemporada.getSelectionModel().getSelectedItem();

            if (ligaSeleccionada != null && temporadaSeleccionada != null) {
                String idLiga = ligaSeleccionada.getIdLeague();
                String temporada = temporadaSeleccionada.getStrSeason();
                String urlString = "https://www.thesportsdb.com/api/v1/json/3/lookuptable.php?l=" + idLiga + "&s=" + temporada;

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String linea;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((linea = reader.readLine()) != null) {
                        stringBuilder.append(linea);
                    }

                    JSONObject response = new JSONObject(stringBuilder.toString());
                    JSONArray results = response.getJSONArray("table");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject clasificacionJson = results.getJSONObject(i);
                        String strTeam = clasificacionJson.getString("strTeam");
                        String strTeamBadge = clasificacionJson.getString("strTeamBadge");
                        String strForm = clasificacionJson.getString("strForm");
                        int intPoints = clasificacionJson.getInt("intPoints");
                        int intGoalsFor = clasificacionJson.getInt("intGoalsFor");
                        int intGoalsAgainst = clasificacionJson.getInt("intGoalsAgainst");


                        Clasificacion clasificacion = new Clasificacion(strTeam, intPoints, intGoalsFor, intGoalsAgainst, strTeamBadge, strForm);
                        listaClasificacion.add(clasificacion);
                    }

                    listView.setItems(listaClasificacion);

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Selección Incompleta");
                alerta.setHeaderText(null);
                alerta.setContentText("Por favor, selecciona una liga y una temporada.");
                alerta.showAndWait();
            }
        } else if (actionEvent.getSource() == menuDetalle) {
            Clasificacion equipoSeleccionado = listView.getSelectionModel().getSelectedItem();
            if (equipoSeleccionado != null) {
                Alert detallesAlerta = new Alert(Alert.AlertType.INFORMATION);
                detallesAlerta.setTitle("Detalles del Equipo");
                detallesAlerta.setHeaderText(equipoSeleccionado.getStrTeam()); // Nombre del equipo

                StringBuilder contenido = new StringBuilder();
                contenido.append("Puntos: ").append(equipoSeleccionado.getIntPoints()).append("\n");
                contenido.append("Forma: ").append(equipoSeleccionado.getStrForm()).append("\n");

                ImageView escudo = new ImageView();
                escudo.setFitHeight(100);
                escudo.setFitWidth(100);
                String urlEscudo = equipoSeleccionado.getStrTeamBadge();
                if (urlEscudo != null && !urlEscudo.isEmpty()) {
                    escudo.setImage(new Image(urlEscudo, true));
                    detallesAlerta.setGraphic(escudo);
                }

                detallesAlerta.setContentText(contenido.toString());
                detallesAlerta.showAndWait();
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Ningún Equipo Seleccionado");
                alerta.setHeaderText(null);
                alerta.setContentText("Por favor, selecciona un equipo de la lista.");
                alerta.showAndWait();
            }
        }


    }



}