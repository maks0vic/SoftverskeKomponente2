package domain;

public class Credit_card {
	private String firstName;
	private String lastName;
	private String cardNumber;
	private String pin;
	
	public Credit_card() {
		super();
	}

	public Credit_card(String firstName, String lastName, String cardNumber, String pin) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.cardNumber = cardNumber;
		this.pin = pin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
	
	
}
