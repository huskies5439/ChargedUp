// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pince extends SubsystemBase {
  private final CANSparkMax moteurLanceurDroit = new CANSparkMax(27,MotorType.kBrushless);
  private final CANSparkMax moteurLanceurGauche = new CANSparkMax(38,MotorType.kBrushless);
  private final MotorControllerGroup moteurLanceur  = new MotorControllerGroup(moteurLanceurDroit,moteurLanceurGauche);
  private final SimpleMotorFeedforward lanceurFF = new SimpleMotorFeedforward(0.135,0.00141); 
 private PIDController pid = new PIDController(0.002, 0, 0.0003);
 
  /** Creates a new Pince. */
  public Pince() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
