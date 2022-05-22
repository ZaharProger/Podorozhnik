package com.podorozhnik.interfaces;

import com.podorozhnik.entities.Location;

import java.util.List;

public interface CollectionReceivedListener {
    void updateCollection(List<Location> newCollection);
}
