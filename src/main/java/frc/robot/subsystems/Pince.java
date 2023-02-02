// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pince extends SubsystemBase {         //
  private DoubleSolenoid pince = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 2);
  private DigitalInput lightBreak = new DigitalInput(0);
  ColorSensorV3 capteurCouleur = new ColorSensorV3(I2C.Port.kOnboard);
  private final ColorMatch colorMatcher = new ColorMatch();
  private final Color kCouleurCone = new Color(0.359, 0.485, 0.158);
  private final Color kCouleurCube = new Color(0.26, 0.429, 0.311);

  ColorMatchResult comparaisonCouleur;
  
  /** Creates a new Pince. */
  public Pince() {
    colorMatcher.addColorMatch(kCouleurCone);
    colorMatcher.addColorMatch(kCouleurCube);
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
public void ouvrirPince(){
  pince.set(Value.kForward);
}

public void fermerPince(){
  pince.set(Value.kReverse);
}

public boolean isBroken(){
  return lightBreak.get();
}

public Color getCouleur(){
  return capteurCouleur.getColor();

}

  public Color comparerCouleur()  {
  comparaisonCouleur = colorMatcher.matchClosestColor(getCouleur());
  return comparaisonCouleur.color;
  }

  public boolean isDetected(){
  return capteurCouleur.getProximity() > 150;
    
  }

  public boolean isCone() {
  
    return comparerCouleur() == kCouleurCone && isDetected();

  }

  public boolean isCube(){
    return comparerCouleur() == kCouleurCube && isDetected();

  }



}
