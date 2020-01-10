package org.commandftc;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Gp {
    private Gamepad gamepad;
    public Gp(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    public Trigger a() {
        return new Trigger(() -> gamepad.a);
    }

    public Trigger b() {
        return new Trigger(() -> gamepad.b);
    }

    public Trigger back() {
        return new Trigger(() -> gamepad.back);
    }

    public Trigger dpad_down() {
        return new Trigger(() -> gamepad.dpad_down);
    }

    public Trigger dpad_up() {
        return new Trigger(() -> gamepad.dpad_up);
    }

    public Trigger dpad_left() {
        return new Trigger(() -> gamepad.dpad_left);
    }

    public Trigger dpad_right() {
        return new Trigger(() -> gamepad.dpad_right);
    }

    public Trigger left_bumper() {
        return new Trigger(() -> gamepad.left_bumper);
    }

    public Trigger left_stick_button() {
        return new Trigger(() -> gamepad.left_stick_button);
    }

    public Trigger right_bumper() {
        return new Trigger(() -> gamepad.right_bumper);
    }

    public Trigger right_stick_button() {
        return new Trigger(() -> gamepad.right_stick_button);
    }

    public Trigger x() {
        return new Trigger(() -> gamepad.x);
    }

    public Trigger y() {
        return new Trigger(() -> gamepad.y);
    }

    public double left_stick_x() { return gamepad.left_stick_x; }
    public double left_stick_y() { return gamepad.left_stick_y; }
    public double right_stick_x() { return gamepad.right_stick_x; }
    public double right_stick_y() { return gamepad.right_stick_y; }

    public double left_trigger() {return gamepad.left_trigger;}
    public double right_trigger() {return gamepad.right_trigger;}
}