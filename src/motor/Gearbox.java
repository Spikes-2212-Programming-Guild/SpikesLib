package motor;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Gearbox implements SpeedController {

    private WPI_TalonSRX master;
    private final SpeedController[] VICTOR_SP;

    public Gearbox(WPI_TalonSRX master, SpeedController... VICTOR_SP){
        this.VICTOR_SP = VICTOR_SP;
        this.master = master;
    }

    public Gearbox(WPI_TalonSRX master){
        this.master = master;
        VICTOR_SP = null;
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
    public void setInverted(boolean isInverted) {
    master.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return master.getInverted();
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

    public void enslaveTalonSRX(WPI_TalonSRX slave){

    }
    public void enslaveVictorSPX(WPI_VictorSPX slave){

    }
}
