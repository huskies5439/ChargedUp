// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.Cible;
import frc.robot.commands.BrasAutoAvecCheck;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Pince;

public class AutoPlacer extends SequentialCommandGroup {
  
  public AutoPlacer(boolean cone,Echelle echelle, Coude coude, Pince pince, BasePilotable basePilotable) {

    double distanceDepart;
    if(cone){
      distanceDepart = 0.4;
    }
    else{
      distanceDepart = 0.6;

    }
    
    
    addCommands(
      //Attraper le cone
      new InstantCommand(pince::fermer).andThen(new WaitCommand(0.2)),

      //Lever le coude
      new WaitCommand(1),//new BrasAutoAvecCheck(Cible.kHaut, echelle, coude),

      //Avancer en levant le bras à sa position finale
      new AvancerDistancePID(distanceDepart, basePilotable),
        

      //Ouvrir la pince
      new InstantCommand(pince::ouvrir).andThen(new WaitCommand(0.2)),

      //Reculer
      new AvancerDistancePID(-distanceDepart, basePilotable),

      //Descendre le bras
   
      new WaitCommand(1)
      //new BrasAutoAvecCheck(Cible.kBas, echelle, coude)
    );
  }
}