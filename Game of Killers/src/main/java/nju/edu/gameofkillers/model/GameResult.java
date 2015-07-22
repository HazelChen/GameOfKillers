package nju.edu.gameofkillers.model;

/**
 * Created by hazel on 2015-07-22.
 */
public enum GameResult {

    ING,

    KILLER_WIN(Identity.KILLER),

    CIVILIAN_WIN(Identity.CIVILIAN);

    private Identity winner;

    private GameResult(){
        this(null);
    }

    private GameResult(Identity winner) {
        this.winner = winner;
    }

    public Identity getWinner() {
        return winner;
    }

}
