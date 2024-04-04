package view;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import model.CellType;
import model.Direction;
import model.GameGrid;
import model.Ghost;
import model.Pacman;
import java.util.List;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
public class GamePanel extends JPanel implements KeyListener {
    private GameGrid grid;
    private List<Ghost> ghosts = new ArrayList<>();
    private Timer timer;
    private Timer animationTimer;
    private Pacman pacman = new Pacman(11, 14);
    private static Color wallColor = new Color(144, 250, 144);

    public GamePanel(GameGrid grid) {
        this.grid = grid;
        setBackground(Color.BLACK);
        ghosts.add(new Ghost(9, 12));
        ghosts.add(new Ghost(10, 12));
        ghosts.add(new Ghost(12, 12));
        ghosts.add(new Ghost(13, 12));
        // la taille du panneau
        int prefWidth = grid.getWidth() * CellSize;
        int prefHeight = grid.getHeight() * CellSize;
        setPreferredSize(new Dimension(prefWidth, prefHeight));
        setFocusable(true);
        addKeyListener(this);
        initGameTimer();
        // l'animation de la bouche de Pac-Man
        animationTimer = new Timer(300, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pacman.toggleMouth(); 
                repaint(); 
            }
        });
        animationTimer.start();
        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });

        this.add(pauseButton);
    }
    
    // dessine la grille 
    private void drawGrid(Graphics g) {
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                CellType cellType = grid.getCellType(x, y);
                switch (cellType) {
                    case WALL:
                        g.setColor(getWallColor()); // Murs
                        g.fillRect(x * CellSize, y * CellSize, CellSize, CellSize);
                        break;
                    case EMPTY: 
                        g.setColor(Color.BLACK); // Cases vides
                        g.fillRect(x * CellSize, y * CellSize, CellSize, CellSize);
                        break;
                    case BLUE_PACGUM:
                        drawPacgum(g, x, y, Color.BLUE); // Pacgomme bleue
                        break;
                    case PURPLE_PACGUM:
                        drawPacgum(g, x, y, Color.MAGENTA); // Pacgomme violette
                        break;
                    case ORANGE_PACGUM:
                        drawPacgum(g, x, y, Color.ORANGE); // Pacgomme orange
                        break;
                    case GREEN_PACGUM:
                        drawPacgum(g, x, y, Color.GREEN); // Pacgomme verte
                        break;
                    case GHOST:
                        break;
                    case PACMAN:
                        break;
                    default:
                        break;
                }
            }
        }
    }

    // dessine le pacman
    private void drawPacman(Graphics g) {
        int baseX = pacman.getX() * CellSize;
        int baseY = pacman.getY() * CellSize;

        if (pacman.isSuperPacman()) {
            g.setColor(Color.ORANGE); // Couleur pour Super Pac-Man
        } else if (pacman.isInvisible()) {
            g.setColor(new Color(255, 240, 125)); // Jaune pâle pour invisible
        } else {
            g.setColor(Color.YELLOW); // Couleur normale de Pac-Man
        }
        // l'ouverture/fermeture de la bouche
        int startAngle = pacman.isMouthOpen() ? 30 : 0; 
        int arcAngle = pacman.isMouthOpen() ? 300 : 360; 

        g.fillArc(baseX, baseY, CellSize, CellSize, startAngle, arcAngle);

        // Dessiner l'œil de Pac-Man
        g.setColor(Color.BLACK);
        int eyeSize = CellSize / 5; 
        int eyeX = baseX + 2 * CellSize / 3;
        int eyeY = baseY + CellSize / 4; 
        g.fillOval(eyeX, eyeY, eyeSize, eyeSize);
        
    }

    // dessine les 4 fontomes 
    private void drawGhosts(Graphics g) {
        for (Ghost ghost : ghosts) {
            int baseX = ghost.getX() * CellSize;
            int baseY = ghost.getY() * CellSize;

            if (pacman.isSuperPacman()) {
                g.setColor(Color.BLUE); 
            } else {
                g.setColor(Color.RED); 
            }
            g.fillArc(baseX, baseY, CellSize, CellSize, 0, 180); 
            g.fillRect(baseX, baseY + CellSize / 2, CellSize, CellSize / 2); 

            // Pieds du fantôme (forme de vague)
            int feetCount = 3; 
            int footWidth = CellSize / ( 2*feetCount);
            int footHeight = footWidth; 
            for (int i = 0; i < feetCount; i++) {
                int footX = baseX + (2 * i * footWidth) - (footWidth / 2);                                                           
                g.fillArc(footX, baseY + CellSize - footHeight, footWidth, footHeight * 2, 0, 180);
            }

            // Yeux du fantôme
            g.setColor(Color.WHITE);
            g.fillOval(baseX + CellSize / 4, baseY + CellSize / 4, CellSize / 4, CellSize / 4); 
            g.fillOval(baseX + 2 * CellSize / 4, baseY + CellSize / 4, CellSize / 4, CellSize / 4); 

            // Pupilles des yeux
            g.setColor(Color.BLACK); 
            g.fillOval(baseX + CellSize / 4 + CellSize / 8, baseY + CellSize / 4, CellSize / 8, CellSize / 8);                                                                                              // gauche
            g.fillOval(baseX + 2 * CellSize / 4 + CellSize / 8, baseY + CellSize / 4, CellSize / 8, CellSize / 8);                                                                                                   
        }
    }
    
    // dessine les pacgommes
    private void drawPacgum(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillOval(x * CellSize + CellSize / 4, y * CellSize + CellSize / 4, CellSize / 2, CellSize / 2);                                                                                                      
    }

    // affiche le score directement sur le haut de la grille 
    private void drawScore(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 22)); 
        String scoreText = "Score: " + pacman.getScore(); 
        int x = getWidth() - getInsets().right - g.getFontMetrics().stringWidth(scoreText) - 10;
        int y = getInsets().top + 20;
        g.drawString(scoreText, x, y);
    }

    // affiche le nombre de vies restant en haut de la grille 
    private void drawLives(Graphics g) {
        g.setColor(Color.BLACK); 
        g.setFont(new Font("Arial", Font.BOLD, 22)); 
        String livesText = "Vies: " + pacman.getLives();
        int x = 10;
        int y = 20;
        g.drawString(livesText, x, y);
    }

    // appelle toutes les fonctions précédentes pour les affichées 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawPacman(g);
        drawGhosts(g);
        drawScore(g);
        drawLives(g);
    }
    
    // fait bouger les fontomes 
    public void updateGhosts() {
        for (Ghost ghost : ghosts) {
            ghost.move(grid);
        }
        repaint();
        if (pacman.areAllPacgumsEaten(grid)) {
            timer.stop(); 
            showWinningMessage();
        }
    }

    // le timer est considèré comme la vitesse des fontomes 
    private void initGameTimer() {
        // Initialise le Timer avec une période par défaut de 200ms
        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pacman.isSuperPacman()) {
                    // Si un fantôme est vulnérable, ajuste le timer à 400 ms
                    if (timer.getDelay() != 400) {
                        timer.setDelay(400);
                    }
                } else {
                    // Sinon, le timer revient à 200 ms
                    if (timer.getDelay() != 200) {
                        timer.setDelay(200);
                    }
                }
                updateGhosts();
                checkCollisions();
            }
        });
        timer.start();
    }


    // message pour affiché "gagné"
    private void showWinningMessage() {
         timer.stop();
        int choice = JOptionPane.showConfirmDialog(this,
                "Score: " + pacman.getScore() + "\nVoulez-vous rejouer ?",
                "Gagné!!",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    // message pour afficher "game-over"
    public void gameOver() {
        timer.stop();
        int choice = JOptionPane.showConfirmDialog(this,
                "Score : " + pacman.getScore() + "\nVoulez-vous rejouer ?",
                "Game Over",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    // affiche un bouton pause
    private void togglePause() {
        if (timer.isRunning()) {
            timer.stop();
        } else {
            timer.start();
            this.requestFocusInWindow();
        }
    }

    // fonction qui permet de rejouer si l'utilisateur le désire 
    private void resetGame() {
        
        grid.fillGrid(GameMap.INITIAL_MAP);
        pacman.setLives(3); 
        pacman.setScore(0);
        pacman.resetPosition(); 
        for (Ghost ghost : ghosts) {
            ghost.resetPosition(); 
        }
        grid.distributePacgums(); 

        wallColor = new Color(144, 250, 144);
        if (timer != null) {
            timer.start(); 
        }

        if (animationTimer != null) {
            animationTimer.restart(); 
        }
        repaint();
    }
    
    // fonction qui permet de détecter toutes les collisions 
    public void checkCollisions() {
        for (Ghost ghost : ghosts) {
            if ((pacman.getX() == ghost.getX() && pacman.getY() == ghost.getY())) {
                // Lorsque Pac-Man est super, les fantômes retournent à leur position initiale
                if (pacman.isSuperPacman()) {
                    resetPositions(); 
                } else if (!pacman.isInvisible()) { // Quand Pac-Man n'est pas invisible et pas super
                    // Si Pac-Man n'est pas super et touche un fantôme
                    pacman.loseLife(); // Pac-Man perd une vie
                    if (!pacman.isAlive()) {
                        gameOver(); // Fin du jeu
                    } else {
                        pacman.resetPosition(); // Réinitialiser seulement la position de Pac-Man
                    }
                } // Si Pac-Man est invisible, rien ne se passe, il ne perd pas de vie
                break;
            }
        }
    }
    
    // réinitialise la positions des fontomes 
    private void resetPositions() {
        for (Ghost ghost : ghosts) {
            ghost.resetPosition();
        }
    }

    // chnage de couleur 
    public Color getWallColor() {
        return wallColor;
    }

    // change la struture du labyrinthe
    public void changeToNewStructure(GameGrid grid) {
        grid.fillGrid(GameMap.NEW_MAP);
        resetPositions();
        pacman.resetPosition();
        grid.distributePacgums2();
        wallColor = Color.cyan;
    }

    // méthode qui gère les mouvements du pacman 
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                pacman.move(Direction.UP, grid,this);
                break;
            case KeyEvent.VK_DOWN:
                pacman.move(Direction.DOWN, grid,this);
                break;
            case KeyEvent.VK_LEFT:
                pacman.move(Direction.LEFT, grid,this);
                break;
            case KeyEvent.VK_RIGHT:
                pacman.move(Direction.RIGHT, grid,this);
                break;
        }
        repaint(); 
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    // taille de la fenetre 
    public static final int CellSize = 25;
}
