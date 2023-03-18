// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import java.nio.file.Path;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Pince;

public class AutoConeCoop extends SequentialCommandGroup {
  
  public AutoConeCoop(BasePilotable basePilotable, Echelle echelle, Coude coude, Pince pince) {
    PathPlannerTrajectory coopConeChercherCone = basePilotable.creerTrajectoire("CoopConeChercherCone", true);


    addCommands(
      new AutoPlacer(echelle, coude, pince),
      new InstantCommand(()-> basePilotable.placerRobotPositionInitial(coopConeChercherCone)) 
    );
  }
}
