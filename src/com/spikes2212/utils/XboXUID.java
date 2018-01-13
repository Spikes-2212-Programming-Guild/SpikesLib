package com.spikes2212.utils;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;

public class XboXUID extends XboxController {

	public XboXUID(int port) {
		super(port);
	}

	public Button getGreenButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getAButton();
			}
		};
	}

	public Button getBlueButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getXButton();
			}
		};
	}

	public Button getRedButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getBButton();
			}
		};
	}

	public Button getYellowButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getYButton();
			}
		};
	}

	public Button getRtButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getTrigger(Hand.kRight);
			}
		};
	}

	public Button getRbButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getBumper(Hand.kRight);
			}
		};
	}

	public Button getLtButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getTrigger(Hand.kLeft);
			}
		};
	}

	public Button getLbButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getBumper(Hand.kLeft);
			}
		};
	}

	public Button getRightStickButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getStickButton(Hand.kRight);
			}
		};
	}

	public Button getLeftStickButton() {
		return new Button() {

			@Override
			public boolean get() {
				return getStickButton(Hand.kLeft);
			}
		};
	}

	public double getRightX() {
		return getX(Hand.kRight);
	}

	public double getRightY() {
		return getY(Hand.kRight);
	}

	public double getLeftX() {
		return getX(Hand.kLeft);
	}

	public double getLeftY() {
		return getY(Hand.kLeft);
	}

	public Button getUpButton() {
		return new Button() {
			@Override
			public boolean get() {
				return getPOV() == 0;
			}
		};
	}

	public Button getDownButton() {
		return new Button() {
			@Override
			public boolean get() {
				return getPOV() == 180;
			}
		};
	}

	public Button getLeftButton() {
		return new Button() {
			@Override
			public boolean get() {
				return getPOV() == 270;
			}
		};
	}

	public Button getRightButton() {
		return new Button() {
			@Override
			public boolean get() {
				return getPOV() == 90;
			}
		};
	}
}