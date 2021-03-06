package com.example.oc_projet4_maru;

import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.oc_projet4_maru.Service.ApiService;
import com.example.oc_projet4_maru.UI.Controller.ListMeetingActivity;
import com.example.oc_projet4_marumaru.R;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {


    private final static int ITEMS_COUNT = 0;


    @Rule
    public final ActivityTestRule<ListMeetingActivity> mActivityTestRule = new ActivityTestRule<>(ListMeetingActivity.class);

    @Before
    public void setUp() {
        ListMeetingActivity listMeetingActivity = mActivityTestRule.getActivity();
        assertThat(listMeetingActivity, notNullValue());
        ApiService service = listMeetingActivity.getApiService();
    }

    /**
     * Génere un seul meeting
     */

    public void meeting(String sujet, int year, int month, int day, List<String> emails, int room){


        // clique sur bouton deuxiéme activiter
        onView(withId(R.id.fab)).perform(click());
        // Vérifie su bien sur l'autre activiter
        onView(withId(R.id.EditTextSujet)).check(matches(isDisplayed()));
        // clique sur l'éditText et écrit "Sujet 1"
        onView(withId(R.id.EditTextSujet)).perform(typeText(sujet));
        //rentre le clavier
        onView(withId(R.id.EditTextMail)).perform(closeSoftKeyboard());
        // clique sur "Ajouter une Heure" + "ok"
        onView(withId(R.id.button_hour)).perform(click());
        onView(withText("OK")).perform(click());
        // clique sur "Ajouter une date" + "ok"
        onView(withId(R.id.button_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year,month, day));
        onView(withText("OK")).perform(click());
        //Spinner
        onView(withId(R.id.EditTextSalle)).perform(click());
        onView(withText("Salle n°" + room)).perform(click());
        // clique sur l'éditText Mail (2 utilisateurs)
        for (String email : emails){
            onView(withId(R.id.EditTextMail)).perform(typeText(email));
            //rentre le clavier
            onView(withId(R.id.EditTextMail)).perform(closeSoftKeyboard());
            onView(withId(R.id.Button_add_chip)).perform(click());
        }
        // clique sur sauvegarder
        onView(withId(R.id.Saved)).perform(click());

    }

    /**
     *  Vérifie si la liste est vide
     *  clique bouton 2éme activité
     *  Ajoute Sujet, Heure, Date, Salle, Mails
     *  Clique sur le bouton Ajouter une réunion
     *  Vérifie si la RecycleurView compte bien 1 élément de plus
     *  Check si Sujet, Salle, Mails, corresponde
     */

    @Test
    public void addMeeting(){
        // vérifie si la liste est vide
        onView(withId(R.id.List_Meeting_RecycleurView))
                .check(matches(hasMinimumChildCount(0)));
        //Ajoute meeting
        meeting("sujet1",2020,3, 3, Arrays.asList("toto@gmail.com", "homer@gmail.com"), 5);
        // verifie si la liste utilisateur contient bien 1 élement de plus
        onView(ViewMatchers.withId(R.id.List_Meeting_RecycleurView)).check(matches(hasMinimumChildCount(1)));
        // Vérifier si le sujet est correct
        onView(ViewMatchers.withId(R.id.Nom_Fragment)).check(matches(withText("sujet1")));
        // Vérifier si les mails sont correct
        onView(ViewMatchers.withId(R.id.Mail_Fragment)).check(matches(withText("toto@gmail.com, homer@gmail.com")));
        // Vérifier si la salle est correct
        onView(ViewMatchers.withId(R.id.Salle_reunion)).check(matches(withText("Salle n°5")));


    }

    /**
     * Test suppression réunion
     */
    @Test
    public void deleteMeeting(){
        // vérifie si la liste est vide
        onView(withId(R.id.List_Meeting_RecycleurView))
                .check(matches(hasMinimumChildCount(0)));
        //Ajoute Meeting
        meeting("sujet1",2020,3, 3, Arrays.asList("toto@gmail.com", "homer@gmail.com"), 5);
        // supprime le 1er élément de la liste
        onView(withId(R.id.DeleteButton)).perform(click());
        // vérifier si la liste est vide de nouveau
        onView(withId(R.id.List_Meeting_RecycleurView))
                .check(matches(hasMinimumChildCount(0)));
    }


    /**
     * Vérifie le filtre Room
     */
    @Test
    public void checkIfFilteringMeetingsIsWorking() {

        // Ajouter 1er meeting
        meeting("sujet1",2020,3, 3, Arrays.asList("toto@gmail.com", "homer@gmail.com"), 5);

        // Ajouter  2éme meeting
        meeting("sujet2",2019,2, 5, Arrays.asList("toto@gmail.com", "homer@gmail.com"), 3);

        //vérifie sur la liste contient bien 2 meetings
        onView(withId(R.id.List_Meeting_RecycleurView))
                .check(matches(hasChildCount(ITEMS_COUNT +2)));


        //Clique sur le filtre
        onView(withId(R.id.dialog)).perform(click());
        //vérifie si la boite de dialog est bien ouverte
        onView(withId(R.id.HoraireText)).check(matches(isDisplayed()));
        // clique sur le spinner Room
        onView(withId(R.id.room_spinner_dialog)).perform(click());
        onData(CoreMatchers.allOf(is(instanceOf(String.class)),
                is("Salle n°5"))).inRoot(isPlatformPopup()).perform(click());
        //clique sur valider
        onView(withId(android.R.id.button1)).perform(click());
        //On vérifie qu'il reste un item dans la liste
        onView(withId(R.id.List_Meeting_RecycleurView))
                .check(matches(hasChildCount(ITEMS_COUNT +1)));
        //vérifie si la room match
        onView(ViewMatchers.withId(R.id.Salle_reunion)).check(matches(withText("Salle n°5")));

        //Clique sur le filtre
        onView(withId(R.id.dialog)).perform(click());
        //Clique sur le spinner date
        onView(withId(R.id.date_spinner_dialog)).perform(click());
        onData(CoreMatchers.allOf(is(instanceOf(String.class)),
                is("3/3/2020"))).inRoot(isPlatformPopup()).perform(click());
        //clique sur valider
        onView(withId(android.R.id.button1)).perform(click());
        //On vérifie qu'il reste un item dans la liste
        onView(withId(R.id.List_Meeting_RecycleurView))
                .check(matches(hasChildCount(ITEMS_COUNT +1)));
        //vérifie si la room match
        onView(ViewMatchers.withId(R.id.date_Fragment)).check(matches(withText("3/3/2020")));



    }
}
