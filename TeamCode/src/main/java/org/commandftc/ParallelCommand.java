package org.commandftc;

import java.util.ArrayList;

/**
 * This class represents a parallel command, where multiple commands
 * are executed at the same time. This class is abstract, but there are three
 * concrete subclasses. See each of them for description.
 * @see ParallelFirstCommand
 * @see ParallelSpecifiedCommand
 * @see ParallelWaitCommand
 */
public abstract class ParallelCommand extends Command {
    /**
     * @see AccessTiken
     */
    private static final class PCAccessToken extends AccessToken {
        private PCAccessToken() {}
    }

    /**
     * @see AccessToken
     */
    public static final PCAccessToken accessToken = new PCAccessToken();

    /**
     * Stores the list of commands to be run simutaneously.
     */
    protected ArrayList<Command> commands;

    /**
     * Creates a ParallelCommand and add all commands.
     */
    public ParallelCommand(final Command ... commands) {
        this.commands = new ArrayList<Command>();
        for (final Command cmd : commands) {
            addCommand(cmd);
        }
    }

    /**
     * Add a command to the list.
     */
    public void addCommand(final Command cmd) {
        if (!cmd.isFinished()) {
            commands.add(cmd);
            for(Subsystem ss : cmd.getRequirements()) {
                addRequirements(ss);
            }
        }
    }

    /**
     * Initializes all commands in the list.
     */
    @Override
    public void init() {
        for (final Command cmd : commands) {
            cmd.real_init(accessToken);
        }
    }

    /**
     * Executes all commands.
     */
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

    /**
     * Ends all commands.
     */
    @Override
    public void end() {
        for(Command cmd : commands) {
            cmd.real_end(accessToken);
        }
    }

    /**
     * Since each subclasses have different "finished" policy, 
     * this is overriden in each subclasses.
     */
    @Override
    public abstract boolean isFinished();
}