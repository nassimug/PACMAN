package main;

import model.GameGrid;
import view.GameMap;
import view.GameView;

public class Main {
    public static void main(String[] args) {
        GameGrid grid = new GameGrid(23, 24); // Taille de la grille (23x24)

        // Rempli la grille avec la représentation du labyrinthe.
        grid.fillGrid(GameMap.INITIAL_MAP);
        
        // Distribue les pacgommes 
        grid.distributePacgums();

        // Initialise et affiche l'interface graphique du jeu
        new GameView(grid);
    }
}
