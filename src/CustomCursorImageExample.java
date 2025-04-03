import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class CustomCursorImageExample extends JFrame {

    public CustomCursorImageExample() {
        setTitle("Custom Cursor with Image");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            // 1. Load the cursor image from the internet (replace with your image URL)
            URL cursorImageUrl = new URL("https://cdn140.picsart.com/298402190179211.png?to=min&r=1024");  // Replace with your image URL
            BufferedImage cursorImage = ImageIO.read(cursorImageUrl);

            // 2. Create a custom cursor
            Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    cursorImage,
                    new Point(0, 0),  // Hotspot (top-left corner)
                    "CustomCursor"
            );

            // 3. Set the custom cursor for the frame
            setCursor(customCursor); // Use setCursor on the JFrame itself
        } catch (IOException e) {
            System.err.println("Error loading cursor image: " + e.getMessage());
            // Handle the error.  Could set a default cursor or display an error message.
            // For now, the default cursor will be used if the image fails to load.
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CustomCursorImageExample::new);
    }
}