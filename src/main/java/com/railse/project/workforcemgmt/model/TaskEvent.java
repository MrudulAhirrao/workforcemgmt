package com.railse.project.workforcemgmt.model;

import com.railse.project.workforcemgmt.model.enums.EventType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class TaskEvent {
    private Long timestamp;
    private EventType eventType;
    private String description;

    public TaskEvent(EventType eventType, String description) {
        this.timestamp = System.currentTimeMillis();
        this.eventType = eventType;
        this.description = description;
    }
}
