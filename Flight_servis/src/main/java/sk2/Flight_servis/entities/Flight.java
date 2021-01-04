package sk2.Flight_servis.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Plane plane;
    private String startDestination;
    private String finishDestination;
    private Integer length;
    private BigDecimal price;

    public Flight(){
    }

    public Flight(Plane plane, String startDestination, String finishDestination, Integer length, BigDecimal price) {
        this.plane = plane;
        this.startDestination = startDestination;
        this.finishDestination = finishDestination;
        this.length = length;
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

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
