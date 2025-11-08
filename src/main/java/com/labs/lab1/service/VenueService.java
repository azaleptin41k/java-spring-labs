package com.labs.lab1.service;

import com.labs.lab1.model.Venue;
import com.labs.lab1.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public Optional<Venue> getVenueById(Long id) {
        return venueRepository.findById(id);
    }

    public Optional<Venue> updateVenue(Long id, Venue updatedVenue) {
        return venueRepository.findById(id)
                .map(existingVenue -> {
                    existingVenue.setName(updatedVenue.getName());
                    existingVenue.setCity(updatedVenue.getCity());
                    existingVenue.setCapacity(updatedVenue.getCapacity());
                    return venueRepository.save(existingVenue);
                });
    }

    public boolean deleteVenue(Long id) {
        if (venueRepository.existsById(id)) {
            venueRepository.deleteById(id);
            return true;
        }
        return false;
    }
}