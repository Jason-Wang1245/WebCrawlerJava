package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.input.*;
import model.SearchEngine;

public class SearchEngineApp extends Application {
    private SearchEngine model;
    private SearchEngineView view;

    public void start(Stage stage){
        model = new SearchEngine();
        view = new SearchEngineView(model);

        view.getSearchButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleSearch();
            }
        });

        view.getSearchBar().setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                view.update();
            }
        });

        stage.setTitle("NOTGoogle");
        stage.setScene(new Scene(view, 500, 460));
        stage.show();

        view.update();
    }

    private void handleSearch(){
        String query = view.getSearchBar().getText();
        boolean boosted = view.getBoosted().isSelected();
        if (query.length() > 0){
            model.search(query, boosted, 10);
            view.getSearchBar().setText("");
        }
        view.update();
    }

    public static void main(String[] args){
        launch(args);
    }
}
