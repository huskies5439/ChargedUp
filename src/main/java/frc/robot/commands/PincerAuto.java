// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pince;

public class PincerAuto extends CommandBase {
    Pince pince;

    boolean etatPasse = true;
    boolean etatActuel = true;

  public PincerAuto(Pince pince) {
   this.pince = pince;
   addRequirements(pince);
  }
  
  @Override
  public void initialize() {
    etatPasse = true;
    etatActuel = true;
  
  }
  
  @Override
  public void execute() {

  etatActuel = pince.getFaisceau();
  
  // Si on permet au robot de pincer et qu'il y a rien dans la pince, ferme le piston et ne permet pas au robot de pincer.
  if (pince.getArmer() && !etatActuel){
    pince.setArmer(false);
    pince.fermer();
  }

  // Si le capteur infrarouge était pas activer dans le passé et est activer maintenant, ouvre le piston et permet au robot de pincer.
  if (!etatPasse && etatActuel){
    pince.setArmer(true);
    // pince.ouvrir();
  }
  // La valeur actuelle du capteur infrarouge devient maintenant celle du passé (la valeur actuelle est reset au début de la loop).
  etatPasse = etatActuel;
}

  @Override
  public void end(boolean interrupted) {}
  
  @Override
  public boolean isFinished() {
    return false;
  }
}