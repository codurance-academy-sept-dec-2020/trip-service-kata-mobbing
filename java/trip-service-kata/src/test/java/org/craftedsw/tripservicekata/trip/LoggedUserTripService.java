package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;

class LoggedUserTripService extends TripService {

  @Override
  protected User getLoggedUser() {
    return new User();
  }
}
