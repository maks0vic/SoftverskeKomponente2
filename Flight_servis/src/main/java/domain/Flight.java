package domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(optional = false)
	private Plane plane;
	private String startDestination;
	private String finishDestination;
	private Integer flightLength;
	private BigDecimal price;
	
	
	public Flight() {
		
	}
	
	public Flight(Plane plane, String startDestination, String finishDestination, Integer flightLength,
			BigDecimal price) {
		this.plane = plane;
		this.startDestination = startDestination;
		this.finishDestination = finishDestination;
		this.flightLength = flightLength;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public String getStartDestination() {
		return startDestination;
	}

	public void setStartDestination(String startDestination) {
		this.startDestination = startDestination;
	}

	public String getFinishDestination() {
		return finishDestination;
	}

	public void setFinishDestination(String finishDestination) {
		this.finishDestination = finishDestination;
	}

	public Integer getFlightLength() {
		return flightLength;
	}

	public void setFlightLength(Integer flightLength) {
		this.flightLength = flightLength;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
}
