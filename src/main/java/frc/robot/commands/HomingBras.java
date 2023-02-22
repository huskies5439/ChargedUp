// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Bras;


public class HomingBras extends SequentialCommandGroup {
  //Sert à mettre le bras à 0 entre les matchs.
  //Présentement ne fait que le Mat. Si le coude reste dépendant du bras, ne rien changer
  //Si le coude devient un système indépendant, on pourra ajouter la séquence après celle-ci

  //À lier à un bouton incongru, genre select

  public HomingBras(Bras bras) {
    
    addCommands(
      //on recule jusqu'à l'interrupteur magnétique et on reset l'encodeur
      new RunCommand(()-> bras.setVoltage(-1), bras).until(bras::getDetecteurMagnetic), 
      new InstantCommand(bras::stop),
      new InstantCommand(bras::resetEncodeur),
      new WaitCommand(0.5),

      //On réavance, puis on recule à nouveau vers l'interrupteur pour valider.
      new RunCommand(()-> bras.setVoltage(1), bras).withTimeout(1),
      new RunCommand(()-> bras.setVoltage(-1), bras).until(bras::getDetecteurMagnetic),
      new InstantCommand(bras::stop),
      new InstantCommand(bras::resetEncodeur)

    );
  }
}
