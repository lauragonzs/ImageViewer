package imageviewer;

import imageviewer.architecture.ImageDisplay;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import imageviewer.architecture.ImageUpdate;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

public class MainFrame extends JFrame {

    private final ImagePanel imagePanel;
    private final Map<String,ImageUpdate> iuMap;

    public MainFrame() {
        this.setTitle("Image Viewer");
        
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout()); //xd
        
        this.add(imagePanel = new ImagePanel());
        this.add(toolbar(), BorderLayout.SOUTH);
        
        iuMap = new HashMap();
    }
    
    public void addImageUpdate(String key, ImageUpdate iu) {
        iuMap.put(key, iu);
    }
    
    public ImageDisplay imageDisplay() {
        return imagePanel;
    }
    
    private Component toolbar() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(button("<", "prev"));
        panel.add(button(">", "next"));
        return panel;
    }
    
    private Component button(String label, String command) {
        final JButton button = new JButton(label);
        button.setFont(new Font("Dialog", 1, 18));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                iuMap.get(command).exec();
            }
        });
        return button;
    }

    
    private class ImagePanel extends JPanel implements ImageDisplay {
        
        private final List<Order> orders;
        private DragEvent onDragged = DragEvent::Null;
        private NotifyEvent onReleased = NotifyEvent::Null;
        private int x;

        private ImagePanel() {
            this.orders = new ArrayList<>();
            this.addMouseListener(new MouseListener() {
                
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    x = e.getX();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    onReleased.handle(e.getX()-x);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            this.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    onDragged.handle(e.getX()-x);
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                }
            });
        }

        @Override
        public void paint(Graphics g) {
            clean(g);
            for (Order order : orders){
                order.calculate(this.getWidth(), this.getHeight());
                g.drawImage(order.image, order.x(this.getWidth()), order.y(this.getHeight()), order.width, order.height, null);                
            }
        }

        private void clean(Graphics g) {
            g.setColor(Color.white);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        @Override
        public void clear() {
            orders.clear();
        }
        
        @Override
        public void paint(Object data, int offset, double ratio) {
            orders.add(new Order(data, offset));
            repaint();
        }

        @Override
        public void onDragged(DragEvent event) {
            this.onDragged = event;
        }

        @Override
        public void onReleased(NotifyEvent event) {
            this.onReleased = event;
        }

        @Override
        public int width() {
            return this.getWidth();
        }
        
    }

    private static class Order {
        public final BufferedImage image;
        public final int offset;
        public  int width;
        public  int height;
        private double ratio;
        
        public Order(Object data, int offset)  {
            this.image = (BufferedImage) data;
            this.offset = offset;
            this.width = image.getWidth();
            this.height = image.getHeight();
            this.ratio = this.width / this.height;
        }
        
        public void calculate(int panelWidth, int panelHeight) {
            this.width = image.getWidth();
            this.height = image.getHeight();
            if(this.height > panelHeight) {
                this.height = panelHeight;
                this.width = (int) (this.height * this.ratio);
            } 
            if(this.width > panelWidth) {
                this.width = panelWidth;
                this.height = (int) (this.width / this.ratio);
            }
        }
        
        public int x(int width) {
            return (width - this.width) / 2 + offset;
        }
        
        public int y(int height) {
            return (height - this.height) / 2;
        }
        
    }
    
    
}
