// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BrasRetractable extends SubsystemBase {
  private WPI_TalonFX moteurBrasRetractable = new WPI_TalonFX(5);
  private double conversionEncodeurBras;
  private DigitalInput detecteurMagnetic = new DigitalInput(9);
  //Je sais pas trop comment faire pour la suite [P-A]
  ElevatorFeedforward feedforward = new ElevatorFeedforward(Constants.kSElevator, Constants.kGElevator, Constants.kVElevator, Constants.kAElevator);

  /** Creates a new BrasRetractable. */
  
  public BrasRetractable() {
    moteurBrasRetractable.setInverted(false);
    moteurBrasRetractable.setNeutralMode(NeutralMode.Brake);
    moteurBrasRetractable.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    resetEncodeur();
    /*1 tour de falcon = 2048 clic. Pignon 14 dents sur le falcon fait tourner gear 40 dents. La gear 40 dents est solidaire d'une
    gear 14 dents (même vitesse). La gear 14 dents fait tourner une gear 60 dents. La gear 60 dents est solidaire d'une roue dentée
    de 16 dents qui fait tourner la chaine 25. Chaque maille de la chaine fait 0.25 pouces*/
    conversionEncodeurBras = (1.0/2048)*(14.0/40)*(14.0/60)*(16.0)*Units.inchesToMeters(0.25);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  SmartDashboard.putNumber("Position BrasRetractable", getPosition());
  SmartDashboard.putNumber("vitesse bras rétractable", getVitesse());
  if (getDetecteurMagnetic()) {
   // resetEncodeur();
  }
  }

  public void setVoltage(double voltage) {
  moteurBrasRetractable.setVoltage(voltage);
  }

  public double getPosition() {
  return moteurBrasRetractable.getSelectedSensorPosition()*conversionEncodeurBras;
  }
  
  public void resetEncodeur() {
    moteurBrasRetractable.setSelectedSensorPosition(0);
  }
  public void stop() {
    setVoltage(0);
  }
  public double getVitesse() {
    return moteurBrasRetractable.getSelectedSensorVelocity()*conversionEncodeurBras*10; //x10 car les encodeur des falcon donne des click par 100 ms.
  }
  public boolean getDetecteurMagnetic() {
    return detecteurMagnetic.get();
  }
  public void allonger() {
    setVoltage(3);
  }
  public void retracter() {
    setVoltage(-3);
  }
}
