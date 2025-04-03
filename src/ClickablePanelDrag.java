import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class ClickablePanelDrag extends JPanel {

    private List<GameObject> objects = new ArrayList<>();
    private Image image;
    private int imageWidth = 50;
    private int imageHeight = 50;
    private GameObject selectedObject = null; // The object being dragged
    private int dragOffsetX, dragOffsetY; // Offset from mouse to object's origin

    public ClickablePanelDrag() {
        try {
            image = ImageIO.read(new java.net.URL("https://avatars.mds.yandex.net/i?id=3a16c6a0b5ffb0512bdcfe7fe679e97227168fcf-11932091-images-thumbs&n=13")); // **REPLACE THIS!**
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
                    for (int i = objects.size() - 1; i >= 0; i--) {
                        GameObject obj = objects.get(i);
                        if (e.getX() >= obj.x && e.getX() <= obj.x + obj.width &&
                                e.getY() >= obj.y && e.getY() <= obj.y + obj.height) {
                            objects.remove(i);
                            repaint();
                            break;
                        }
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    // Check for right mouse button click to select an object for dragging
                    for (int i = objects.size() - 1; i >= 0; i--) {
                        GameObject obj = objects.get(i);
                        if (e.getX() >= obj.x && e.getX() <= obj.x + obj.width &&
                                e.getY() >= obj.y && e.getY() <= obj.y + obj.height) {
                            selectedObject = obj;
                            dragOffsetX = e.getX() - obj.x;
                            dragOffsetY = e.getY() - obj.y;
                            break;
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Stop dragging when the mouse is released
                selectedObject = null;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedObject != null) {
                    // Update the object's position based on the mouse's new position
                    selectedObject.x = e.getX() - dragOffsetX;
                    selectedObject.y = e.getY() - dragOffsetY;
                    repaint(); // Redraw the panel
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
        JFrame frame = new JFrame("Clickable Panel with Drag");
        ClickablePanelDrag panel = new ClickablePanelDrag();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}