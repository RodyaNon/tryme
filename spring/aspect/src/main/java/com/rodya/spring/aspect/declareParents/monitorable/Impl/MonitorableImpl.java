package com.rodya.spring.aspect.declareParents.monitorable.Impl;

import com.rodya.spring.aspect.declareParents.monitorable.Monitorable;

public class MonitorableImpl implements Monitorable {
    private boolean value =  true;

    @Override
    public boolean getValue() {
        return value;
    }
    @Override
    public void setValue(boolean value){
        this.value = value;
    }
}
