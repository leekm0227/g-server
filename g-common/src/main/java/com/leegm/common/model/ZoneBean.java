package com.leegm.common.model;

import java.util.ArrayList;
import java.util.Collection;

public class ZoneBean extends AbstractBean {

    private int zoneId;
    private Collection<ObjectBean> objects;

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public ZoneBean(Collection<ObjectBean> objects) {
        this.objects = objects;
    }

    public ZoneBean() {
        this.objects = new ArrayList<>();
    }

    public void addObject(ObjectBean object) {
        objects.add(object);
    }

    public Collection<ObjectBean> getObjects() {
        return objects;
    }

}
