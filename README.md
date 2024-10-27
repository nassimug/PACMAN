# Pacman Game

Ce projet consiste en la création d’un jeu inspiré du célèbre jeu [Pacman](https://fr.wikipedia.org/wiki/Pac-Man), développé en utilisant les bibliothèques graphiques Java.

## Description du Jeu

Le jeu se déroule sur une grille 2D représentant un labyrinthe vu de dessus. L'objectif est de guider Pacman dans le labyrinthe pour qu'il mange toutes les pacgommes tout en évitant les quatre fantômes qui y errent aléatoirement. Le joueur commence avec trois vies et peut en gagner une supplémentaire en accumulant plus de 5000 points.

### Règles du Jeu

- **Vies initiales :** 3
- **Conditions de victoire :** manger toutes les pacgommes
- **Conditions de défaite :** perdre toutes ses vies

### Déplacements et interactions

- **Pacman** peut se déplacer dans les quatre directions du labyrinthe. Il peut utiliser les passages de chaque côté de l’écran pour traverser d’un bord à l’autre.
- **Fantômes** se déplacent aléatoirement, changeant de direction lorsqu'ils rencontrent un mur. Si un fantôme touche Pacman, le joueur perd une vie.

### Types de PacGommes et Effets

| Couleur | Points | Effet |
|---------|--------|-------|
| Jaune   | 100    | Aucun effet spécial |
| Violet  | 300    | Pacman devient invisible pour les fantômes et change de couleur en jaune pâle |
| Orange  | 500    | Pacman devient un superpacman (couleur orange) et les fantômes deviennent bleus, devenant vulnérables |
| Vert    | 1000   | Change la structure du labyrinthe |

### Effets et règles spécifiques

- **Invisibilité (pacgommes violettes) :** Pacman peut traverser les fantômes sans perdre de vie.
- **SuperPacman (pacgommes oranges) :** Les fantômes deviennent vulnérables et se déplacent deux fois plus lentement. S’ils sont touchés par Pacman, ils retournent au centre du labyrinthe.
- **Points supplémentaires :** Si le joueur atteint 5000 points, une vie supplémentaire est accordée.

## Terminaison du Jeu

Le jeu se termine lorsque :
1. Pacman mange toutes les pacgommes (victoire).
2. Pacman perd toutes ses vies (défaite).

## Instructions d'Installation

1. Clonez le dépôt sur votre machine.
   ```bash
   git clone https://github.com/nassimug/Pacman
