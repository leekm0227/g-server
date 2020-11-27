package com.leegm.common.model;

import java.util.ArrayList;
import java.util.List;

public class ZoneBean extends AbstractBean {

    private int zoneId;
    private List<ObjectBean> objects;

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public ZoneBean() {
        this.objects = new ArrayList<>();
    }

    public List<ObjectBean> getObjects() {
        return objects;
    }

    public boolean addObject(ObjectBean objectBean){
        return objects.add(objectBean);
    }

    public boolean removeObject(ObjectBean objectBean){
        return objects.remove(objectBean);
    }
}
