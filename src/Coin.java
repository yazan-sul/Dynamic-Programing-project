public class Coin {
	private int value;
	private String chosenBy;
	
	public Coin(int value) {
		this.value = value;
		chosenBy = "";
	}

	public Coin(int value, boolean isActive, String chosenBy) {
		super();
		this.value = value;
		this.chosenBy = chosenBy;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getChosenBy() {
		return chosenBy;
	}

	public void setChosenBy(String chosenBy) {
		if(chosenBy.equals("1"))
			this.chosenBy = "player1";
		else {
			this.chosenBy = "player2";
		}
	}

}