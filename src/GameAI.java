
public class GameAI {
	private int coins[];
	private int [][]dp;
	public GameAI(int[] coins) {
		super();
		this.coins = coins;
		initDPTable();
	}

	public int bestMove(int i, int j) {		
		if (coins[i] + Math.min(m(i + 2, j), m(i + 1, j - 1)) > coins[j]+ Math.min(m(i, j - 2), m(i + 1, j - 1)))
			return i;
		return j;
	}
	public void initDPTable() {
		dp = new int[coins.length][coins.length];

		for (int j = 0; j < dp.length; j++) {
			for (int i = j; i >= 0; i--) {
				dp[i][j] = m(i, j);
			}
		}
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp.length; j++) {
				if (dp[i][j] == 0) {
					continue;

				}
			}
		}
	}
	public int m(int i, int j) {
		if (i == j)
			return coins[i];
		if (j < i)
			return 0;
		if (j - i == 1)
			return Math.max(coins[i], coins[j]);

		return Math.max(coins[i] + Math.min(dp[i + 2][j], dp[i + 1][ j - 1]),
				coins[j]+ Math.min(dp[i] [j - 2], dp[i + 1][ j - 1]));

	}
}
