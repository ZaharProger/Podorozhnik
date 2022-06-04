package com.podorozhnik.interfaces;

import com.podorozhnik.enums.OperationResults;

public interface EventListener {
    void onResultReceived(OperationResults result);
}
