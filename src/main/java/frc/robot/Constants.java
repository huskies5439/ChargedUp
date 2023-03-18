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

    public static final double maxVitesse = 1.0;//ir dev = 3
    public static final double maxAcceleration = 1.0;//ir dev = 3

    public static final double kS = 0.11989;
    public static final double kV = 2.3714;
    public static final double kA = 0.78828;
    public static final double kPRamsete = 3.2252;

    public static final SimpleMotorFeedforward feedforward = 
            new SimpleMotorFeedforward(BasePilotableConstantes.kS, BasePilotableConstantes.kV, BasePilotableConstantes.kA);

    public static final double kPBalancer = -0.2; // à valider4
    public static final int kToleranceBalancer = 5;
    

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
    public static final double[] kBas = {-10, 0};
    public static final double[] kMilieu = {105, 0}; //95 normalement
    public static final double[] kHaut = {105, 0.45};

  }
}