package com.company;


import com.company.Controller.PaneController;
import com.company.Model.GameState;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static javafx.application.Platform.exit;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Map parameters = parseInput(getParameters());
        if (parameters == null){
            exit();
        }
        BorderPane root = new BorderPane();
        FXMLLoader paneLoader = new FXMLLoader(getClass().getResource("pane.fxml"));
        root.setCenter(paneLoader.load());
        PaneController paneController = paneLoader.getController();

        GameState model = new GameState(4,true,8, true, true);
        paneController.initModel(model);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Map parseInput(Parameters parameters) {
        Map resp = new HashMap();
        for (String string: parameters.getRaw()) {
            System.out.print(string);
        }

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
                        File f = new File(par);
                        if(!(f.exists() && !f.isDirectory())) {
                            throw new IllegalArgumentException("Load parameter must be an existing file");
                        }
                        resp.put("load", par);
                        break;
                    default:
                        System.out.println("Dots and Boxes does not take such argument: \""+arg+"\".");
                        return null;
                }
            }catch (Exception e){
                System.out.println("Illegal parameter: \""+par+"\" for argument: \n"+arg+"\". "+ e.getMessage());
                return null;
            }

        }
        return resp;
    }
}
