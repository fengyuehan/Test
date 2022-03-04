package com.example.transition;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.Scene;
import androidx.transition.TransitionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransitionThreeActivity extends AppCompatActivity {
    @BindView(R.id.view_one1)
    ConstraintLayout viewOne1;

    private Scene scene,scene1;
    private SceneTransition sceneTransition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_three);
        ButterKnife.bind(this);
        scene = Scene.getSceneForLayout(viewOne1,R.layout.layout_scene1,this);
        scene1 = Scene.getSceneForLayout(viewOne1,R.layout.layout_scene2,this);
        sceneTransition = new SceneTransition();
    }

    @OnClick({R.id.bt_three_transition1, R.id.bt_three_transition2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_three_transition1:
                TransitionManager.go(scene,sceneTransition);
                break;
            case R.id.bt_three_transition2:
                TransitionManager.go(scene1,sceneTransition);
                break;
        }
    }
}
