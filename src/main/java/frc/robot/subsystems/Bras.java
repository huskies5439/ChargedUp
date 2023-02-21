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
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BrasConstants;
import pabeles.concurrency.ConcurrencyOps.Reset;

public class Bras extends SubsystemBase {
  private WPI_TalonFX moteurBrasRetractable = new WPI_TalonFX(5);
  private double conversionEncodeurMat;
  private Encoder encodeurCoude = new Encoder(4, 5);
  private DigitalInput detecteurMagnetic = new DigitalInput(9);
  ElevatorFeedforward feedforward = new ElevatorFeedforward(BrasConstants.kSElevator, BrasConstants.kGElevator, BrasConstants.kVElevator, BrasConstants.kAElevator);

  //Encoder encodeurCoude = ....... // utiliser les ports 4,5. C'est un encodeur externe, donc voir les encodeurs de la basePilotable
  double conversionEncodeurCoude;
  public Bras() {
    moteurBrasRetractable.setInverted(false);
    moteurBrasRetractable.setNeutralMode(NeutralMode.Brake);
    moteurBrasRetractable.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    resetEncodeurMat();
    /*1 tour de falcon = 2048 clic. Pignon 14 dents sur le falcon fait tourner gear 40 dents. La gear 40 dents est solidaire d'une
    gear 14 dents (même vitesse). La gear 14 dents fait tourner une gear 60 dents. La gear 60 dents est solidaire d'une roue dentée
    de 16 dents qui fait tourner la chaine 25. Chaque maille de la chaine fait 0.25 pouces*/
    conversionEncodeurMat = (1.0/2048)*(14.0/40)*(14.0/60)*(16.0)*Units.inchesToMeters(0.25);

    conversionEncodeurCoude = 1; //Trouver la vraie valeur lorsque le CAD sera fait.

  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  SmartDashboard.putNumber("Position Mat", getPositionMat());
  SmartDashboard.putNumber("Vitesse Mat", getVitesseMat());
  SmartDashboard.putNumber("Vitesse Coude", getVitesseCoude());
  SmartDashboard.putNumber("Distance Coude", getDistanceCoude());
  }

  public void setVoltage(double voltage) {
  moteurBrasRetractable.setVoltage(voltage);
  }

  public void stop() {
    setVoltage(0);
  }

  public void allonger() {
    if (getPositionMat()< BrasConstants.kMaxMat){
        setVoltage(3);
    }
    else {
      stop();
    }
  
  }
  public void retracter() {
    if (getPositionMat() > 0){
      setVoltage(-3);
    }
    else{
      stop();
    }
    
  }

  //////Encodeur Mât
  public double getPositionMat() {
  return moteurBrasRetractable.getSelectedSensorPosition()*conversionEncodeurMat;
  }

  public double getVitesseMat() {
    return moteurBrasRetractable.getSelectedSensorVelocity()*conversionEncodeurMat*10; //x10 car les encodeur des falcon donne des click par 100 ms.
  }
  
  public void resetEncodeurMat() {
    moteurBrasRetractable.setSelectedSensorPosition(0);
  }

 
  public boolean getDetecteurMagnetic() {
    return detecteurMagnetic.get();
  }


  /////Encodeur Coude - mettre les méthodes ici.

  public double getDistanceCoude() {
    return encodeurCoude.getDistance();
  }

  public double getVitesseCoude() {
    return encodeurCoude.getRate();
  }

  public void resetEncodeurCoude() {
    encodeurCoude.reset();
  }


}
