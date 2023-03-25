// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.Cible;
import frc.robot.commands.BrasAutoAvecCheck;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Pince;

public class JamesBande extends SequentialCommandGroup {
  
  public JamesBande(Echelle echelle, Coude coude, Pince pince, BasePilotable basePilotable) {
    
    PathPlannerTrajectory chercherConeBande = basePilotable.creerTrajectoire("ChercherConeBande", true);
    PathPlannerTrajectory retourConeBande = basePilotable.creerTrajectoire("RetourAvecConeBande", false);


    
    addCommands(
      new InstantCommand(() -> basePilotable.placerRobotPositionInitial(chercherConeBande)),
      new InstantCommand(() -> basePilotable.setBrakeEtRampTeleop(false)),

    //Placer un cone
    new AutoPlacer(false, echelle, coude, pince, basePilotable),

    
    basePilotable.ramsete(chercherConeBande),
    
    new ConditionalCommand(new WaitCommand(15),
                          new TournerPID(0,basePilotable), 
                          pince::getArmer),


    new ParallelCommandGroup(basePilotable.ramsete(retourConeBande), new BrasAutoAvecCheck(Cible.kMilieu, echelle, coude)),
    new BrasAutoAvecCheck(Cible.kHaut, echelle, coude),
    new InstantCommand(pince::ouvrir)


    );
  }
}
