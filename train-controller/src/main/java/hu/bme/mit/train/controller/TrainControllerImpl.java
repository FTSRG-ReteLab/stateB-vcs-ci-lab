package hu.bme.mit.train.controller;

import com.google.common.collect.*;
import hu.bme.mit.train.interfaces.TrainController;

import java.util.*;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;

public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private Table<Long,Integer,Integer> table = HashBasedTable.create();

	@Override
	public void followSpeed() {

		Date date = new Date();

		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
			if(referenceSpeed + step <= 0) referenceSpeed = 0;
			else referenceSpeed += step;

		}

		table.put(date.getTime(),referenceSpeed,step);

		enforceSpeedLimit();

	}

	public int collectionSize(){

		return table.size();

	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	@Override
	public void emergencyBrake(){

		this.speedLimit = 0;
		enforceSpeedLimit();

	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;		
	}

}
