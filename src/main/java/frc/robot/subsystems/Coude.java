// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.fasterxml.jackson.databind.JsonSerializable.Base;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BrasConstants;

public class Coude extends SubsystemBase {
  double conversionEncodeur;
  private Encoder encodeur = new Encoder(4, 5);
  private WPI_TalonFX moteur = new WPI_TalonFX(6);
  
  public Coude() {
    conversionEncodeur = 1; //Trouver la vraie valeur lorsque le CAD sera fait.
    moteur.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Vitesse Coude", getVitesse());
    SmartDashboard.putNumber("Distance Coude", getPosition());
  
  }
  public double getPosition() {
    return encodeur.getDistance();
  }

  public double getVitesse() {
    return encodeur.getRate();
  }

  public void resetEncodeur() {
    encodeur.reset();
  }
  public void setVoltage(double voltage) {
    moteur.setVoltage(voltage);
  }
     public void descendre() {
    if (getPosition() > 0){
      setVoltage(-3);
    }
    else{
      stop();
    }
    
  }
    public void monter() {
    if (getPosition() < BrasConstants.kMaxCoude){
        setVoltage(3);
    }
    else {
      stop();
  
   }
  }

  public void stop(){
    setVoltage(0);

  }
}
