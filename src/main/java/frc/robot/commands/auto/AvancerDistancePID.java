// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.BasePilotableConstantes;
import frc.robot.subsystems.BasePilotable;

public class AvancerDistancePID extends CommandBase {
  /** Creates a new AvancerDistancePID. */
  BasePilotable basePilotable;
  double distance;
  double voltage;
  double positionDepart;
  ProfiledPIDController pid;


  public AvancerDistancePID(double distance, BasePilotable basePilotable) {
    this.basePilotable = basePilotable;
    this.distance = distance;
    addRequirements(basePilotable);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pid = new ProfiledPIDController(12, 0, 0,
        new TrapezoidProfile.Constraints(BasePilotableConstantes.maxVitesse, BasePilotableConstantes.maxAcceleration) );
    basePilotable.setBrakeEtRampTeleop(false);
    pid.setGoal(distance);
    pid.setTolerance(0.03);
    positionDepart = basePilotable.getPosition();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      voltage = pid.calculate(basePilotable.getPosition()-positionDepart) + BasePilotableConstantes.feedforward.calculate(pid.getSetpoint().velocity);
      basePilotable.autoConduire(voltage, voltage);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    voltage = 0;
  
    basePilotable.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return pid.atGoal();
  }
}
