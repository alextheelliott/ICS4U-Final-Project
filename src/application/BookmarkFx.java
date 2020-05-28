package application;

import buttons.Buttons.ImageCondition;
import buttons.DropdownButton;

import static application.Constants.LayoutConstants.*;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import javafx.geometry.Insets;
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

    /*
     * Constructor the takes a bookmark object and the location for the object.
     * Creats a hbox for the dropdown, title, and delete button then adds that 
     * hbox to a vbox with the webview.
     */
    public BookmarkFx(Bookmark bm, double x, double y) {
        vbox = new VBox();
        vbox.setPadding(new Insets(0,0,0,10));
        vbox.setLayoutX(x);
        vbox.setLayoutY(y);
        vbox.setPrefWidth(250);
        vbox.setMaxHeight(MENU_BUTTON_SIZE/3);
        hbox = new HBox();

        WebView wv = new WebView();
        wv.getEngine().load(bm.getUrl());
        wv.setZoom(0.25);
        wv.autosize();

        Text t = new Text(bm.getTitle()+"  ");
        /* 
         * When clicked on the title the url is retrieved from the bookmark object and added
         * to the users clipboard. The clipboard is gotten from toolkit. The copied text is
         * also shown to notifty the user.
         */
        // https://stackoverflow.com/questions/6710350/copying-text-to-the-clipboard-using-java
        t.setOnMouseClicked(event -> {
            StringSelection stringSelection = new StringSelection(wv.getEngine().getLocation());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            SceneFX.copied.setOpacity(1.0);
        });
        t.setFont(Font.font(16));
		t.setFill(Color.WHITE);

        // Creates a button to delete the bookmark from the csv.
        Button b = new Button();
        b.setPrefSize(20, 20);
        b.setText("❌");
        b.setStyle("-fx-font-size: 6");
		b.setOnAction(event -> {
            Bookmark.removeBookmark(bm);
            SceneFX.loadBookmarks();
        });

        // Everything is added to the hbox...
        hbox.getChildren().addAll(
            new DropdownButton(
                MENU_BUTTON_SIZE/3,
                0.0, 0.0,
                new Image(DROPDOWN_OPEN, false), 
                new Image(DROPDOWN_CLOSED, false),
                ImageCondition.STATEDEPENDENT,
                wv, false
            ).getNode(),
            t,
            b
        );

        // then the vbox.
        vbox.getChildren().addAll(
            hbox,
            wv
        );

    }

    //Return the vbox so the object can be rendered.
    public Node getNode() {
        return vbox;
    }

}