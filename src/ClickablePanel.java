import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class ClickablePanel extends JPanel {

    private List<GameObject> objects = new ArrayList<>();
    private Image image;  // Image for the game object
    private int imageWidth = 80;
    private int imageHeight = 80;

    public ClickablePanel() {
        try {
            image = ImageIO.read(new java.net.URL("https://i.pinimg.com/originals/d5/09/cd/d509cda0558a668af9c375d8594c7a6b.png")); // **REPLACE THIS!**
        } catch (IOException e) {
            e.printStackTrace();
            image = null;
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    // Create a new game object at the mouse click location
                    objects.add(new GameObject(e.getX() - imageWidth / 2, e.getY() - imageHeight / 2, image, imageWidth, imageHeight)); // Center the image
                    repaint(); // Request a redraw of the panel
                }
            }
        });
        setPreferredSize(new Dimension(500,500));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (GameObject obj : objects) {
            obj.draw(g);
        }
    }

    private class GameObject {
        int x, y;
        Image image;
        int width, height;

        public GameObject(int x, int y, Image image, int width, int height) {
            this.x = x;
            this.y = y;
            this.image = image;
            this.width = width;
            this.height = height;
        }

        public void draw(Graphics g) {
            if (image != null) {
                g.drawImage(image, x, y, width, height, null);
            } else {
                g.setColor(Color.RED);
                g.fillRect(x, y, width, height);  // Draw a red rectangle if image is missing
            }

        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clickable Panel");
        ClickablePanel panel = new ClickablePanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}