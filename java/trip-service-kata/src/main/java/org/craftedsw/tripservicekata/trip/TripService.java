package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

  public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
    List<Trip> tripList = new ArrayList<>();
    User loggedUser = getLoggedUser();
    if (user.isFriendsWith(loggedUser)) {
      tripList = getUserTrips(user);
    }
    return tripList;
  }

  protected List<Trip> getUserTrips(User user) {
    return TripDAO.findTripsByUser(user);
  }

  private User getLoggedUser() {
    User loggedUser = getUser();
    if (loggedUser == null) {
      throw new UserNotLoggedInException();
    }
    return loggedUser;
  }

  protected User getUser() {
    return UserSession.getInstance().getLoggedUser();
  }

}
