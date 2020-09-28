package org.craftedsw.tripservicekata.trip.helpers;

import org.craftedsw.tripservicekata.trip.TripService;
import org.craftedsw.tripservicekata.user.User;

public class NotLoggedUserTripService extends TripService {

  public User getLoggedUser() {
    return null;
  }
}
