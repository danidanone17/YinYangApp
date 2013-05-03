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

	//private Context context = this.getInstrumentation().getTargetContext()
		//	.getApplicationContext();

	public void testNotExistingIds() {
		UserProfileController controller = null;
		Context context = this.getInstrumentation().getTargetContext()
				.getApplicationContext();
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
	 * Get information about one specific user, userId: 42
	 * Testcases contains hard coded data manually selected from the database
	 */
	public void testExistingId() {
		Context context = this.getInstrumentation().getTargetContext()
				.getApplicationContext();
		UserProfileController controller = null;
		try {
			controller = new UserProfileController(context, 42);
			assertEquals("getReputationText", 9060, controller.getReputationText());
			assertEquals("getUserName", "Coincoin", controller.getUserName());
			assertEquals("getWebsite", "" , controller.getWebsite());
			assertEquals("getLocation", "Montreal, Canada", controller.getLocation());
			assertEquals("getAge", "32", controller.getAge());
			assertEquals("gtProfileViews", 358, controller.getProfileViews());
		} catch (NullPointerException e) {
			fail("Should not have thrown a NullPointerException");
		}
		
	}
}
