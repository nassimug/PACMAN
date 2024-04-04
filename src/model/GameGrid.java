package model;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import view.Observable;
import view.Observer;


public class GameGrid implements Observable{
    private CellType[][] grid;
    private int width;
    private int height;
    private List<Observer> observers = new ArrayList<>(); // Observateurs pour le modèle Observer


    // Constructeur pour initialiser la grille avec une taille donnée
    public GameGrid(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new CellType[width][height];
    }

    // Méthodes pour ajouter, retirer et notifier les observateurs du modèle Observer
    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Récupère le type de cellule à une position donnée dans la grille
    public CellType getCellType(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return grid[x][y];
        }
        return CellType.WALL; // Retourne WALL par défaut si hors limites
    }
    
    // Définit le type de cellule à une position donnée dans la grille
    public void setCellType(int x, int y, CellType type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[x][y] = type;
        }
    }

    // Efface la grille en mettant toutes les cellules à EMPTY
    public void clearGrid() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[x][y] = CellType.EMPTY; 
            }
        }
    }

    // Remplit la grille en fonction d'une représentation textuelle du labyrinthe
    public void fillGrid(String mapRepresentation) {
        clearGrid();
        String[] lines = mapRepresentation.split("\n");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char c = lines[y].charAt(x);
                switch (c) {
                    case '#':
                        grid[x][y] = CellType.WALL;
                        break;
                    case '.':
                        grid[x][y] = CellType.EMPTY;
                        break;
                }
            }
        }
    }
    
    // Distribue les pacgums sur la grille
    public void distributePacgums() {
        Random rand = new Random();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y] == CellType.EMPTY) {
                    int randomNum = rand.nextInt(100);
                    if (randomNum < 70) {
                        grid[x][y] = CellType.BLUE_PACGUM;
                    } else if (randomNum < 90) {
                        grid[x][y] = CellType.PURPLE_PACGUM;
                    } else if (randomNum < 97) {
                        grid[x][y] = CellType.ORANGE_PACGUM;
                    } else if (randomNum < 100) {
                        grid[x][y] = CellType.GREEN_PACGUM;
                    }
                }
            }
        }
    }
    
    // Distribue une deuxième série de pacgums sur la grille 
    // (sans pacgums vertes, sinon le jeu tournerais en boucle)
    public void distributePacgums2() {
        Random rand = new Random();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y] == CellType.EMPTY) {
                    int randomNum = rand.nextInt(100);
                    if (randomNum < 75) {
                        grid[x][y] = CellType.BLUE_PACGUM;
                    } else if (randomNum < 95) {
                        grid[x][y] = CellType.PURPLE_PACGUM;
                    } else if (randomNum < 100) {
                        grid[x][y] = CellType.ORANGE_PACGUM;
                    }
                }
            }
        }
    }

}
