package com.railse.project.workforcemgmt.model;

import com.railse.project.workforcemgmt.model.enums.EventType;

public class ActivityLog extends TaskEvent {
public ActivityLog(String description) {
        super(EventType.SYSTEM, description);
    }
}
