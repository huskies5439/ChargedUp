// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Bras;

//Cette commande remplie deux fonctions 
//1 : Reset l'encodeur si le détecteur magnétique détecte un aimant
//2 : Si l'encodeur-mat est positif (donc mat en extension), il faut appliquer un léger voltage pour éviter de descendre avec la gravité

public class BrasBackground extends CommandBase {
  Bras bras;
  

  public BrasBackground(Bras bras) {

    this.bras = bras;
    addRequirements(bras);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    
//Reset si détecteur magnétique



//Applique un voltage constant si l'encodeur >0
// Le voltage est à trouver, mais surement vraiment faible, genre 1 V

  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
