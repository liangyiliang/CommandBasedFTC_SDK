package org.commandftc.tests;

import org.commandftc.*;

public class CountTo10Command extends Command {
    private NumberCounter counter;

    public CountTo10Command(NumberCounter nc) {
        counter = nc;
        addRequirements(counter);
    }

    @Override 
    public void init() { 
        counter.reset();
        System.out.println("C10: Initialized");
    }

    @Override
    public void execute() {
        System.out.println("C10: Curr = " + counter.getNumber());
        counter.toNext();
    }

    @Override
    public void end() {
        System.out.println("C10: Ending");
    }

    @Override
    public boolean isFinished() {
        return counter.getNumber() > 10;
    }

}