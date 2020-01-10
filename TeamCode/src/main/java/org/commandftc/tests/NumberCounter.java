package org.commandftc.tests;

import org.commandftc.Subsystem;

public class NumberCounter extends Subsystem {
    private int currentNumber = 1;
    
    public NumberCounter() { }

    public void toNext() {
        currentNumber ++;
    }

    public int getNumber() {
        return currentNumber;
    }
    
}