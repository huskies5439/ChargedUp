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

    boolean etatPasse=true;
    boolean etatActuel=true;

  public PincerAuto(Pince pince) {
   this.pince = pince;
   addRequirements(pince);
  }
  

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    etatPasse=true;
    etatActuel=true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Il faut associer la valeur du faisceau avec etat actuel

    //si la pince est armé et le faisceau est bloqué, on ferme la pince et désarme la pince
     
    

    //si le capteur passe de bloqué à débloqué, on arme la pince et ouvre la pince (au cas où elle ne soit pas fermée)
    
    //Etat passé = état actuel
}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}

