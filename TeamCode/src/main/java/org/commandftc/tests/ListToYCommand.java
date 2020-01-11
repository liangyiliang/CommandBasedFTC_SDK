package org.commandftc.tests;

import org.commandftc.*;
public class ListToYCommand extends Command {
    private LetterLister lister;
    public ListToYCommand(LetterLister lister) {
        this.lister = lister;
        addRequirements(lister);
    }

    @Override 
    public void init() { 
        lister.reset();
        System.out.println("LY: Initialized");
    }

    @Override
    public void execute() {
        System.out.println("LY: Curr = " + lister.getChar());
        lister.toNext();
    }

    @Override
    public void end() {
        System.out.println("LY: Ending");
    }

    @Override
    public boolean isFinished() {
        return lister.getChar() > 'Y';
    }
}