package Frontend.FileIO.sample;

public class ViewDataController implements ScreenInterface {

    private ScreensController myController;

    @Override
    public void setScreenParent(ScreensController parent) {
        this.myController = parent;
    }
}
