package sk2.Flight_servis.forms;

import sk2.Flight_servis.entities.Plane;

import java.math.BigDecimal;

public class SearchFlightForm {

    private Plane plane;
    private String startDestination;
    private String finishDestination;
    private Integer length;
    private BigDecimal price;

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
