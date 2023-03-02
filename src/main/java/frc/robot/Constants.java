// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {

  public static final class BasePilotableConstants {
    public static final double rampTeleop = 0.2;

    public static final int kToleranceBalancer = 5;
  }
  
  public static final class EchelleConstants {
    public static final double kMaxEchelle = 0.58; //en mètre

    //Pour le PID
    public static final double kP = 100;
    
    public static final double kMaxVelocity = 2; //en m/s
    public static final double kMaxAcceleration = 4; //en m/s²
    public static final double kPositionTolerance = 5;// en m
  }

  public static final class CoudeConstants {
    public static final double kMaxCoude = 135;
    public static final double kMinCoude = -10;

    public static final double kOffsetAngle = 0; //à savoir la distance
    
    //Pour le PID
    public static final double kP = 0.1;

    public static final double kMaxVelocity = 15; //en degré/s
    public static final double kMaxAcceleration = 15; //en degré/s²
    public static final double kPositionTolerance = 1; // en degré

    //Pour le feedforward
    public static final double kS = 0;
    public static final double kG = 0;
    public static final double kV = 0;
    public static final double kA = 0;
  }

  public static final class Cible {
    //angle [degrés] du coude en premier, puis hauteur [m] de l'échelle
    public static final double[] kBas = {0, 0};
    public static final double[] kMilieu = {20, 0.2};
    public static final double[] kHaut = {45, 0.4};
  }
}