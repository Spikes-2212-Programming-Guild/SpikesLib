package motor;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

import java.util.List;

public class Gearbox implements SpeedController {

    private WPI_TalonSRX master;
    private List<SpeedController> victorSPs;

    public Gearbox(WPI_TalonSRX master) {
        this.master = master;
    }

    @Override
    public void set(double speed) {
        master.set(speed);
    }

    @Override
    public double get() {
        return master.get();
    }

    @Override
    public boolean getInverted() {
        return master.getInverted();
    }

    @Override
    public void setInverted(boolean isInverted) {
        master.setInverted(isInverted);
    }

    @Override
    public void disable() {
        master.disable();
    }

    @Override
    public void stopMotor() {
        master.stopMotor();
    }

    @Override
    public void pidWrite(double output) {
        master.pidWrite(output);
    }

    public void enslaveTalonSRX(WPI_TalonSRX... slaves) {
        for(WPI_TalonSRX slave : slaves) {
            slave.follow(master);
        }
    }

    public void enslaveVictorSPX(WPI_VictorSPX... slaves) {
        for(WPI_VictorSPX slave : slaves) {
            slave.follow(master);
        }
    }

    public void enslaveVictorSP(VictorSP... slaves) {
        for(VictorSP slave : slaves) {
            victorSPs.add(slave);
        }
    }

}