package algonquin.cst2335.tang0243;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * This class contains a set of Espresso tests for the MainActivity of an Android application.
 * It tests the password validation functionality by entering different types of passwords and
 * checking the displayed message.
 * @author Ziyao Tang
 * @version 1.0
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    /**
     * Rule to launch the MainActivity using ActivityScenarioRule.
     */
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * This test case checks the scenario where a simple password "12345" is entered.
     * It verifies that clicking the button results in the display of the "You shall not pass!"
     * message in the TextView.
     */
    @Test
    public void mainActivityTest() {

        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView( withId(R.id.button) );
        materialButton.perform(click());

        ViewInteraction textView = onView( withId(R.id.textView) );
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * This test case checks a scenario where the password lacks an uppercase character.
     * It enters "password123#$*" and expects the "You shall not pass!" message to be displayed.
     */
    @Test
    public void testFindMissingUpperCase() {
        //find the edit text view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in password123#$*
        appCompatEditText.perform(replaceText("password123#$*"));
        //find the button
        ViewInteraction materialButton = onView( withId(R.id.button) );
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * This test case checks a scenario where the password lacks a lowercase character.
     * It enters "PASSWORD123#$*" and expects the "You shall not pass!" message to be displayed.
     */
    @Test
    public void testFindMissingLowerCase() {
        //find the edit text view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in PASSWORD123#$*
        appCompatEditText.perform(replaceText("PASSWORD123#$*"));
        //find the button
        ViewInteraction materialButton = onView( withId(R.id.button) );
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * This test case checks a scenario where the password lacks a special character.
     * It enters "Password123" and expects the "You shall not pass!" message to be displayed.
     */
    @Test
    public void testFindMissingSpecialCase() {
        //find the edit text view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in Password123
        appCompatEditText.perform(replaceText("Password123"));
        //find the button
        ViewInteraction materialButton = onView( withId(R.id.button) );
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * This test case checks a scenario where the password lacks a number.
     * It enters "Password#$*" and expects the "You shall not pass!" message to be displayed.
     */
    @Test
    public void testFindMissingNumberCase() {
        //find the edit text view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in Password#$*
        appCompatEditText.perform(replaceText("Password#$*"));
        //find the button
        ViewInteraction materialButton = onView( withId(R.id.button) );
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * This test case checks a scenario where the password meets all the requirements.
     * It enters "Password123#$*" and expects the message "Your password meets the requirements"
     * to be displayed.
     */
    @Test
    public void testFindNoMissing() {
        //find the edit text view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in Password123#$*
        appCompatEditText.perform(replaceText("Password123#$*"));
        //find the button
        ViewInteraction materialButton = onView( withId(R.id.button) );
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("Your password meets the requirements")));
    }
}
