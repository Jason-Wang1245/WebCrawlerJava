package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.SearchEngine;
import model.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class SearchEngineView extends Pane{
    private TextField searchBar;
    private RadioButton boosted;
    private ListView<String> topSearch;
    private Button searchButton;
    private Text title;
    private SearchEngine model;

    public SearchEngineView(SearchEngine searchEngine){
        model = searchEngine;

        // set title
        title = new Text(177, 35, "NOTGoogle");
        title.setFont(Font.font("Product Sans", 30));

        // instantiate search bar
        searchBar = new TextField();
        searchBar.relocate(30, 50);
        searchBar.setPrefSize(440, 25);

        // instantiate search button
        searchButton = new Button();
        searchButton.setText("Search");
        searchButton.relocate(50, 90);
        searchButton.setPrefSize(300, 25);

        // instantiate radio button
        boosted = new RadioButton("boosted");
        boosted.relocate(390, 95);

        // instantiate top search results ListView
        topSearch = new ListView<String>();
        topSearch.relocate(30, 130);
        topSearch.setPrefSize(440, 300);

        getChildren().addAll(searchBar, searchButton, boosted, topSearch, title);
    }

    public TextField getSearchBar(){
        return searchBar;
    }

    public RadioButton getBoosted(){
        return boosted;
    }

    public ListView<String> getTopSearch(){
        return topSearch;
    }

    public Button getSearchButton(){
        return searchButton;
    }

    public void update(List<SearchResult> searchResults){
        // when a list of search results is provided within the argument, update the topSearch ListView accordingly
        if (searchResults != null){
            String[] searchList = new String[searchResults.size()];
            for (int i = 0; i < searchResults.size(); i++)
                searchList[i] = searchResults.get(i).toString();
            ObservableList<String> list = FXCollections.observableArrayList(searchList);
            topSearch.setItems(list);
        }
        // disables the search and boost buttons radio buttons when the search bar is empty
        searchButton.setDisable(searchBar.getText().length() == 0);
        boosted.setDisable(searchBar.getText().length() == 0);
    }
}
