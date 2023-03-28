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
  BasePilotable basePilotable;
  double distance;
  double voltage;
  double positionDepart;
  ProfiledPIDController pid;


  public AvancerDistancePID(double distance, BasePilotable basePilotable) {
    this.basePilotable = basePilotable;
    this.distance = distance;
    addRequirements(basePilotable);
  }

  @Override
  public void initialize() {

    //Créer le PID
    pid = new ProfiledPIDController(12, 0, 0,
        new TrapezoidProfile.Constraints(BasePilotableConstantes.maxVitesse, BasePilotableConstantes.maxAcceleration) );
    basePilotable.setBrakeEtRampTeleop(false);
    pid.setGoal(distance);
    pid.setTolerance(0.03);
    positionDepart = basePilotable.getPosition();
  }

  @Override
  public void execute() {
    //Bouger avec PID
      voltage = pid.calculate(basePilotable.getPosition()-positionDepart) + BasePilotableConstantes.feedforward.calculate(pid.getSetpoint().velocity);
      basePilotable.autoConduire(voltage, voltage);
  }
  
  @Override
  public void end(boolean interrupted) {
    voltage = 0;
    basePilotable.stop();
  }
  
  @Override
  public boolean isFinished() {
    //Le robot à atteint sa cible
    return pid.atGoal();
  }
}
