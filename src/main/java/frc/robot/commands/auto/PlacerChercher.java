// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Pince;

public class PlacerChercher extends SequentialCommandGroup {
  
  public PlacerChercher(boolean placerCone, boolean chercherCone, Echelle echelle, Coude coude, Pince pince ,BasePilotable basePilotable) {
    
    double distanceDepart;
    if (placerCone) {
      distanceDepart = 0.4;
    }
    
    else {
      distanceDepart = 0.6;
    }
    
    addCommands(
      //Attraper le cone
      new InstantCommand(pince::fermer).andThen(new WaitCommand(0.2)),

      //Lever le bras
      new WaitCommand(1), //new BrasAutoAvecCheck(Cible.kHaut, echelle, coude), 

      //Avancer en levant le bras à sa position finale
      new AvancerDistancePID(distanceDepart, basePilotable),

      //Ouvrir la pince
      new InstantCommand(pince::ouvrir).andThen(new WaitCommand(0.2)),

      //Reculer
      new AvancerDistancePID(-distanceDepart, basePilotable),

      //Baisser le bras
      new WaitCommand(1), //new BrasAutoAvecCheck(Cible.kBas, echelle, coude),

      //Chercher Cube/Cone (Utiliser un boolean pour avoir si c un cone ou un cube pis dire que true c un trajet et false un autre)
      new WaitCommand(1)

      //Surrement qu'il ne restera plus de temp
    );
  }
}
