// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BasePilotable;

public class AutoReculerRamsete extends SequentialCommandGroup {
  
  public AutoReculerRamsete(BasePilotable basePilotable) {

    //fait reculer le robot avec un ramsete
    PathPlannerTrajectory reculer = basePilotable.creerTrajectoire("Reculer", true);

    addCommands(
      new InstantCommand(() -> basePilotable.placerRobotPositionInitial(reculer)),
      new InstantCommand(() -> basePilotable.setBrakeEtRampTeleop(false)),

      basePilotable.ramsete(reculer)

    );
  }
}