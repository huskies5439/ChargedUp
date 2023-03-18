// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BasePilotable;

public class AvancerDistance extends SequentialCommandGroup {
  BasePilotable basePilotable;
  double cible;
  double PoseDepart;
  /** Creates a new AvancerDistance. */
  public AvancerDistance(BasePilotable basePilotable, double cible) {
  this.basePilotable = basePilotable;
  this.cible = cible;
    addRequirements(basePilotable);
    PathPlannerTrajectory trajet = basePilotable.creerTrajectoirePoint(1,basePilotable.getPose().getY(),0);
    addCommands(
      new InstantCommand(() -> basePilotable.setBrakeEtRampTeleop(false)),

      basePilotable.ramsete(trajet),

      new InstantCommand(() -> basePilotable.setBrakeEtRampTeleop(true))
      );
    
  }
}
