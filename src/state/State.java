package state;

import model.Pacman;

public interface State {
    void handleInput(Pacman pacman);
    void update(Pacman pacman);
}
