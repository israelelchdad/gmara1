package com.example.myapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.model.DafLearning1;
import com.example.model.Profile;
import com.example.model.StudyOption;
import com.example.model.shas_masechtot_list_models.AllShasItem;
import com.example.myapp.R;
import com.example.myapp.adapters.RecyclerViewStudyOptionsAdapter;
import com.example.myapp.dataBase.AppDataBase;
import com.example.myapp.databinding.ActivityProfileBinding;
import com.example.myapp.interfaces.CreateTypeOfStudy;
import com.example.myapp.utils.Language;
import com.example.myapp.utils.ManageSharedPreferences;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;


import static com.example.myapp.activity.SplashActivity.KEY_EXTRA_List1;

  public class ProfileActivity extends AppCompatActivity implements CreateTypeOfStudy {

    private ActivityProfileBinding binding;
    private ArrayList <DafLearning1> mListLearning = new ArrayList<>();
    private ArrayList <StudyOption> mStudyOptionsList = new ArrayList<>();
    private AllShasItem mAllShas;
    private Profile mProfile = new Profile(0);

    private String mStringTypeOfStudy;
    private RecyclerView myRecyclerViewStudyOptions;
    private RecyclerViewStudyOptionsAdapter mRecyclerViewStudyOptionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initListAllShah();
        initViews();
        initListener();
    }

      private void initListAllShah() {
          Gson gson = new Gson();
          try {
              String txt = convertStreamToString(Objects.requireNonNull(this).getAssets().open("list_all_shas_json.txt"));
              mAllShas = gson.fromJson(txt, AllShasItem.class);
          }catch (Exception e){ }
      }

    private void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        initStudyOptionsList();
        myRecyclerViewStudyOptions = binding.myRecyclerView;
        myRecyclerViewStudyOptions.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewStudyOptionsAdapter = new RecyclerViewStudyOptionsAdapter(this,mStudyOptionsList,mAllShas);
        myRecyclerViewStudyOptions.setAdapter(mRecyclerViewStudyOptionsAdapter);
    }

      private void initStudyOptionsList() {
          mStudyOptionsList.add(new StudyOption("דף היומי"));
          mStudyOptionsList.add(new StudyOption("תלמוד בבלי"));

      }

      private void initListener() {
        binding.ProfileCreateLearningBU.setOnClickListener(v -> createLearningClicked(v));
    }

    private void createLearningClicked(View v) {
        onRadioNumberOfReps();
        alertDialogAreYouSure();
    }

      public void onRadioNumberOfReps () {
          int selectedId = binding.ProfileNumberOfRepsRG.getCheckedRadioButtonId();
          switch (selectedId) {
              case R.id.radio_No_thanks:
                  mProfile.setNumberOfReps(0);
                  break;
              case R.id.radio_Once:
                  mProfile.setNumberOfReps(1);
                  break;
              case R.id.radio_Twice:
                  mProfile.setNumberOfReps(2);
                  break;
              case R.id.radio_3_times:
                  mProfile.setNumberOfReps(3);
                  break;
          }
      }

    private void alertDialogAreYouSure() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        ManageSharedPreferences.setProfile(mProfile,getBaseContext());
                        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                        intent.putExtra(KEY_EXTRA_List1, mListLearning);
                        startActivity(intent);
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        String typeOfStudy =  "מסוג: " + mStringTypeOfStudy;
        String numberOfReps =  "חזרות: " + mProfile.getNumberOfReps();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ברצונך ליצור לימוד יומי");
        builder.setMessage(typeOfStudy +"\n"+ numberOfReps);
        builder.setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

      private void CreateListAllShas() {
          int id = 1;
          for (int i = 0; i <mAllShas.getSeder().size() ; i++) {
              for (int j = 0; j <mAllShas.getSeder().get(i).getMasechtot().size() ; j++) {
                  for (int k = 2; k <(mAllShas.getSeder().get(i).getMasechtot().get(j).getPages()+2) ; k++) {
                      mListLearning.add(new DafLearning1(mAllShas.getSeder().get(i).getMasechtot().get(j).getName(),k,id));
                      id++;
                  }
              }
          }
      }

      private static String convertStreamToString(InputStream is) throws Exception {
          BufferedReader reader = new BufferedReader(new InputStreamReader(is));
          StringBuilder sb = new StringBuilder();
          String line = null;
          while ((line = reader.readLine()) != null) {
              sb.append(line).append("\n");
          }
          reader.close();
          return sb.toString();
      }

      @Override
      public void CreateListTypeOfStudy(String stringTypeOfStudy) {
          if (stringTypeOfStudy.equals("דף היומי")){
              mStringTypeOfStudy = stringTypeOfStudy;
              CreateListAllShas();
              AppDataBase.getInstance(this).daoLearning1().insertAllLearning(mListLearning);
          }
      }
  }

