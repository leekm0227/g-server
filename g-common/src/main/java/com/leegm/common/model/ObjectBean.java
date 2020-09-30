package com.leegm.common.model;

import com.leegm.common.protocol.Object;
import com.leegm.common.protocol.State;
import com.leegm.common.util.Const;

public class ObjectBean extends AbstractBean {
    private String name;
    private int objectId;
    private byte type;
    private byte state = State.IDLE;
    private float[] position;
    private float[] direction = new float[]{0, 0, 0};
    private int hp;
    private int mp;

    //status
    private float power = 1;
    private float defense = 1;
    private int range = 1;
    private float attackSpeed = 1;
    private float moveSpeed = 1;
    private int maxHp = 1;
    private int maxMp = 1;

    public ObjectBean(String name, int objectId, byte type, float[] position) {
        this.name = name;
        this.objectId = objectId;
        this.type = type;
        this.position = position;
        this.hp = maxHp;
        this.mp = maxMp;
    }

    public ObjectBean update(Object object) {
        if (object.direction() != null) {
            this.position[Const.POS_X] = object.direction().x();
            this.position[Const.POS_Y] = object.direction().y();
            this.position[Const.POS_Z] = object.direction().z();
        }

        if (object.state() != State.IDLE) {
            this.state = object.state();
        }

        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public float[] getPosition() {
        return position;
    }

    public float getPosition(int index) {
        return position[index];
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public void setPosition(int index, float position) {
        this.position[index] = position;
    }

    public float[] getDirection() {
        return direction;
    }

    public void setDirection(float[] direction) {
        this.direction = direction;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public float getDefense() {
        return defense;
    }

    public void setDefense(float defense) {
        this.defense = defense;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }
}
