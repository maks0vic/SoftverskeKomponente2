package domain;

import java.util.List;

public class User {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String passportNumber;
	private Rank rank;
	private List<Credit_card> creditCards;
	
	
	public User() {
		super();
	}
	
	public User(String firstName, String lastName, String email, String password, String passportNumber, Rank rank,
			List<Credit_card> creditCards) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.passportNumber = passportNumber;
		this.rank = rank;
		this.creditCards = creditCards;
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassportNumber() {
		return passportNumber;
	}
	
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public List<Credit_card> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(List<Credit_card> creditCards) {
		this.creditCards = creditCards;
	}
	
	
	
}
