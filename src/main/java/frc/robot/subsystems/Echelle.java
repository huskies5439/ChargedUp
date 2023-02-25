// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.EchelleConstants;
import pabeles.concurrency.ConcurrencyOps.Reset;

public class Echelle extends SubsystemBase {
  private WPI_TalonFX moteurBras = new WPI_TalonFX(5);
  private double conversionEncodeur;
  private DigitalInput detecteurMagnetique = new DigitalInput(9);
  private ProfiledPIDController pid;



  public Echelle() {

    moteurBras.setInverted(false);
    moteurBras.setNeutralMode(NeutralMode.Brake);
    moteurBras.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    resetEncodeur();

    /*1 tour de falcon = 2048 clic. Pignon 14 dents sur le falcon fait tourner gear 40 dents. La gear 40 dents est solidaire d'une
    gear 14 dents (même vitesse). La gear 14 dents fait tourner une gear 60 dents. La gear 60 dents est solidaire d'une roue dentée
    de 16 dents qui fait tourner la chaine 25. Chaque maille de la chaine fait 0.25 pouces*/
    conversionEncodeur = (1.0/2048)*(14.0/40)*(14.0/60)*(16.0)*Units.inchesToMeters(0.25);

    pid = new ProfiledPIDController(100, 0, 0,
            //Vitesse et accélération max vraiment faibles pour tester     
            new TrapezoidProfile.Constraints(2,4));
            pid.setTolerance(5);

    setCible(0);

  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  SmartDashboard.putNumber("Position Mat", getPosition());
  SmartDashboard.putNumber("Vitesse Mat", getVitesse());

  

 // pidEchelle();

  }

  public void setVoltage(double voltage) {
  moteurBras.setVoltage(voltage);
  }

  public void stop() {
    setVoltage(0);
  }

  public void allonger() {
    setVoltage(3);
    /*
    if (getPosition()< EchelleConstants.kMaxEchelle){
      setVoltage(3);
    }
    else {
      stop();
    } */
    
  
  }
  public void retracter() {
    setVoltage(-3);
    /*
    if (getPosition() > 0){
      setVoltage(-3);
    }
    else{
      stop();
    }
     */
    
    
  }

  //////Encodeur Mât
  public double getPosition() {
  return moteurBras.getSelectedSensorPosition()*conversionEncodeur;
  }

  public double getVitesse() {
    return moteurBras.getSelectedSensorVelocity()*conversionEncodeur*10; //x10 car les encodeur des falcon donne des click par 100 ms.
  }
  
  public void resetEncodeur() {
    moteurBras.setSelectedSensorPosition(0);
  }

 
  public boolean getDetecteurMagnetique() {
    return detecteurMagnetique.get();
  }

public void pidEchelle() {
  setVoltage(pid.calculate(getPosition()));
}
public void setCible(double cible){
  pid.setGoal(cible);
}


}
