package org.commandftc;


public class ParallelFirstCommand extends ParallelCommand {

    public ParallelFirstCommand(Command ... commands) {
        super(commands);
    }

    @Override
    public ParallelFirstCommand whileUntilEither(Command other) {
        addCommand(other);
        return this;
    }

    @Override
    public boolean isFinished() {
        for(Command cmd : commands) {
            if(cmd.isFinished()) return true;
        }
        return false;
    }
}