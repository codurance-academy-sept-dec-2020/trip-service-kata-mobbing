package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

  private final UserSession userSession;

  public TripService(UserSession userSession) {
    this.userSession = userSession;
  }

  public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
    List<Trip> tripList = new ArrayList<>();
    User loggedUser = getLoggedUser();
    if (loggedUser != null) {
      if (user.isFriendsWith(loggedUser)) {
        tripList = getUserTrips(user);
      }
      return tripList;
    } else {
      throw new UserNotLoggedInException();
    }
  }

  protected List<Trip> getUserTrips(User user) {
    return TripDAO.findTripsByUser(user);
  }

  protected User getLoggedUser() {
    return userSession.getLoggedUser();
  }

}
