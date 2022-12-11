package gui;

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
        // call handleSearch when search button is clicked
        view.getSearchButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleSearch();
            }
        });
        // update the view when user finishes typing in search bar
        view.getSearchBar().setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                view.update(null);
            }
        });

        stage.setTitle("NOTGoogle");
        stage.setScene(new Scene(view, 500, 460));
        stage.show();

        view.update(null);
    }
    // private method for handling search
    private void handleSearch(){
        String query = view.getSearchBar().getText();
        boolean boosted = view.getBoosted().isSelected();
        // if the user typed something within the search bar when the search button is clicked, call the views update method with the list of search results
        if (query.length() > 0){
            view.getSearchBar().setText("");
            view.update(model.search(query, boosted, 10));
        } else {
            view.update(null);
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
