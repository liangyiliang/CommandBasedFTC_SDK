package org.commandftc.tests;

import org.commandftc.Subsystem;

public class LetterLister extends Subsystem {
    private final char chA = 'A';
    private int currNumber = 0;
    
    public LetterLister() { }

    public void toNext() {
        currNumber = (currNumber + 1) % 26;
    }

    public char getChar() {
        return (char)((int)chA + currNumber);
    }
}