// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.BasePilotableConstantes;
import frc.robot.subsystems.BasePilotable;

public class TournerPID extends CommandBase {
  BasePilotable basePilotable;
  double angle;
  double voltage;
  double angleDepart;
  ProfiledPIDController pid;

  public TournerPID(double angle, BasePilotable basePilotable) {
    this.basePilotable = basePilotable;
    this.angle = angle;
    addRequirements(basePilotable);
  }

  @Override
  public void initialize() {

    //Créer le PID pour tourner
    pid = new ProfiledPIDController(BasePilotableConstantes.kPTourner, 0, 0,
        new TrapezoidProfile.Constraints(BasePilotableConstantes.maxVitesseTourner, BasePilotableConstantes.kMaxAccelerationTourner));
    basePilotable.setBrakeEtRampTeleop(false);
    pid.setGoal(angle);
    pid.setTolerance(5);
    angleDepart = basePilotable.getAngle();
  }

  @Override
  public void execute() {
    //Tourner
    voltage = pid.calculate(basePilotable.getAngle()) + BasePilotableConstantes.feedforwardtourner.calculate(pid.getSetpoint().velocity);
      basePilotable.autoConduire(-voltage, voltage);
  }

  @Override
  public void end(boolean interrupted) {
    
    voltage = 0;
  
    basePilotable.stop();
  }

  @Override
  public boolean isFinished() {
    //S'arrêter après avoir atteint la cible
    return pid.atGoal();
  }
}
