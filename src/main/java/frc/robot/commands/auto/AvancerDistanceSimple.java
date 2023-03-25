// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;

public class AvancerDistanceSimple extends CommandBase {
  double distance;
  BasePilotable basePilotable;
  double positionDepart;
  double voltage;
  /** Creates a new AvancerDistanceSimple. */
  public AvancerDistanceSimple(double distance, double voltage, BasePilotable basePilotable) {
    this.distance = distance;
    this.basePilotable = basePilotable;
    //this.voltage = voltage;
    addRequirements(basePilotable);

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    positionDepart = basePilotable.getPosition();
    basePilotable.setBrakeEtRampTeleop(false);
    voltage = 2.5*Math.signum(distance);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    basePilotable.autoConduire(voltage, voltage);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    basePilotable.stop();
    voltage = 0;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(basePilotable.getPosition() -positionDepart) > Math.abs(distance);
  }
}
