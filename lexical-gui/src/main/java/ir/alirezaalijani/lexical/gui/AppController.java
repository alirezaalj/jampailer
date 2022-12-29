package ir.alirezaalijani.lexical.gui;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import ir.alirezaalijani.lexical.core.LexicalAnalyzer;
import ir.alirezaalijani.lexical.core.LexicalAnalyzerImpl;
import ir.alirezaalijani.share.domain.core.Lexical;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

/**
 * @author Alireza Alijani : <a href="https://alirezaalijani.ir">https://alirezaalijani.ir</a>
 * @email alirezaalijani.ir@gmail.com
 * @date 12/29/2022
 */

public class AppController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<LexicalView> lexicalTable;
    private ObservableList<LexicalView> lexicalViews;

    @FXML
    private TextArea txtFile;
    @FXML
    private TextField txtFilePath;

    @FXML
    void initialize() {
        this.lexicalViews = this.lexicalTable.getItems();
    }

    @FXML
    public void analyze(MouseEvent mouseEvent) {
        if (!this.txtFile.getText().isBlank()){
            doAnalyze(txtFile.getText());
        }
    }

    @FXML
    public void openFile(MouseEvent mouseEvent) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Please Choose java file.","*.java"));
        File selectedFile = fileChooser.showOpenDialog(txtFile.getScene().getWindow());
        if (selectedFile != null) {
            txtFilePath.setText(selectedFile.getAbsolutePath());
            if (selectedFile.exists()){
                try {
                    String txtOfFile= Files.readString(selectedFile.toPath());
                    this.txtFile.setText(txtOfFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    private void doAnalyze(String text){
        LexicalAnalyzer lexicalAnalyzer=new LexicalAnalyzerImpl(text);
        var lexicalResult = lexicalAnalyzer.analyze();
        if (!lexicalResult.isEmpty()){
            lexicalViews.clear();
            lexicalViews.addAll(
                    lexicalResult.stream()
                            .map(this::lexicalViewMap)
                            .collect(Collectors.toList())
            );
        }
    }

    private LexicalView lexicalViewMap(Lexical lexical){
        return new LexicalView(lexical);
    }
}
