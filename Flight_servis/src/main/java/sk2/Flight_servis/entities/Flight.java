package sk2.Flight_servis.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long planeId;
    private String startDestination;
    private String finishDestination;
    private Integer length;
    private BigDecimal price;
    private Boolean canceled;

    public Flight(){
    }

    public Flight(long planeId, String startDestination, String finishDestination, Integer length, BigDecimal price) {
        this.planeId = planeId;
        this.startDestination = startDestination;
        this.finishDestination = finishDestination;
        this.length = length;
        this.price = price;
        this.canceled = false;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getPlaneId() {
        return planeId;
    }

    public void setPlaneId(long planeId) {
        this.planeId = planeId;
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
