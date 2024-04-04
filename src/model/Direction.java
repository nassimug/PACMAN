package model;

import java.util.Random;

public enum Direction {
    
    UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

        public final int dx;
        public final int dy;
        private static final Random random = new Random();
        
        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        // Méthode utilitaire pour obtenir une direction aléatoire
        public static Direction getRandomDirection() {
            return values()[random.nextInt(values().length)];
        }

        // Méthode utilitaire pour vérifier si une direction est l'opposée d'une autre
        public boolean isOpposite(Direction other) {
            return dx == -other.dx && dy == -other.dy;
        }

        // Méthode utilitaire pour obtenir la direction opposée de la direction actuelle
        public Direction getOpposite() {
            switch (this) {
                case UP: return DOWN;
                case DOWN: return UP;
                case LEFT: return RIGHT;
                case RIGHT: return LEFT;
                default: return this; 
            }
        }
}
