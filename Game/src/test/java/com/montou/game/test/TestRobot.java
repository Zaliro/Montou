package com.montou.game.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.montou.game.entities.Robot;

public class TestRobot {

	private Robot robot1;
	private Robot robot2;
	private Robot robot3;

	@Before
	public void setup() {
		robot1 = new Robot("robot1", 1, 1);
		robot2 = new Robot("robot2", 2, 2);
		robot3 = new Robot("robot3", 3, 3);
	}

	@Test
	public void substractLifePointsTest() {
		// test avec valeur negative, positive, et si le robot passe en dessous de 0

		robot1.substractLifePoints(-20);
		robot2.substractLifePoints(20);
		robot2.substractLifePoints(-10);
		robot3.substractLifePoints(120);
		assertArrayEquals(new int[] { robot1.getLifePoints(), robot2.getLifePoints(), robot3.getLifePoints() },
				new int[] { 100, 80, 0 });
	}

	@Test
	public void substractEnergyPointsTest() {
		robot1.substractEnergyPoints(-20);
		robot2.substractEnergyPoints(20);
		robot2.substractEnergyPoints(-10);
		robot3.substractEnergyPoints(120);
		assertArrayEquals(new int[] { robot1.getEnergyPoints(), robot2.getEnergyPoints(), robot3.getEnergyPoints() },
				new int[] { 100, 80, 0 });

	}

	@Test
	public void regenTest() {
		robot1.substractEnergyPoints(20);
		robot1.regen(10);
		robot2.substractEnergyPoints(20);
		robot2.regen(-20);
		robot3.regen(200);

		assertArrayEquals(new int[] { robot1.getEnergyPoints(), robot2.getEnergyPoints(), robot3.getEnergyPoints() },
				new int[] { 90, 80, 100 });
	}
}
