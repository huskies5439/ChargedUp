// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.BrasConstants;
import frc.robot.subsystems.Coude;

public class CoudeAuto extends CommandBase {

  Coude coude;
  double rotation;
  double voltage;

  ProfiledPIDController pid = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(0.1,0.1));

  public CoudeAuto(double rotationCible, Coude coude) {
    this.coude = coude;
    this.rotation = rotationCible;
    addRequirements(coude);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pid.setTolerance(5);
    rotation = MathUtil.clamp(rotation, 0, BrasConstants.kMaxCoude);
    pid.setGoal(rotation);


  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    voltage = pid.calculate(coude.getPosition());
    coude.setVoltage(voltage);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    coude.stop();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return pid.atGoal();
  }
}
