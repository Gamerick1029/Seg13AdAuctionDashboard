package Frontend.sample;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class Campaign {

    private String name;
    private CheckBox displayed;
    private Button remove;

    public Campaign(String name) {
        this.name = name;
        this.displayed = new CheckBox();
        displayed.setSelected(true);
        this.remove = new Button("✖");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CheckBox getDisplayed() {
        return displayed;
    }

    public void setDisplayed(CheckBox displayed) {
        this.displayed = displayed;
    }

    public Button getRemove() {
        return remove;
    }

    public void setRemove(Button remove) {
        this.remove = remove;
    }
}
