package imageviewer.architecture;


public interface ImageDisplay {
    
    int width();
    void clear();
    void paint(Object data, int offset, double ratio);
    void onDragged(DragEvent event);
    void onReleased(NotifyEvent event);

    
    
    public interface NotifyEvent {
        public static void Null(int offset) {};
        void handle(int offset);
    }
    
    public interface DragEvent {
        public static void Null(int offset) {};
        void handle(int offset);
    }
    
}
