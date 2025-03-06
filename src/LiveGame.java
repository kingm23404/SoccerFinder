import java.util.ArrayList;
import java.util.List;

public class LiveGame {
    private List<Player> playersInGame;
    private boolean isCanceled;

    public LiveGame() {
        playersInGame = new ArrayList<>();
        isCanceled = false;
    }

    public void addPlayer(Player player) {
        if (!isCanceled) {
            playersInGame.add(player);
            System.out.println(player.getName() + " has joined the game!");
        }
    }

    public void cancelGame() {
        isCanceled = true;
        System.out.println("The game has been canceled.");
    }

    public void displayGameStatus() {
        if (isCanceled) {
            System.out.println("Game Status: Canceled");
        } else {
            System.out.println("Game Status: Active");
            for (Player p : playersInGame) {
                System.out.println(p);
            }
        }
    }
}

