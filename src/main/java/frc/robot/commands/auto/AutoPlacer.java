// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.Cible;
import frc.robot.commands.BrasAuto;
import frc.robot.commands.BrasAutoAvecCheck;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Pince;

public class AutoPlacer extends SequentialCommandGroup {
  
  public AutoPlacer(boolean cone, Echelle echelle, Coude coude, Pince pince, BasePilotable basePilotable) {

    //change la distance dépendament du trajet selectionner
    double distanceDepart;
    if(cone) {
      distanceDepart = 0.35;
    }
    
    else {
      distanceDepart = 0.55;
    }
    
    addCommands(
      //Attraper le cone
      new InstantCommand(pince::fermer),

      //Lever le coude
      new BrasAutoAvecCheck(Cible.kMilieu, echelle, coude),

      //Avancer en levant le bras à sa position finale
      new AvancerDistanceSimple(distanceDepart, 2.5, basePilotable).alongWith(new BrasAutoAvecCheck(Cible.kHaut, echelle, coude)),

      //new WaitCommand(1),

      //Ouvrir la pince
      new InstantCommand(pince::ouvrir),

      //Reculer
      new AvancerDistanceSimple(-distanceDepart, 2.5, basePilotable),

      //Descendre le bras
      new BrasAuto(Cible.kRentrer, echelle, coude)

    );
  }
}