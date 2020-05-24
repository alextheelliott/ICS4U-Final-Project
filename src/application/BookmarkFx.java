package application;

import buttons.Buttons.ImageCondition;
import buttons.Buttons;
import buttons.DropdownButton;

import static application.Constants.LayoutConstants.*;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class BookmarkFx {
    
    private VBox vbox;
    private HBox hbox; 

    public BookmarkFx(Bookmark bm, double x, double y) {
        vbox = new VBox();
        vbox.setLayoutX(x);
        vbox.setLayoutY(y);
        vbox.setPrefWidth(250);
        vbox.setMaxHeight(MENU_BUTTON_SIZE/3);
        hbox = new HBox();

        WebView wv = new WebView();
        wv.getEngine().load(bm.getUrl());
        wv.setZoom(0.25);
        wv.autosize();

        Text t = new Text(bm.getTitle());
        //https://stackoverflow.com/questions/6710350/copying-text-to-the-clipboard-using-java
        t.setOnMouseClicked(event -> {
            StringSelection stringSelection = new StringSelection(wv.getEngine().getLocation());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            SceneFX.copied.setOpacity(1.0);
        });
        t.setFont(Font.font(16));
		t.setFill(Color.WHITE);

        hbox.getChildren().addAll(
            new DropdownButton(
                MENU_BUTTON_SIZE/3,
                0.0, 0.0,
                new Image(DROPDOWN_OPEN, false), 
                new Image(DROPDOWN_CLOSED, false),
                ImageCondition.STATEDEPENDENT,
                wv, false
            ).getNode(),
            t
        );

        vbox.getChildren().addAll(
            hbox,
            wv
        );

		Button b = new Button();
		b.setLayoutX(0);
		b.setLayoutY(0);
		b.setPrefSize(30, 30);
		b.setText("❌");
		b.setOnAction(event -> {
            Bookmark.removeBookmark(bm);
            SceneFX.loadBookmarks();
        });
        
        vbox.getChildren().addAll(b);
    }

    public Node getNode() {
        return vbox;
    }

}