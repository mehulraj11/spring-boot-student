package com.example.student;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentApplicationTests {

	private Calculator calculator = new Calculator();
	@Test
	void contextLoads() {
	}


	@Test
	void doSumTest(){
		int expected = 6;
		int result = calculator.doSum(1,2,3);

		assertEquals(expected, result);
	}
}
