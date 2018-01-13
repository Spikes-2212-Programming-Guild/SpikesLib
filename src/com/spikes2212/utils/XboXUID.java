package com.spikes2212.utils;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;

public class XboXUID extends XboxController {

	/**
	 * Constructs a new {@link XboXUID} using the port of the USB on the driver
	 * station.
	 * 
	 * @param port
	 *            The port on the Driver Station that the joystick is plugged
	 *            into.
	 */
	public XboXUID(int port) {
		super(port);
	}

	/**
	 * Returns the green button on the joy stick.
	 * 
	 * @return the green button on the joy stick.
	 */
	public Button getGreenButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getAButton();
			}
		};
	}

	/**
	 * Returns the blue button on the joy stick.
	 * 
	 * @return the blue button on the joy stick.
	 */
	public Button getBlueButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getXButton();
			}
		};
	}

	/**
	 * Returns the red button on the joy stick.
	 * 
	 * @return the red button on the joy stick.
	 */
	public Button getRedButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getBButton();
			}
		};
	}

	/**
	 * Returns the yellow button on the joy stick.
	 * 
	 * @return the yellow button on the joy stick.
	 */
	public Button getYellowButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getYButton();
			}
		};
	}

	/**
	 * Returns the right trigger button on the joy stick.
	 * 
	 * @return the right trigger button on the joy stick.
	 */
	public Button getRtButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getTrigger(Hand.kRight);
			}
		};
	}

	/**
	 * Returns the right bumper button on the joy stick.
	 * 
	 * @return the right bumper button on the joy stick.
	 */
	public Button getRbButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getBumper(Hand.kRight);
			}
		};
	}

	/**
	 * Returns the left trigger button on the joy stick.
	 * 
	 * @return the left trigger button on the joy stick.
	 */
	public Button getLtButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getTrigger(Hand.kLeft);
			}
		};
	}

	/**
	 * Returns the left bumper button on the joy stick.
	 * 
	 * @return the left bumper button on the joy stick.
	 */
	public Button getLbButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getBumper(Hand.kLeft);
			}
		};
	}

	/**
	 * Returns the button on the right stick.
	 * 
	 * @return the left trigger button on the joy stick.
	 */
	public Button getRightStickButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getStickButton(Hand.kRight);
			}
		};
	}

	/**
	 * Returns the button on the left stick.
	 * 
	 * @return the left trigger button on the joy stick.
	 */
	public Button getLeftStickButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getStickButton(Hand.kLeft);
			}
		};
	}

	/**
	 * Get the X axis value of the right stick.
	 * 
	 * @return the X axis value of the right stick.
	 */
	public double getRightX() {
		return getX(Hand.kRight);
	}

	/**
	 * Get the Y axis value of the right stick.
	 * 
	 * @return the Y axis value of the right stick.
	 */
	public double getRightY() {
		return getY(Hand.kRight);
	}

	/**
	 * Get the X axis value of the left stick.
	 * 
	 * @return the X axis value of the left stick.
	 */
	public double getLeftX() {
		return getX(Hand.kLeft);
	}

	/**
	 * Get the Y axis value of the left stick.
	 * 
	 * @return the Y axis value of the left stick.
	 */
	public double getLeftY() {
		return getY(Hand.kLeft);
	}

	/**
	 * Returns the up arrow button.
	 * 
	 * @return the up arrow button.
	 */
	public Button getUpButton() {
		return new Button() {
			@Override
			public boolean get() {
				return getPOV() == 0;
			}
		};
	}

	/**
	 * Returns the down arrow button.
	 * 
	 * @return the down arrow button.
	 */
	public Button getDownButton() {
		return new Button() {
			@Override
			public boolean get() {
				return getPOV() == 180;
			}
		};
	}

	/**
	 * Returns the left arrow button.
	 * 
	 * @return the left arrow button.
	 */
	public Button getLeftButton() {
		return new Button() {
			@Override
			public boolean get() {
				return getPOV() == 270;
			}
		};
	}

	/**
	 * Returns the right arrow button.
	 * 
	 * @return the right arrow button.
	 */
	public Button getRightButton() {
		return new Button() {
			@Override
			public boolean get() {
				return getPOV() == 90;
			}
		};
	}
}