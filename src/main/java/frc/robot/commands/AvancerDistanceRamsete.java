// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BasePilotable;

public class AvancerDistanceRamsete extends SequentialCommandGroup {
  BasePilotable basePilotable;
  double distance;
  double PoseDepart;
  /** Creates a new AvancerDistance. */
  public AvancerDistanceRamsete(double distance, BasePilotable basePilotable ) {
  this.basePilotable = basePilotable;
  this.distance = distance;

    addRequirements(basePilotable);
    //PathPlannerTrajectory trajet; //= basePilotable.creerTrajectoirePoint(basePilotable.getPose().getX()+distance,
                                 // basePilotable.getPose().getY(),0); //ou angle = 180 ???
    addCommands(
      new InstantCommand(() -> basePilotable.setBrakeEtRampTeleop(false)),

      basePilotable.ramsete(basePilotable.creerTrajectoirePoint(basePilotable.getPose().getX()+distance,
      basePilotable.getPose().getY(),0)),

      new InstantCommand(() -> basePilotable.setBrakeEtRampTeleop(true))
      );
    
  }
}
