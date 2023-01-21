package imageviewer.architecture;

public class UpdatePrevImage implements ImageUpdate {
    private final ImagePresenter imagePresenter;
    
    public UpdatePrevImage(ImagePresenter imagePresenter) {
        this.imagePresenter = imagePresenter;
    }
    
    @Override
    public void exec() {
        imagePresenter.show(imagePresenter.currentImage().prev());
    }
}
