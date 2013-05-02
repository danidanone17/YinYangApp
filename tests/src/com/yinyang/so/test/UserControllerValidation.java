/**
 * 
 */
package com.yinyang.so.test;

import com.yinyang.so.controllers.UserProfileController;

/**
 * @author Fredrik
 * 
 */
public class UserControllerValidation extends
		android.test.InstrumentationTestCase {

	private UserProfileController controller;

	public void testNotExistingIds() {
		int[] ids = { -1, 0, 1, 3 };
		for (int id : ids) {
			try {
				controller = new UserProfileController(this
						.getInstrumentation().getTargetContext()
						.getApplicationContext(), id);
				fail("Should have thrown a NullPointerException");
			} catch (NullPointerException e) {
				// success
			}
			assertNull("There should be no user with given id: "+id, controller);
		}
	}

}
