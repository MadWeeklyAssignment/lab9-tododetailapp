package com.example.todo_detail_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TodoDetail extends AppCompatActivity {

    /* name, value pair to be returned in an intent */
    private static final String IS_TODO_COMPLETE = "com.example.isTodoComplete";

    private static final String TODO_INDEX = "com.example.todoIndex";
    private int mTodoIndex;
    private String[] todoDetails;
    /* Create an anonymous implementation of OnClickListener for all clickable view objects */
    private View.OnClickListener mTodoListener = new View.OnClickListener() {
        public void onClick(View v) {
            // get the clicked object and do something
            switch (v.getId() /*to get clicked view object id**/) {
                case R.id.checkBoxIsComplete:
                    CheckBox checkboxIsComplete = (CheckBox) findViewById(R.id.checkBoxIsComplete);
                    setIsComplete(checkboxIsComplete.isChecked());
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    public static Intent newIntent(Context packageContext, int todoIndex) {
        Intent intent = new Intent(packageContext, TodoDetail.class);
        intent.putExtra(TODO_INDEX, todoIndex);
        return intent;
    }

    /* override to write the value of mTodoIndex into the Bundle with TODO_INDEX as its key */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(TODO_INDEX, mTodoIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        if (savedInstanceState != null) {
            mTodoIndex = savedInstanceState.getInt(TODO_INDEX, 0);
        }

        /* TODO: refactor to a data layer */
        Resources res = getResources();
        todoDetails = res.getStringArray(R.array.todo_detail);

        /* get the intent extra int for the todos index */
        int mTodoIndex = getIntent().getIntExtra(TODO_INDEX, 0);
        updateTextViewTodoDetail(mTodoIndex);

        CheckBox checkboxIsComplete = (CheckBox) findViewById(R.id.checkBoxIsComplete);
        /* Register the onClick listener with the generic implementation mTodoListener */
        checkboxIsComplete.setOnClickListener(mTodoListener);

    }

    private void updateTextViewTodoDetail(int todoIndex) {

        final TextView textViewTodoDetail;
        textViewTodoDetail = (TextView) findViewById(R.id.textViewTodoDetail);

        /* display the first task from mTodo array in the TodoTextView */
        textViewTodoDetail.setText(todoDetails[todoIndex]);

    }

    private void setIsComplete(boolean isChecked) {

        /* celebrate with a static Toast! */
        if (isChecked) {
            Toast.makeText(TodoDetail.this,
                    "Hurray, it's done!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(TodoDetail.this,
                    "There is always tomorrow!", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent();
        intent.putExtra(IS_TODO_COMPLETE, isChecked);
        setResult(RESULT_OK, intent);
    }

}