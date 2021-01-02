package service;

import repository.PlaneRepository;

public class PlaneService {

    private PlaneRepository planeRepository;

    public PlaneService(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }


}
