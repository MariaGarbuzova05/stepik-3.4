import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class ClickablePanel2 extends JPanel {

    private List<GameObject> objects = new ArrayList<>();
    private Image image;
    private int imageWidth = 50;
    private int imageHeight = 50;

    public ClickablePanel2() {
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
                    objects.add(new GameObject(e.getX() - imageWidth / 2, e.getY() - imageHeight / 2, image, imageWidth, imageHeight));
                    repaint();
                } else if (SwingUtilities.isMiddleMouseButton(e)) {
                    // Check for middle mouse button click (wheel click)
                    for (int i = objects.size() - 1; i >= 0; i--) {
                        GameObject obj = objects.get(i);
                        if (e.getX() >= obj.x && e.getX() <= obj.x + obj.width &&
                                e.getY() >= obj.y && e.getY() <= obj.y + obj.height) {
                            objects.remove(i); // Remove the object
                            repaint(); // Request a redraw
                            break; // Exit the loop after removing an object
                        }
                    }
                }
            }
        });
        setPreferredSize(new Dimension(500, 500));
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
                g.fillRect(x, y, width, height);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clickable Panel");
        ClickablePanel2 panel = new ClickablePanel2();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}