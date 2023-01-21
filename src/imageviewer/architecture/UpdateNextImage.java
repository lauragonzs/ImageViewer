package imageviewer.architecture;

public class UpdateNextImage implements ImageUpdate {
    private final ImagePresenter imagePresenter;
    
    public UpdateNextImage(ImagePresenter imagePresenter) {
        this.imagePresenter = imagePresenter;
    }
    
    @Override
    public void exec() {
        imagePresenter.show(imagePresenter.currentImage().next());
    }
}
