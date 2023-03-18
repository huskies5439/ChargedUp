// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Pince;

public class AutoCubeRetourCone extends SequentialCommandGroup {
  
  public AutoCubeRetourCone(BasePilotable basePilotable, Echelle echelle, Coude coude, Pince pince) {
    PathPlannerTrajectory cubeChercherCone = basePilotable.creerTrajectoire("CubeChercherCone", true);
    PathPlannerTrajectory retourAvecCone = basePilotable.creerTrajectoire("RetourAvecCone", true);

    addCommands(
      new InstantCommand(() -> basePilotable.placerRobotPositionInitial(cubeChercherCone)),
      new InstantCommand(() -> basePilotable.setBrakeEtRampTeleop(false)),

      new AutoPlacer(false, echelle, coude, pince, basePilotable)

      //basePilotable.ramsete(cubeChercherCone).raceWith(new InstantCommand(pince::getFaisceau)),
      //basePilotable.ramsete(retourAvecCone)

    );
  }
}
