package com.podorozhnik.interfaces;

import com.podorozhnik.enums.OperationResults;

public interface DatabaseEventListener {
    void onResultReceived(OperationResults result);
}
