package com.example.quiz;


import android.support.test.espresso.DataInteraction;
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

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InstructorQuizTest2 {

    @Rule
    public ActivityTestRule<InstructorQuiz> mActivityTestRule = new ActivityTestRule<>(InstructorQuiz.class);

    @Test
    public void instructorQuizTest2() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.point),
                        childAtPosition(
                                allOf(withId(R.id.mark),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("3"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                3),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("a shot"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.description), withText("a shot"),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                3),
                        isDisplayed()));
        appCompatEditText3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.description), withText("a shot"),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                3),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("a short question"));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.description), withText("a short question"),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                3),
                        isDisplayed()));
        appCompatEditText5.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.addNext), withText("Add"),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                4),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.point),
                        childAtPosition(
                                allOf(withId(R.id.mark),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.type),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                2),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                3),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("1+1="), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.a_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                7),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("0"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.b_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                9),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.c_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                11),
                        isDisplayed()));
        appCompatEditText10.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.d_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                13),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("3"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.addNext), withText("Add"),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                4),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.point),
                        childAtPosition(
                                allOf(withId(R.id.mark),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText12.perform(replaceText("3"), closeSoftKeyboard());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                3),
                        isDisplayed()));
        appCompatEditText13.perform(replaceText("a second question"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.addNext), withText("Add"),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                4),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.point),
                        childAtPosition(
                                allOf(withId(R.id.mark),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText14.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.type),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                2),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatTextView2.perform(click());

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                3),
                        isDisplayed()));
        appCompatEditText15.perform(replaceText("2*2="), closeSoftKeyboard());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.a_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                7),
                        isDisplayed()));
        appCompatEditText16.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.b_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                9),
                        isDisplayed()));
        appCompatEditText17.perform(replaceText("4"), closeSoftKeyboard());

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.c_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                11),
                        isDisplayed()));
        appCompatEditText18.perform(replaceText("6"), closeSoftKeyboard());

        ViewInteraction appCompatEditText19 = onView(
                allOf(withId(R.id.d_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                13),
                        isDisplayed()));
        appCompatEditText19.perform(replaceText("8"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.point),
                        childAtPosition(
                                allOf(withId(R.id.mark),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText20.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText21 = onView(
                allOf(withId(R.id.description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                3),
                        isDisplayed()));
        appCompatEditText21.perform(replaceText("a short question"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatEditText22 = onView(
                allOf(withId(R.id.point),
                        childAtPosition(
                                allOf(withId(R.id.mark),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText22.perform(replaceText("3"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatEditText23 = onView(
                allOf(withId(R.id.point), withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.mark),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText23.perform(replaceText("3"));

        ViewInteraction appCompatEditText24 = onView(
                allOf(withId(R.id.point), withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.mark),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText24.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText25 = onView(
                allOf(withId(R.id.description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                3),
                        isDisplayed()));
        appCompatEditText25.perform(replaceText("1+1="), closeSoftKeyboard());

        ViewInteraction appCompatEditText26 = onView(
                allOf(withId(R.id.a_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                7),
                        isDisplayed()));
        appCompatEditText26.perform(replaceText("0"), closeSoftKeyboard());

        ViewInteraction appCompatEditText27 = onView(
                allOf(withId(R.id.b_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                9),
                        isDisplayed()));
        appCompatEditText27.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText28 = onView(
                allOf(withId(R.id.c_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                11),
                        isDisplayed()));
        appCompatEditText28.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText29 = onView(
                allOf(withId(R.id.d_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                13),
                        isDisplayed()));
        appCompatEditText29.perform(replaceText("3"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatEditText30 = onView(
                allOf(withId(R.id.point),
                        childAtPosition(
                                allOf(withId(R.id.mark),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText30.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText31 = onView(
                allOf(withId(R.id.description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                3),
                        isDisplayed()));
        appCompatEditText31.perform(replaceText("a short question"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatEditText32 = onView(
                allOf(withId(R.id.point),
                        childAtPosition(
                                allOf(withId(R.id.mark),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText32.perform(replaceText("3"), closeSoftKeyboard());

        ViewInteraction appCompatEditText33 = onView(
                allOf(withId(R.id.description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                3),
                        isDisplayed()));
        appCompatEditText33.perform(replaceText("2*2="), closeSoftKeyboard());

        ViewInteraction appCompatEditText34 = onView(
                allOf(withId(R.id.a_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                7),
                        isDisplayed()));
        appCompatEditText34.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText35 = onView(
                allOf(withId(R.id.b_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                9),
                        isDisplayed()));
        appCompatEditText35.perform(replaceText("4"), closeSoftKeyboard());

        ViewInteraction appCompatEditText36 = onView(
                allOf(withId(R.id.c_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                11),
                        isDisplayed()));
        appCompatEditText36.perform(replaceText("6"), closeSoftKeyboard());

        ViewInteraction appCompatEditText37 = onView(
                allOf(withId(R.id.d_description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                13),
                        isDisplayed()));
        appCompatEditText37.perform(replaceText("8"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatEditText38 = onView(
                allOf(withId(R.id.point),
                        childAtPosition(
                                allOf(withId(R.id.mark),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText38.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText39 = onView(
                allOf(withId(R.id.description),
                        childAtPosition(
                                withParent(withId(R.id.questionList)),
                                3),
                        isDisplayed()));
        appCompatEditText39.perform(replaceText("a short question"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.save), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());

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
