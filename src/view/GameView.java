package view;
import javax.swing.JFrame;
import model.GameGrid;

public class GameView extends JFrame {
    private GamePanel panel; 

    // parametre d'affichage de la fenetre 
    public GameView(GameGrid grid) {
        setTitle("Pac-Man");
        this.panel = new GamePanel(grid);
        add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public GamePanel getGamePanel() {
        return panel;
    } 
}
