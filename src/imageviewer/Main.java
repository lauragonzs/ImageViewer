package imageviewer;

import imageviewer.architecture.ImageDisplay;
import imageviewer.architecture.ImagePresenter;
import imageviewer.architecture.UpdateNextImage;
import imageviewer.architecture.UpdatePrevImage;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        ImageLoaderFromFile loader = new ImageLoaderFromFile(new File("images"));  
        MainFrame mainFrame = new MainFrame();        
        ImageDisplay imageDisplay = mainFrame.imageDisplay();
        ImagePresenter imagePresenter = ImagePresenter.with(loader.load(), imageDisplay); 
        mainFrame.addImageUpdate("prev", new UpdatePrevImage(imagePresenter));
        mainFrame.addImageUpdate("next", new UpdateNextImage(imagePresenter));
        
        mainFrame.setVisible(true);
    }
    
}
