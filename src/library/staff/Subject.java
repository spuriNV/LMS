package library.staff;

import library.users.User;

public interface Subject {

    public void addToList(User user);

    public void notifyObserversItem();

    public void notifyObserversFines();

    public void removeFromList(User user);

}
