package imageviewer.architecture;

public interface Image {
    Object data();
    int width();
    Image prev();
    Image next();
    String name();
    
}
