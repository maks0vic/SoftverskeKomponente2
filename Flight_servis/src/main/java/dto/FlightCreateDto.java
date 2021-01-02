package dto;

import java.math.BigDecimal;

public class FlightCreateDto {

    private Long planeId;
    private String startDestination;
    private String finishDestination;
    private Integer flightLength;
    private BigDecimal price;

    public Long getPlaneId() {
        return planeId;
    }

    public void setPlaneId(Long planeId) {
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
