import javafx.scene.control.Label;

public class GameState {
	private Coin []coin;
	private int player1Score;
	private int player2Score=0;
	private boolean isPlayer1Turn = true;
	private int playableIndexLeft;
	private int playableIndexRight;
	
	public GameState(int[] coins) {
        this.player1Score = 0;
        this.player2Score = 0;
        this.isPlayer1Turn = true;
        this.coin = new Coin[coins.length];
        this.playableIndexRight = coins.length - 1;
        int i=0;
        for(int coine: coins) {
        	coin[i] = new Coin(coine);
        	i++;
        }
    }
	
	public Coin getCoin(int index) {
		return coin[index];
	}
	
	public boolean isCoinPlayable(int index) {
		return index == playableIndexLeft || index == playableIndexRight;
	}
	
	public boolean isGameOver() {
		return playableIndexLeft == -1;
	}
	public int playableIndexLeft() {
		return playableIndexLeft;
	}public int playableIndexRight() {
		return playableIndexRight;
	}
	public void pickCoin(int index) {
		if (!isCoinPlayable(index)) return;
		if(isGameOver()) {
			playableIndexLeft = -1;
			playableIndexRight = coin.length;
			return;
		}
		if (index == playableIndexLeft) {
			playableIndexLeft++;
		}else {
			playableIndexRight--;
		}
		if(isPlayer1Turn()) {
			Coin coin = getCoin(index);
			incremantScore(coin.getValue());
			coin.setChosenBy("1");
			
		}
		else {
			Coin coin = getCoin(index);
			incremantScore(coin.getValue());
			coin.setChosenBy("2");
			
		}
		if(playableIndexLeft > playableIndexRight) {
			playableIndexLeft = -1;
			playableIndexRight = coin.length;
			return;
		}
	}
	public String winner() {
		if(getPlayer1Score() > getPlayer2Score())
			return "player1";
		else if(getPlayer1Score() < getPlayer2Score())
			return "player2";
		else
			return "draw";
	}
	private void incremantScore(int n) {
		if(isPlayer1Turn())
			setPlayer1Score(getPlayer1Score()+n);
		else 
			setPlayer2Score(getPlayer2Score()+n);
	}
	public void switchTurn() {
	        isPlayer1Turn = !isPlayer1Turn;
    }
	
	public void setCoin(Coin[] coin) {
		this.coin = coin;
	}

	public int getPlayer1Score() {
		return player1Score;
	}

	public void setPlayer1Score(int player1Score) {
		this.player1Score = player1Score;
	}

	public int getPlayer2Score() {
		return player2Score;
	}

	public void setPlayer2Score(int player2Score) {
		this.player2Score = player2Score;
	}

	public boolean isPlayer1Turn() {
		return isPlayer1Turn;
	}

	public void setPlayer1Turn(boolean isPlayer1Turn) {
		this.isPlayer1Turn = isPlayer1Turn;
	}


	
}
