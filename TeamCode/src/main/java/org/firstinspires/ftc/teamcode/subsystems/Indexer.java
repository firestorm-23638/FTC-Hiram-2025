package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.Config;

/*
    TODO: Fix issue where spamming bumpers makes current tick offset
 */

// can only move counter clockwise
// https://www.gobilda.com/5203-series-yellow-jacket-planetary-gear-motor-19-2-1-ratio-24mm-length-8mm-rex-shaft-312-rpm-3-3-5v-encoder
public class Indexer extends SubsystemBase {
    private final SpindexerMotor motor;
    private int intakeIndex = 0;
    private static int sixtyDegreeRevolutions = 0;
    private final IntakeIndex[] slots = {new IntakeIndex(), new IntakeIndex(), new IntakeIndex()};
    private static final int EPS = 25;
    private final Telemetry telemetry;
    public Indexer(HardwareMap hw, Telemetry telemetry) {
        motor = new SpindexerMotor(hw);
        this.telemetry = telemetry;
    }

    public void rotateToNearestIndex() {
        float closest = Float.POSITIVE_INFINITY;
        int closestIndex = 0;

        float ticksAtZero = motor.getRevolutions() * SpindexerMotor.encoderResolution;
        float ticksAtOne = ticksAtZero + SpindexerMotor.encoderResolution120;
        float ticksAtTwo = ticksAtOne + SpindexerMotor.encoderResolution120;

        double current = motor.getCurrentTick();
        double closestTick = ticksAtZero;
        double closestDist = Math.abs(current - ticksAtZero);

        double d1 = Math.abs(current - ticksAtOne);
        if (d1 < closestDist) {
            closestDist = d1;
            closestTick = ticksAtOne;
            closestIndex = 1;
        }

        double d2 = Math.abs(current - ticksAtTwo);
        if (d2 < closestDist) {
            closestDist = d2;
            closestTick = ticksAtTwo;
            closestIndex = 2;
        }

        intakeIndex = closestIndex;
        motor.rotateToTick(closestTick);
    }

    public void rotateToNearestSlot() {
        if (sixtyDegreeRevolutions != 0) {
            boolean dir = !(sixtyDegreeRevolutions > 0);
            motor.rotate60(dir);
            sixtyDegreeRevolutions = 0;
        }
    }

    public Command rotateToNearestIndexCmd(){
        return new InstantCommand(this::rotateToNearestSlot);
    }

    public SlotState getCurrentSlot() {
        return slots[intakeIndex].getState();
    }

    public double getTarget() {
        return motor.getTargetTick();
    }

    @Override
    public void periodic() {
        motor.rotate();

        if (motor.currentTick > motor.getTargetTick() - EPS
            && motor.getCurrentTick() < motor.getTargetTick() + EPS) {
            motor.clearErrors();
        }
        telemetry.addData("CurrentTick", motor.currentTick);
        telemetry.addData("target", motor.targetTick);
    }

    public void rotate120(boolean clockwise) {
        motor.rotate120(clockwise);
        if (clockwise) {
            intakeIndex = Util.modPlusOne(intakeIndex);
        } else {
            intakeIndex = Util.modMinusOne(intakeIndex);
        }
    }

    public void rotate60(boolean clockwise) {
        motor.rotate60(clockwise);
        if (clockwise) {
            sixtyDegreeRevolutions++;
        } else {
            sixtyDegreeRevolutions--;
        }
        if (sixtyDegreeRevolutions == 2) {
            sixtyDegreeRevolutions = 0;
            intakeIndex = Util.modPlusOne(intakeIndex);
        } else if (sixtyDegreeRevolutions == -2) {
            sixtyDegreeRevolutions = 0;
            intakeIndex = Util.modMinusOne(intakeIndex);
        }
    }
    public int getIntakeIndex() {
        return this.intakeIndex;
    }


    public CommandBase rotate120Cmd(boolean clockwise){
        return new InstantCommand(()->this.rotate120(clockwise));
    }

    public  CommandBase rotate60Cmd(boolean clockwise) {
        return new InstantCommand(()->this.rotate60(clockwise));
    }

    public CommandBase nearTarget() {
        return new WaitUntilCommand(() -> motor.currentTick > motor.getTargetTick() - EPS
            && motor.getCurrentTick() < motor.getTargetTick() + EPS
            && motor.getVelocity() < 100);
    }

    public CommandBase goToBestStartingLocationCmd() {
        return new InstantCommand(() -> {
            int index = getBestStartingLocation();
            moveToSlot(index);
        });
    }

    public CommandBase rotateRightCmd(){
        return new InstantCommand(() -> motor.setTargetPosition((int) motor.targetTick + 100));
    }

    public CommandBase rotateLeftCmd(){
        return new InstantCommand(() -> motor.setTargetPosition((int) motor.targetTick - 100));
    }

    public CommandBase setSlotColor(String colors) {
        return new InstantCommand(() -> setSlots(colors));
    }

    public CommandBase reset() {
        return new InstantCommand(() -> {
            motor.reset();
            intakeIndex = 0;
            sixtyDegreeRevolutions = 0;
        });
    }

    public CommandBase goToSlotCmd(int slot) {
        return new InstantCommand(() -> moveToSlot(slot));
    }


    // Returns the index of the closest available spindexer slot
    public int findNextAvailableSlot() throws RuntimeException {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].isEmpty()) {
                return i;
            }
        }
        throw new RuntimeException("No available slots");
    }
    // Rotates spindexer to the closest available slot
    public void moveToSlot(int slot) {
        if (slot > 2 || slot < 0)
            throw new IllegalArgumentException("Slot must be between 0 and 2");

        int indexDelta = slot - intakeIndex;
        if (slot == 0 && intakeIndex == 2) {
            indexDelta = 1;
        } else if (slot == 2 && intakeIndex == 0) {
            indexDelta = -1;
        }

        boolean dir = indexDelta > 0;
        for (int i = 0; i < Math.abs(indexDelta); i++) {
            motor.rotate120(dir);
        }

        intakeIndex = slot;

    }
    // Finds the next available spindexer slot and rotates to it
    public void findEmptyAndMove() {
        int slot = findNextAvailableSlot();
        moveToSlot(slot);
    }

    public char getStateLetter(SlotState slotState){
        if(slotState.equals(SlotState.GREEN)){
            return 'G';
        }
        if(slotState.equals(SlotState.PURPLE)){
            return 'P';
        }
        return 'E';

    }

    public int getBestStartingLocation(){
        char[] balls = {getStateLetter(slots[0].getState()),
                getStateLetter(slots[1].getState()),
                getStateLetter(slots[2].getState()),
                getStateLetter(slots[0].getState()),
                getStateLetter(slots[1].getState()),
                getStateLetter(slots[2].getState()),
        };

        char[] motif = Config.getMotif();
        for(int i = 0; i < balls.length; i++){
            if(i == 4)
                return 0;

            if(balls[i] == motif[0] && balls[i+1] == motif[1] && balls[i+2] == motif[2])
                return i;
        }
        return 0;
    }


    // Ejects all three balls from the spindexer
    public void ejectAll() {
        int bestStartingLocation = getBestStartingLocation();
        moveToSlot(bestStartingLocation);
    }

    public void setSlots(String pattern) {
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (c == 'P')
                slots[i].state = SlotState.PURPLE;

            if (c == 'G')
                slots[i].state = SlotState.GREEN;

            if (c == 'E')
                slots[i].state = SlotState.EMPTY;
        }
    }

    public enum SlotState {EMPTY, GREEN, PURPLE}

    public static class IntakeIndex {
        private SlotState state;

        public SlotState getState() {
            return state;
        }

        public boolean isEmpty() {
            return (state == SlotState.EMPTY);
        }
    }

    public static class SpindexerMotor {
        private double currentTick;
        private double targetTick;
        private final DcMotorEx motor;
        public final static float encoderResolution = 4096f;
        public final static float encoderResolution120 = encoderResolution / 3;
        public final static float encoderResolution60 = encoderResolution120 / 2;
        public final static float encoderResolution240 = encoderResolution120 * 2;
        public final static double kP = 0.0005960185250345786;
        public final static double kD = 0.0000123458;

        private long lastTime = 0;
        private double lastError = 0;
        private double errors = 0;
        /*
        float err = encoderResolution120 - motor.getCurrentPosition();
        float power = kP * err;
        int currentRevolutions = motor.getRevolutions();
        int startDeg = (int) (encoderResolution * currentRevolutions);
        int end120 = startDeg + (int) encoderResolution120;
        int end60 = startDeg + (int) encoderResolution60;

        */
        public SpindexerMotor(HardwareMap hw) {
            motor = hw.get(DcMotorEx.class, "spindexerMotor");
            lastTime = System.nanoTime();
        }

        // Returns true if the motor is busy
        public boolean isBusy() {
            return motor.isBusy();
        }

        // Rotates toward the target position
        public void rotate() {
            long now = System.nanoTime();
            double dt = (now - lastTime) / 1e9;

            currentTick = -motor.getCurrentPosition();
            double err =  currentTick - targetTick;
            double velError = -this.getVelocity() - 0;

            double power = -(kP * err) - (kD * velError);

            motor.setPower(power + Math.signum(power)*0.01);
        }

        public void clearErrors() {
            errors = 0;
        }

        public void setTargetPosition(double targetTick){
            this.targetTick = targetTick;
        }
        /**
         * Rotates the motor 120 degrees clockwise or counter-clockwise
         * @param clockwise Turn direction
         */
        public void rotate120(boolean clockwise) {
            if (motor.isBusy()) {
                throw new RuntimeException("Motor is busy");
            }
            if (clockwise) {
                targetTick = targetTick + encoderResolution120;
            } else {
                targetTick = targetTick - encoderResolution120;
            }
        }
        /**
         * Rotates the motor 60 degrees clockwise or counter-clockwise
         * @param clockwise Turn direction
         */
        public void rotate60(boolean clockwise) {
            if (motor.isBusy()) {
                return;
            }
            if (clockwise) {
                targetTick = targetTick + encoderResolution60;
            } else {
                targetTick = targetTick - encoderResolution60;
            }
        }

        public void rotateToTick(double tick) {
            targetTick = tick;
        }

        public void reset(){
            targetTick = 0;
            currentTick = 0;
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        /**
         *
         * @return int (the number of revolutions the motor has made)
         */
        public int getRevolutions() {
            return (int) (-motor.getCurrentPosition() / encoderResolution);
        }

        public double getCurrentTick(){
            return this.currentTick;
        }
        public double getTargetTick(){
            return  this.targetTick;
        }

        public double getVelocity() {
            return motor.getVelocity();
        }
    }
}
