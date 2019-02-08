package edu.ucsd.team14.personalbest;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref;

    boolean run;
    TextView numSteps;
    TextView speedVal;
    TextView currDist;
    TextView textSteps;

    Button runWalkButton;
    Button showStatsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Shared Preference is a key-value dictionary where we store use height
        // The name of our dictionary is an application ID
        // Height is stored in cm because it is more precise
        sharedPref = this.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        // Uncomment this to debug height prompt
        // SharedPreferences.Editor editor = sharedPref.edit();
        // editor.clear().commit();

        if(sharedPref.getInt(getString(R.string.height_key), -1) == -1) {
            Intent intent = new Intent(this, EnterHeightActivity.class);
            startActivity(intent);
        }

        setupScene();

        run = false;


    }




    protected void changeMode() {

        animateTransition();
        // Do something else required to change the mode

        run = !run;
    }


    private void animateTransition() {

        ConstraintLayout rootLayout = (ConstraintLayout) findViewById(R.id.main);
        ConstraintSet constraintMain = new ConstraintSet();
        constraintMain.clone(rootLayout);
        ConstraintSet constraintReset = new ConstraintSet();
        constraintReset.load(this, R.layout.activity_main);
        ConstraintSet constraintRun = new ConstraintSet();
        constraintRun.load(this, R.layout.activity_run);
        ChangeBounds transition = new ChangeBounds();
        transition.setDuration(1000L);
        TransitionManager.beginDelayedTransition(rootLayout, transition);

        if(run) {

            constraintReset.applyTo(rootLayout);
        } else {

            constraintRun.applyTo(rootLayout);
        }


    }


    public void setupScene() {
        System.out.println("Setting up scene");
        numSteps = (TextView) this.findViewById(R.id.tx_num_steps);
        speedVal = (TextView) this.findViewById(R.id.tx_val_speed);
        currDist = (TextView) this.findViewById(R.id.tx_val_dist);
        textSteps = (TextView) this.findViewById(R.id.tx_steps);

        Button runWalk = (Button) findViewById(R.id.bt_runwalk);
        runWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMode();

            }
        });
    }





    @Override
    protected void onStart() {
        super.onStart();


    }

    @Entity(tableName = "steps_table")
    public class Word {

        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "word")
        private String mWord;

        public Word(String word) {this.mWord = word;}

        public String getWord(){return this.mWord;}
    }



}


