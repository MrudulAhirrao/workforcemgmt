package com.railse.project.workforcemgmt.model;

import com.railse.project.workforcemgmt.model.enums.EventType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserComment extends TaskEvent {
    public UserComment(String description) {
        super(EventType.COMMENT, description);
    }
}
