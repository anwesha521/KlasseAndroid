package com.example.asus.klasseandroid;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StudentQuizTest2 {

    @Rule
    public ActivityTestRule<StudentQuiz> mActivityTestRule = new ActivityTestRule<>(StudentQuiz.class);

    @Test
    public void studentQuizTest2() {
        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.rb1), withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.choices),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.rb2), withText("4"),
                        childAtPosition(
                                allOf(withId(R.id.choices),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatRadioButton2.perform(click());

        ViewInteraction appCompatRadioButton3 = onView(
                allOf(withId(R.id.rb2), withText("4"),
                        childAtPosition(
                                allOf(withId(R.id.choices),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatRadioButton3.perform(click());

        ViewInteraction appCompatRadioButton4 = onView(
                allOf(withId(R.id.rb4), withText("9"),
                        childAtPosition(
                                allOf(withId(R.id.choices),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        appCompatRadioButton4.perform(click());

        ViewInteraction appCompatRadioButton5 = onView(
                allOf(withId(R.id.rb3), withText("7"),
                        childAtPosition(
                                allOf(withId(R.id.choices),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatRadioButton5.perform(click());

        ViewInteraction appCompatRadioButton6 = onView(
                allOf(withId(R.id.rb2), withText("4"),
                        childAtPosition(
                                allOf(withId(R.id.choices),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatRadioButton6.perform(click());

        ViewInteraction appCompatRadioButton7 = onView(
                allOf(withId(R.id.rb3), withText("6"),
                        childAtPosition(
                                allOf(withId(R.id.choices),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatRadioButton7.perform(click());

        ViewInteraction appCompatRadioButton8 = onView(
                allOf(withId(R.id.rb2), withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.choices),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatRadioButton8.perform(click());

        ViewInteraction appCompatRadioButton9 = onView(
                allOf(withId(R.id.rb3), withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.choices),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatRadioButton9.perform(click());

        ViewInteraction appCompatRadioButton10 = onView(
                allOf(withId(R.id.rb2), withText("4"),
                        childAtPosition(
                                allOf(withId(R.id.choices),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatRadioButton10.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.submit), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
