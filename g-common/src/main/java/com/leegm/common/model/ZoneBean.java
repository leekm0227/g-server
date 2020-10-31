package com.leegm.common.model;

import java.util.ArrayList;
import java.util.Collection;

public class ZoneBean extends AbstractBean {

    private int zoneId;
    private ObjectBean[] objects;

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public ZoneBean(ObjectBean[] objects) {
        this.objects = objects;
    }

    public ObjectBean[] getObjects() {
        return objects;
    }
}
