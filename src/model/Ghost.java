package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;


public class Ghost extends Character {
    private int startX;
    private int startY;
    private Direction direction;
    private static final Random random = new Random();
    private boolean isVulnerable;
    private Timer Timer;

    public Ghost(int x, int y) {
        super(x, y); // Appel du constructeur de la classe parent Character
        this.startX = x;
        this.startY = y;
        this.direction = Direction.getRandomDirection();

        // Initialisation d'un timer qui remettra la vulnérabilité du fantôme à faux après 5 secondes
        Timer = new Timer(5000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setVulnerable(false); 
                Timer.stop(); 
            }
        });
        Timer.setRepeats(false);
    }

    // Méthode pour déplacer le fantôme dans la grille
    public void move(GameGrid grid) {
        List<Direction> possibleDirections = new ArrayList<>();
        
        // Vérifier toutes les directions possibles où le fantôme peut se déplacer (ne pas entrer dans les murs)
        for (Direction d : Direction.values()) {
            if (!d.isOpposite(this.direction) && grid.getCellType(x + d.dx, y + d.dy) != CellType.WALL) {
                possibleDirections.add(d);
            }
        }

        // Permettre au fantôme de faire demi-tour s'il n'y a pas d'autres options
        if (possibleDirections.isEmpty()) {
            possibleDirections.add(this.direction.getOpposite());
        }
        
        // Sélectionner aléatoirement l'une des directions possibles et se déplacer dans cette direction
        this.direction = possibleDirections.get(random.nextInt(possibleDirections.size()));
        x += direction.dx;
        y += direction.dy;
    }

    
    // Getter pour la direction actuelle du fantôme
    public Direction getDirection() {
        return this.direction; 
    }
    
    // Réinitialise le fantôme à sa position et direction de départ
    public void resetPosition() {
        this.x = this.startX;
        this.y = this.startY;
        this.direction = getDirection();
    }

    // Vérifie si le fantôme est actuellement vulnérable
    public boolean isVulnerable() {
        return isVulnerable;
    }

    // Définit la vulnérabilité du fantôme et démarre/arrête le timer en conséquence
    public void setVulnerable(boolean vulnerable) {
        isVulnerable = vulnerable;
        if (vulnerable) {
            resetPosition(); 
            Timer.start(); 
        } else {
            Timer.stop();
        }
    }
}
