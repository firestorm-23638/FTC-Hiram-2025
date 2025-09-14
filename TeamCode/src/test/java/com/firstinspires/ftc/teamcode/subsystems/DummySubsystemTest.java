package com.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class DummySubsystemTest {
    private MockSubsystem subsystem;
    private CommandScheduler scheduler;
    private Servo servo;


    @BeforeEach
    void setupTests() {
        HardwareMap map = mock(HardwareMap.class);
        this.servo = mock(Servo.class);
        when(map.get(eq(Servo.class), eq("servo"))).thenReturn(servo);

        subsystem = new MockSubsystem(map);
        scheduler = CommandScheduler.getInstance();

        scheduler.registerSubsystem(subsystem);
    }


    @Test
    void testLowerbound() {
        scheduler.enable();

        scheduler.schedule(subsystem.goToPos(-1));
        scheduler.run();

        verify(servo, times(1)).setPosition(0);

    }

    @Test
    void testUpperbound() {
        scheduler.enable();

        scheduler.schedule(subsystem.goToPos(200));
        scheduler.run();

        verify(servo, times(1)).setPosition(180);

    }






}
