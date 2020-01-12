package org.commandftc;

import java.util.LinkedList;

/**
 * A SequentialCommand is a composite command that 
 * runs the sub-commands sequentially. Ends when the last sub-command
 * ends.
 */
public class SequentialCommand extends Command {
    /**
     * @see AccessToken
     */
    private static final class SCAccessToken extends AccessToken {
        private SCAccessToken() {}
    }
    public static final SCAccessToken accessToken = new SCAccessToken();

    private LinkedList<Command> commands;

    public SequentialCommand() {
        this.commands = new LinkedList<Command>();
    }

    public SequentialCommand(Command ... commands) {
        this.commands = new LinkedList<Command>();
        for(Command cmd : commands) {
            addCommand(cmd);
            for(Subsystem ss : cmd.getRequirements()) {
                addRequirements(ss);
            }
        }
    }

    public void addCommand(Command cmd) {
        if(!cmd.isFinished()) {
            commands.add(cmd);
        }
    }

    @Override
    public SequentialCommand andThen(Command next) {
        addCommand(next);
        return this;
    }
    
    @Override
    public void init() {
        if(commands.isEmpty()) return;
        commands.getFirst().init();
    }

    @Override
    public void execute() {
        if(commands.isEmpty()) return;
        Command currCmd = commands.getFirst();
        if(currCmd.isFinished()) {
            currCmd.real_end(accessToken);
            commands.pop();
            // Jump to "A"
        } else {
            currCmd.execute();
            return;
        }
        

        // A
        if(commands.isEmpty()) return;
        // switch command
        currCmd = commands.getFirst();
        // run it if the new command is not finished.
        // if it's already finished, the next loop will handle it.
        if(!currCmd.isFinished()) {
            currCmd.execute();
        }
    }

    @Override
    public void end() {
        for(Command cmd : commands) {
            cmd.real_end(accessToken);
        }
    }

    @Override
    public boolean isFinished() {
        return commands.isEmpty() || commands.getLast().isFinished();
    }
}