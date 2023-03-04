// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Coude;
import frc.robot.subsystems.Echelle;


public class HomingBras extends SequentialCommandGroup {
  //Sert à mettre le bras à 0 entre les matchs.
  //Présentement ne fait que le Mat. Si le coude reste dépendant du bras, ne rien changer
  //Si le coude devient un système indépendant, on pourra ajouter la séquence après celle-ci

  //À lier à un bouton incongru, genre select

  public HomingBras(Echelle echelle, Coude coude) {
    addCommands(
 //Partie échelle
      //Avance un peu
      new RunCommand(()-> echelle.setVoltage(2), echelle).withTimeout(0.5),
    
      //on recule jusqu'à l'interrupteur magnétique et on reset l'encodeur
      new RunCommand(()-> echelle.setVoltage(-1), echelle).until(echelle::getDetecteurMagnetique), 
      new InstantCommand(echelle::stop),
      new InstantCommand(echelle::resetEncodeur),

      //On réavance, puis on recule à nouveau vers l'interrupteur pour valider.
      new RunCommand(()-> echelle.setVoltage(1), echelle).withTimeout(0.5),
      new RunCommand(()-> echelle.setVoltage(-1), echelle).until(echelle::getDetecteurMagnetique),
      new InstantCommand(echelle::stop),
      new InstantCommand(echelle::resetEncodeur),

 //Partie coude 
      //Avance un peu
      new RunCommand(()-> coude.setVoltage(2), coude).withTimeout(0.5),

      //on recule jusqu'à l'interrupteur magnétique et on reset l'encodeur
      new RunCommand(()-> coude.setVoltage(-1), coude).until(coude::getLimitSwitch),
      new InstantCommand(coude::stop),
      new InstantCommand(coude::resetEncodeur),

      //On réavance, puis on recule à nouveau vers l'interrupteur pour valider.
      new RunCommand(()-> coude.setVoltage(1), coude).withTimeout(0.5),
      new RunCommand(()-> coude.setVoltage(-1), coude).until(coude::getLimitSwitch),
      new InstantCommand(coude::stop),
      new InstantCommand(coude::resetEncodeur)
    );
  }
}