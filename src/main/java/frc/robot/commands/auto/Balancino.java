// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.BasePilotableConstantes;
import frc.robot.commands.balancer.Balancer;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;
import frc.robot.subsystems.Pince;


public class Balancino extends SequentialCommandGroup {
//Trajet du centre. Place un objet, tourne, balance

  public Balancino(boolean cone, BasePilotable basePilotable, Echelle echelle, Coude coude, Pince pince) {
    addCommands(
      new AutoPlacer(cone, echelle, coude, pince, basePilotable),
      new TournerPID(180, basePilotable),
      new Balancer(basePilotable)
    );
  }
}
