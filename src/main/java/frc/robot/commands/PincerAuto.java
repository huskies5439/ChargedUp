// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pince;

public class PincerAuto extends CommandBase {
    Pince pince;

    boolean capteurPasse;
    boolean capteurActuel;

    boolean etatPasse = true;
    boolean etatActuel = true;

  public PincerAuto(Pince pince) {
   this.pince = pince;
   addRequirements(pince);
  }
  
  @Override
  public void initialize() {
    etatPasse=true;
    etatActuel=true;
  }
  
  @Override
  public void execute() {

  etatActuel = pince.getFaisceau();
  
  if (pince.getArmer() && !etatActuel){
    pince.setArmer(false);
    pince.fermer();
  }

  if (!etatPasse && etatActuel){
    pince.setArmer(true);
    pince.ouvrir();
  }

  etatPasse = etatActuel;
  
}

  @Override
  public void end(boolean interrupted) {}
  
  @Override
  public boolean isFinished() {
    return false;
  }
}