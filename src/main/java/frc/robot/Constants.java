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

    public static final double kMaxEchelle = 0.56; //en mètre

    //Pour le PID
    public static final double kP = 100;
    
    public static final double kMaxVelocity = 2; //en m/s
    public static final double kMaxAcceleration = 4; //en m/s²
    public static final double kPositionTolerance = 5;// en m
  }

  public static final class CoudeConstants {
    
    public static final double kMax = 100;
    public static final double kMin = -15;
    public static final double kOffset = -8;
    
    //Pour le PID
    public static final double kP = 0.15;

    public static final double kMaxVelocity = 270; //en degré/s
    public static final double kMaxAcceleration = 180; //en degré/s²
    public static final double kPositionTolerance = 1; // en degré

    //Pour le feedforward
    public static final double kS = 0;
    public static final double kG = 0;
    public static final double kV = 0;
    public static final double kA = 0;
  }

  public static final class Cible {
    //angle [degrés] du coude en premier, puis hauteur [m] de l'échelle
    public static final double[] kBas = {-10, 0};
    public static final double[] kMilieu = {95, 0};
    public static final double[] kHaut = {95, 0.45};
  }
}