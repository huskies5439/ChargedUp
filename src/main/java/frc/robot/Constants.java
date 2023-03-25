// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public final class Constants {

  public static final class BasePilotableConstantes {

    public static final double rampTeleop = 0.2;

    
    public static final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(0.635);//a déterminer dans sis id

    public static final double maxVitesse =2.0;//ir dev = 3
    public static final double maxAcceleration = 1.5;//ir dev = 3

    public static final double kS = 0.17836;
    public static final double kV = 2.1008;
    public static final double kA = 1.0138;
    public static final double kPRamsete = 2.4443;

    public static final SimpleMotorFeedforward feedforward = 
            new SimpleMotorFeedforward(BasePilotableConstantes.kS, BasePilotableConstantes.kV, BasePilotableConstantes.kA);

    
    public static final double kSTourner = 0.58645;
    public static final double kVTourner = 0.004763;
    public static final double kATourner = 0.002023;
    public static final double kPTourner = 0.2;

    public static final double maxVitesseTourner = 180; //en °/s
    public static final double kMaxAccelerationTourner = 270; //en °/s²

    public static final SimpleMotorFeedforward feedforwardtourner =
            new SimpleMotorFeedforward(BasePilotableConstantes.kSTourner, BasePilotableConstantes.kVTourner, BasePilotableConstantes.kATourner);
  }
  
  public static final class EchelleConstantes {

    public static final double kMaxEchelle = 0.56; //en mètre

    //Pour le PID
    public static final double kP = 150;
    
    public static final double kMaxVelocity = 2; //en m/s
    public static final double kMaxAcceleration = 4; //en m/s²
    public static final double kPositionTolerance = 0.05;// en m
  }

  public static final class CoudeConstantes {
    
    public static final double kMax = 110;
    public static final double kMin = -10;
    public static final double kOffset = -8;
    
    //Pour le PID
    public static final double kP = 0.225;

   

    public static final double kMaxVelocity = 270; //en degré/s
    public static final double kMaxAcceleration = 180; //en degré/s²
    public static final double kTolerance = 10; // en degré
  }

  public static final class Cible {
    //angle [degrés] du coude en premier, puis hauteur [m] de l'échelle
    public static final double[] kRentrer = {-10, 0};
    public static final double[] kMilieu = {105, 0}; 
    public static final double[] kHaut = {105, 0.45};
    public static final double[] kSol = {35, 0};

  }
}