package com.company;


import com.company.Controller.PaneController;
import com.company.Model.GameState;
import com.company.Model.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static javafx.application.Platform.exit;

public class Main extends Application implements Serializable{

    private static final int HEIGHT = 1000;
    private static final int WIDTH = 1000;
    private static final int MIN_PARAM_COUNT = 5;

    private GameState gameState;
    private Map parameters;
    private Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        parameters = parseInput(getParameters());
        if (parameters == null){
            exit();
            throw new NullPointerException();
        }
        FXMLLoader paneLoader = new FXMLLoader(getClass().getResource("pane.fxml"));
        Parent root = paneLoader.load();
        PaneController paneController = paneLoader.getController();
        paneController.setMain(this);

        if (parameters.containsKey("load")){
            loadFromFile();
        }else{
            restart();
        }


        Scene scene = new Scene(root, WIDTH, HEIGHT);
        if (primaryStage != null)
            this.primaryStage = primaryStage;
        else
            this.primaryStage = new Stage();
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

        paneController.initModel(gameState);

    }

    private Map parseInput(Parameters parameters) {
        Map resp = new HashMap();
        String arg, par;
        for (int i =0; i < parameters.getRaw().size(); i++) {
            arg = parameters.getRaw().get(i);
            if (i < parameters.getRaw().size() - 1){
                par = parameters.getRaw().get(++i);
            }else{
                System.out.println("Last parameter is missing.");
                return null;
            }
            try {
                switch (arg){
                    case "-size":
                        int ans = Integer.parseInt(par);
                        if (ans < 3){
                            throw new IllegalArgumentException("Size integer must be bigger than 2.");
                        }
                        resp.put("size", ans);
                        break;
                    case "-ai":
                        ans = Integer.parseInt(par);
                        if (ans < 0 || ans > 3){
                            throw new IllegalArgumentException("Ai integer must be between 0 and 3.");
                        }
                        resp.put("ai", ans);
                        break;
                    case "-mode":
                        switch (par) {
                            case "time":
                                resp.put("mode", true);
                                break;
                            case "depth":
                                resp.put("mode", false);
                                break;
                            default:
                                throw new IllegalArgumentException("Mode parameter must be time or depth.");
                        }
                        break;
                    case "-param":
                        resp.put("param", Integer.parseInt(par));
                        break;
                    case "-prune":
                        switch (par) {
                            case "on":
                                resp.put("prune", true);
                                break;
                            case "off":
                                resp.put("prune", false);
                                break;
                            default:
                                throw new IllegalArgumentException("Prune parameter must on or off");
                        }
                        break;
                    case "-load":
                        resp.put("load", par);
                        break;
                    default:
                        System.out.println("Dots and Boxes does not take such argument: \""+arg+"\".");
                        return null;
                }
            }catch (Exception e){
                System.out.println("Illegal parameter: \""+par+"\" for argument: \""+arg+"\". "+ e.getMessage());
                return null;
            }

        }
        if (resp.size() < MIN_PARAM_COUNT) {
            System.out.println("Some parameters are missing, please see the README file in the project folder.");
            return null;
        }
        return resp;
    }

    public GameState restart(){
        gameState = new GameState(
                (int)parameters.get("size"),
                (int)parameters.get("ai"),
                (int)parameters.get("param"),
                (boolean)parameters.get("mode"),
                (boolean)parameters.get("prune"));
        return gameState;
    }

    private void loadFromFile() {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream((String) parameters.get("load"));
            objectInputStream = new ObjectInputStream(fileInputStream);
            Main oldMe = (Main) objectInputStream.readObject();
            gameState = oldMe.gameState;
            parameters = oldMe.parameters;
            primaryStage = oldMe.primaryStage;
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Error loading from file. The game will be started from scratch.");
            restart();
        }
    }

    private File fileRequestDialog(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save File");
        chooser.setInitialFileName("Dont-and-Crosses");
        return chooser.showSaveDialog(new Stage());
    }

    private void writeToFile(Object o, File file){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(o);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("File was not saved");
        }
    }

    public void save(){
        File file = fileRequestDialog();
        writeToFile(this, file);
    }

    private void writeObject(final ObjectOutputStream out) throws IOException {
        out.writeObject(gameState);
        out.writeObject(parameters);
    }

    private void readObject(final ObjectInputStream ois) throws IOException, ClassNotFoundException {
        gameState = (GameState) ois.readObject();
        parameters = (Map) ois.readObject();
    }

    public void dotFileRequest() {
        File file = fileRequestDialog();
        //writeToFile(gameState.getDot(), file);
    }
}
