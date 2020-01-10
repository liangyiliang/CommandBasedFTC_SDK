package org.commandftc;

import java.util.ArrayList;

public abstract class ParallelCommand extends Command {
    /**
     * This class is used to emulate the C++ "friend" class features. 
     * @see https://stackoverflow.com/questions/182278/is-there-a-way-to-simulate-the-c-friend-concept-in-java
     */
    public static final class PCAccessToken extends AccessToken {
        private PCAccessToken() {}
    }
    public static final PCAccessToken accessToken = new PCAccessToken();

    protected ArrayList<Command> commands;

    public ParallelCommand(final Command ... commands) {
        this.commands = new ArrayList<Command>();
        for (final Command cmd : commands) {
            addCommand(cmd);
        }
    }

    public void addCommand(final Command cmd) {
        if (!cmd.isFinished()) {
            commands.add(cmd);
            for(Subsystem ss : cmd.getRequirements()) {
                addRequirements(ss);
            }
        }
    }

    @Override
    public void init() {
        for (final Command cmd : commands) {
            cmd.init();
        }
    }

    @Override
    public void execute() {
        for (final Command cmd : commands) {
            if(cmd.isFinished()){
                cmd.real_end(accessToken);
            } else {
                cmd.execute();
            }
        }
    }

    @Override
    public void end() {
        for(Command cmd : commands) {
            cmd.real_end(accessToken);
        }
    }

    @Override
    public abstract boolean isFinished();
}