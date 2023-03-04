// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//Théo

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pince extends SubsystemBase { 
  private DoubleSolenoid pincePiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, 6, 5);
  private WPI_TalonSRX pinceMoteur = new WPI_TalonSRX(7);
  private DigitalInput lightBreak = new DigitalInput(7);
  private boolean capteurArmer = true;
  private boolean ouverturePiston;

  public Pince() {
    pinceMoteur.setInverted(false);
    pinceMoteur.setNeutralMode(NeutralMode.Brake);
    
    ouvrirPiston();
    stopMoteur();
  }

  @Override
  public void periodic() {}

  //Pince pneumatique
  public void ouvrirPiston() {
    pincePiston.set(Value.kForward);
    ouverturePiston = true;
  }

  public void fermerPiston() {
    pincePiston.set(Value.kReverse);
    ouverturePiston = false;
  }

  public boolean getOuverturePiston() {
    return ouverturePiston;
  }

  public void togglePincePiston() {
    if (ouverturePiston) {
      fermerPiston();
    } else {
      ouvrirPiston();
    }
  }

  //Pince motoriser
  public void ouvrirMoteur() {
    pinceMoteur.setVoltage(12);
  }

  public void fermerMoteur() {
    pinceMoteur.setVoltage(-12);
  }

  public void stopMoteur() {
    pinceMoteur.setVoltage(0);
  }

  //Capteur pas bloqué true capteur bloqué false
  public boolean getFaisceau() {
    return lightBreak.get();
  }

  public boolean getArmer() {
    return capteurArmer;
  }

  public void setArmer(boolean estActif) {
    capteurArmer = estActif;
  }
}