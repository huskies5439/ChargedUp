// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.balancer;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BasePilotable;

//////POTENTIEL DÉSUET ??????/////////
public class DetecterPenteDescendante extends CommandBase {
  
  BasePilotable basePilotable;
  double anglePasse;
  double angleActuel;
  double deltaAngle;
  double voltage;
  int compteur;
  LinearFilter filtre = LinearFilter.singlePoleIIR(0.1, 0.02);

  boolean detecterPenteActif;

  public DetecterPenteDescendante(double voltage, BasePilotable basePilotable, boolean reculer) {
    this.basePilotable = basePilotable;

    if (reculer == true) {
      voltage *= -1;
    }

    this.voltage = voltage;

    addRequirements(basePilotable);
  }

  @Override
  public void initialize() {
    basePilotable.setBrakeEtRampTeleop(false);
    angleActuel = 0;
    anglePasse = 0;

    compteur = 0;

    detecterPenteActif = true;
  }

  @Override
  public void execute() {
    angleActuel = Math.abs(basePilotable.getPitch());

    deltaAngle = angleActuel - anglePasse;

     deltaAngle = filtre.calculate(deltaAngle);

    if (angleActuel > 13 && deltaAngle < -0.03) {
        compteur = compteur + 1;
    }

    else {
      compteur = 0;
    }

    basePilotable.autoConduire(voltage, voltage);

    anglePasse = angleActuel;

    SmartDashboard.putBoolean("detecterpenteactif", detecterPenteActif);
  }

  @Override
  public void end(boolean interrupted) {
    basePilotable.stop();

    detecterPenteActif = false;
    SmartDashboard.putBoolean("detecterpenteactif", detecterPenteActif);
  }

  @Override
  public boolean isFinished() {
    return  compteur >= 5;
    
    //angleActuel > 12 && deltaAngle < -0.035;//-0.05
  }
}