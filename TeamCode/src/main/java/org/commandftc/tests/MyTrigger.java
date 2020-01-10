package org.commandftc.tests;

import org.commandftc.Trigger;

public class MyTrigger extends Trigger {
    private boolean value = false;
    @Override
    public boolean get() {
        return value;
    }

    public void set(boolean v) {
        value = v;
    }
}