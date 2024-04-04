package model;


import javax.swing.Timer;

import view.GamePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class Pacman extends Character {
    private boolean mouthOpen = true; 
    private int score;
    private int lives;
    private int winLives;
    private boolean isInvisible = false;
    private Timer Timer;
    private final static int startingX = 11;
    private final static int startingY = 14;
    private boolean isSuperPacman;
    private List<Ghost> ghosts = new ArrayList<>();


    public Pacman(int x, int y) {
        super(x, y);
        this.score = 0;
        this.lives = 3;
        this.winLives = 0;
        Timer = new Timer(5000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setSuperPacman(false);
                setInvisible(false);
                Timer.stop();
            }
        });
        Timer.setRepeats(false);
    }
    
    // Méthode pour déplacer Pacman dans une direction donnée dans la grille
    public void move(Direction direction, GameGrid grid, GamePanel gamePanel) {

        int newX = x;
        int newY = y;

        // Détermine la nouvelle position basée sur la direction
        switch (direction) {
            case UP:
                newY = (y > 0) ? y - 1 : grid.getHeight() - 1;
                break;
            case DOWN:
                newY = (y < grid.getHeight() - 1) ? y + 1 : 0;
                break;
            case LEFT:
                newX = (x > 0) ? x - 1 : grid.getWidth() - 1;
                break;
            case RIGHT:
                newX = (x < grid.getWidth() - 1) ? x + 1 : 0;
                break;
        }

        // Vérifiez si la nouvelle position est dans un mur.
        CellType cellType = grid.getCellType(newX, newY);
        if (cellType != CellType.WALL) {
            // Tente de manger une pacgomme à la nouvelle position
            int eatResult = eatPacgum(newX, newY, cellType, grid, gamePanel);

            // Si manger une pacgomme ne nécessite pas d'arrêter le mouvement
            if (eatResult == 0) {
                x = newX;
                y = newY;
            }
        }
        // Sinon, Pacman reste à sa position actuelle (il heurte un mur).
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int i) {
        this.score=i;
    }

    // Méthode pour incrémenter le score quand Pacman mange une pacgomme
    private void incrementScore(CellType pacgumType) {
        switch (pacgumType) {
            case BLUE_PACGUM:
                score += 100; 
                break;
            case PURPLE_PACGUM:
                score += 300; 
                break;
            case ORANGE_PACGUM:
                score += 500;
                break;
            case GREEN_PACGUM:
                score += 1000; 
                break;
            case EMPTY:
                break;
            case GHOST:
                break;
            case PACMAN:
                break;
            case WALL:
                break;
            default:
                break;
        }

        if (getScore() - (getwinLives() * 5000) >= 5000) {
            winwinLives();
            winLife();
        }
    }

    // Méthode pour manger une pacgomme et appliquer ses effets
    private int eatPacgum(int x, int y, CellType pacgumType, GameGrid grid,GamePanel gamePanel) {

        grid.setCellType(x, y, CellType.EMPTY);

        incrementScore(pacgumType);

        if (pacgumType == CellType.PURPLE_PACGUM) {
            setInvisible(true);
        } else if (pacgumType == CellType.ORANGE_PACGUM) {
            setSuperPacman(true);
            for (Ghost ghost : ghosts) {
                ghost.setVulnerable(true);
                ghost.resetPosition();
            }
        } else if (pacgumType == CellType.GREEN_PACGUM) {
            gamePanel.changeToNewStructure(grid); // Change the labyrinth structure
            return 1;
        }
        return 0;
    }

    // Méthode pour vérifier si toutes les pacgommes ont été mangées
    public boolean areAllPacgumsEaten(GameGrid grid) {
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                CellType cellType = grid.getCellType(x, y);
                if (cellType == CellType.BLUE_PACGUM || cellType == CellType.PURPLE_PACGUM
                        || cellType == CellType.ORANGE_PACGUM || cellType == CellType.GREEN_PACGUM) {
                    return false; // Trouvé une pacgomme, donc toutes les pacgommes ne sont pas mangées
                }
            }
        }
        return true; // Aucune pacgomme trouvée, toutes sont mangées
    }
    
    // perte de vie 
    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }

    // gain d'une vie (majorée à 3 maximum)
    public void winLife() {
        if( getLives() < 3){
            lives++;
        }    
    }

    public int getwinLives() {
        return winLives;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    // verifie si le pacman est toujours en vie 
    public boolean isAlive() {
        return lives > 0;
    }
    
    public void winwinLives(){
        this.winLives ++; 
    }

    // réinitialise la position du pacman 
    public void resetPosition() {

        this.x = startingX;
        this.y = startingY;
        setInvisible(false);
    
    }

    // lance un timer une fois le pacman invisvible  
    public void setInvisible(boolean invisible) {
        isInvisible = invisible;
        if (isInvisible) {
            if (Timer.isRunning()) {
                Timer.stop(); // Arrête le timer s'il est déjà en cours d'exécution
            }
            Timer.start(); // Redémarre le timer
        } else {
            Timer.stop(); // Arrête le timer si Pacman n'est plus invisible
        }
    }

    // verifie si le pacman est invisible pour les fontomes
    public boolean isInvisible() {
        return isInvisible;
    }
 
    // Méthode pour basculer l'état de la bouche de Pacman (ouverte/fermée)
    public void toggleMouth() {
        mouthOpen = !mouthOpen;
    }

    public boolean isMouthOpen() {
        return mouthOpen;
    }

    // verifie si le pacman est un superPacman (lorsqu'il mange une pacgomme orange)
    public boolean isSuperPacman() {
        return isSuperPacman;
    }
    
    // lancer un timer lorsqu'il est superPacman
    public void setSuperPacman(boolean isSuper) {
        isSuperPacman = isSuper;
        if (isSuper) {
            if (Timer.isRunning()) {
                Timer.stop(); // Arrête le timer s'il est déjà en cours d'exécution
            }
            Timer.start(); // Redémarre le timer
        } else {
            Timer.stop(); // Arrête le timer si Pacman n'est plus en mode super
        }
    }

}
