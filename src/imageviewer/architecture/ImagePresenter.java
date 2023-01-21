package imageviewer.architecture;

public class ImagePresenter {
    private Image image;
    private final ImageDisplay display;


    private ImagePresenter(Image image, ImageDisplay display) {
        this.image = image;
        this.display = display;
        this.display.onDragged(this::onDragged);
        this.display.onReleased(this::onReleased);
        refresh();
    }
    
    public static ImagePresenter with(Image image, ImageDisplay imageDisplay) {
        return new ImagePresenter(image, imageDisplay);
    } 
    
    public void show(Image image) {
        this.image = image;
        refresh();
    }
    
    private void onDragged(int offset) {
        display.clear();
        display.paint(image.data(), offset, 1);
        int newOffset;
        if (offset < 0) {
            Image next = image.next();
            if(display.width() > next.width()) newOffset = (next.width() + display.width())/2 + offset;
            else newOffset = display.width() + offset;
            display.paint(next.data(), newOffset, 1);
        }
        else {
            Image prev = image.prev();
            if(display.width() > prev.width()) newOffset = offset - (prev.width() + display.width())/2;
            else newOffset = offset - display.width();
            display.paint(prev.data(), newOffset, 1);
        }
    }
    
    private void onReleased(int offset) {
        if (Math.abs(offset) > display.width() / 2) {
            this.image = offset < 0 ? image.next() : image.prev();
        }
        refresh();
    }
    
    private void refresh() {
        display.clear();
        display.paint(image.data(), 0, 1);
    }
    
    
    public Image currentImage() {
        return image;
    }
    
}
