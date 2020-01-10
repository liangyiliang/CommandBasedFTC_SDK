package org.commandftc;

import java.util.Objects;
import java.util.function.BooleanSupplier;

public class Trigger {
    private BooleanSupplier isActive;
    public Trigger(BooleanSupplier isActive) {
        this.isActive = isActive;
    }
    public Trigger() {
        this.isActive = () -> false;
    }

    public boolean get() {
        return isActive.getAsBoolean();
    }

    public Trigger whenActive(final Command command) {
        Objects.requireNonNull(command);

        CommandScheduler.addButton(new Runnable() {
            private boolean lastPressed = get();

            @Override
            public void run() {
                boolean nowPressed = get();
                if(!lastPressed && nowPressed) {
                    CommandScheduler.scheduleCommand(command);
                }
                lastPressed = nowPressed;
            }
        });
        return this;
    }

    public Trigger whenPressed(Command command) {
        return whenActive(command);
    }

    public Trigger whileActiveContinuous(final Command command) {
        Objects.requireNonNull(command);

        CommandScheduler.addButton(new Runnable() {
            private boolean lastPressed = get();
            @Override 
            public void run() {
                boolean nowPressed = get();
                if(nowPressed) {
                    CommandScheduler.scheduleCommand(command);
                } else if(lastPressed) {
                    CommandScheduler.unscheduleCommand(command);
                }
                lastPressed = nowPressed;
            }
        });

        return this;
    }

    public Trigger whileHeld(Command command) { 
        return whileActiveContinuous(command); 
    }

    public Trigger whileActiveOnce(final Command command) {
        Objects.requireNonNull(command);

        CommandScheduler.addButton(new Runnable() {
            private boolean lastPressed = get();

            @Override
            public void run() {
                boolean nowPressed = get();
                if(!lastPressed && nowPressed){
                    CommandScheduler.scheduleCommand(command);
                } else if (lastPressed && !nowPressed) {
                    CommandScheduler.unscheduleCommand(command);
                }
                lastPressed = nowPressed;
            }
        });
        return this;
    }

    public Trigger whenHeld(Command command) {
        return whileActiveOnce(command);
    }

    public Trigger whenInactive(final Command command) {
        Objects.requireNonNull(command);

        CommandScheduler.addButton(new Runnable() {
            private boolean lastPressed = get();
            @Override
            public void run() {
                boolean nowPressed = get();
                if(lastPressed && !nowPressed) {
                    CommandScheduler.scheduleCommand(command);
                }
                lastPressed = nowPressed;
            }
        });
        return this;
    }

    public Trigger whenReleased(Command command) {
        return whenInactive(command);
    }

    public Trigger toggleWhenActive(final Command command) {
        Objects.requireNonNull(command);

        CommandScheduler.addButton(new Runnable() {
            private boolean lastPressed = get();

            @Override
            public void run() {
                boolean nowPressed = get();
                if(!lastPressed && nowPressed) {
                    if(CommandScheduler.isScheduled(command)) {
                        CommandScheduler.unscheduleCommand(command);
                    } else {
                        CommandScheduler.scheduleCommand(command);
                    }
                }
                lastPressed = nowPressed;
            }
        });
        return this;
    }

    public Trigger toggleWhenPressed(Command command) {
        return toggleWhenActive(command);
    }

    public Trigger cancelWhenActive(final Command command) {
        Objects.requireNonNull(command);
        CommandScheduler.addButton(new Runnable() {
            private boolean lastPressed = get();

            @Override
            public void run() {
                boolean nowPressed = get();
                if (!lastPressed && nowPressed) {
                    CommandScheduler.unscheduleCommand(command);
                }
                lastPressed = nowPressed;
            }
        });
        return this;
    }

    public Trigger cancelWhenPressed(Command command) {
        return cancelWhenActive(command);
    }

    public Trigger and(Trigger other) {
        return new Trigger(() -> (get() && other.get()));
    }

    public Trigger or(Trigger other) {
        return new Trigger(() -> (get() || other.get()));
    }

    public Trigger not() {
        return new Trigger(() -> !get());
    }
}