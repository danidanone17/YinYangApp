/**
 * 
 */
package com.yinyang.so.test;

import android.content.Context;

import com.yinyang.so.controllers.UserProfileController;

/**
 * @author Fredrik
 * 
 */
public class UserControllerValidation extends
		android.test.InstrumentationTestCase {

	private Context context = this.getInstrumentation().getTargetContext()
			.getApplicationContext();

	public void testNotExistingIds() {
		UserProfileController controller = null;

		int[] ids = { -1, 0, 1, 3 };
		for (int id : ids) {
			try {
				controller = new UserProfileController(context, id);
				fail("Should have thrown a NullPointerException");
			} catch (NullPointerException e) {
				// success
			}
			assertNull("There should be no user with given id: " + id,
					controller);
		}
	}

	/**
	 * Testcases hard coded data from database
	 */
	public void testExistingIds() {

		UserProfileController controller = null;
		try {
			controller = new UserProfileController(context, 42);
		} catch (NullPointerException e) {
			fail("Should not have thrown a NullPointerException");
		}
		
	}
}
