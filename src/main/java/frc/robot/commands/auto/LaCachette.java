// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//Félix Tremblay

package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Pince;

public class LaCachette extends SequentialCommandGroup {

  public LaCachette(BasePilotable basePilotable, Echelle echelle, Coude coude, Pince pince) {

    //Créer la trajectoire
    PathPlannerTrajectory laCachette = basePilotable.creerTrajectoire("LaCachette", true);
    addCommands(
      //Initialiser le robot pour l'autonome
      new InstantCommand(()-> basePilotable.placerRobotPositionInitial(laCachette)),
      new InstantCommand(()-> basePilotable.setBrakeEtRampTeleop(false)),

      //Placer un cube
      new AutoPlacer(false, echelle, coude, pince, basePilotable),

      //Sortir de la zone de départ et se cacher
      basePilotable.ramsete(laCachette)

    );
  }
}
