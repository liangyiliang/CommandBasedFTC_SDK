package org.commandftc;
import java.util.*;

public final class CommandScheduler {

    /**
     * This class is used to emulate the C++ "friend" class features. See https://stackoverflow.com/questions/182278/is-there-a-way-to-simulate-the-c-friend-concept-in-java
     * @see AccessToken
     */
    public static final class CSAccessToken extends AccessToken {
        private CSAccessToken() { }
    }

    private static final CSAccessToken accessToken = new CSAccessToken();

    /**
     * Subsystem and Default Command
     */
    private static final Map<Subsystem, Command> mapSsDefCmd;

    private static final Set<Subsystem> subsystems;

    private static final LinkedList<Command> scheduledCommands;

    private static final Set<Runnable> buttons; 

    static {
        mapSsDefCmd = new LinkedHashMap<> ();
        subsystems = new HashSet<Subsystem>();
        scheduledCommands = new LinkedList<Command>();
        buttons = new LinkedHashSet<Runnable>();
    }

    public static void setDefaultCommand(Subsystem ss, Command cmd) {
        if(cmd.isFinished()) {
            throw new IllegalArgumentException("Default commands shouldn't be finished.");
        }
        if(!cmd.getRequirements().contains(ss)) {
            throw new IllegalArgumentException("Default commands must require the subsystem.");
        }
        mapSsDefCmd.put(ss, cmd);
    }

    public static Command getDefaultCommand(Subsystem ss) {
        return mapSsDefCmd.get(ss);
    }

    public static void registerSubsystem(Subsystem subsystem) {
        subsystems.add(subsystem);
    }

    public static void scheduleCommand(Command cmd) {
        if(cmd.isFinished()) {
            throw new IllegalArgumentException("Cannot schedule a finished command.");
        }
        if(!subsystems.containsAll(cmd.getRequirements())) {
            throw new IllegalArgumentException("Cannot schedule command because requirement not met.");
        }
        scheduledCommands.add(cmd);
        cmd.init();
    }

    public static void scheduleCommands(Command ... cmds) {
        for(Command cmd : cmds) {
            scheduleCommand(cmd);
        }
    }

    public static void addButton(Runnable button) {
        buttons.add(button);
    }

    public static void runOnce() {
        Set<Subsystem> usedSs = new HashSet<Subsystem>();
        ArrayList<Command> toRemove = new ArrayList<Command>();

        for(Command cmd : scheduledCommands) {
            if(cmd.isFinished()) {
                cmd.real_end(accessToken);
                toRemove.add(cmd);
            } else {
                usedSs.addAll(cmd.getRequirements());
                cmd.execute();
            }
        }

        scheduledCommands.removeAll(toRemove);

        for(Subsystem ss : subsystems) {
            if(!usedSs.contains(ss)) {
                Command defaultCommand = mapSsDefCmd.get(ss);
                if(defaultCommand != null) {
                    defaultCommand.execute();
                } else {
                    ss.periodic();
                }
            }
        }

        for(Runnable button : buttons) {
            button.run();
        }
    }

    public static void unscheduleCommand(Command command) {
        if(scheduledCommands.contains(command)) {
            command.real_end(accessToken);
            scheduledCommands.remove(command);
        }
    }

    public static boolean isScheduled(Command command) {
        return scheduledCommands.contains(command);
    }

    public static void unscheduleAll() {
        while(!scheduledCommands.isEmpty()) {
            unscheduleCommand(scheduledCommands.get(0));
        }
    }
}