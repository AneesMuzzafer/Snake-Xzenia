
package trysnake;

import java.awt.Graphics;
import javax.swing.JPanel;


public class renderSnake extends JPanel {
    
    private static final long serialVersionUID = 1L ;
    
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    
        TrySnake.snake.repaint(g);
    }
}
