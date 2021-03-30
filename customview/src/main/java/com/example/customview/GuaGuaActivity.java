package com.example.customview;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.guaguaka.GuaGuaCard;

/**
 * author : zhangzf
 * date   : 2021/3/23
 * desc   :
 */
public class GuaGuaActivity extends AppCompatActivity {
    private TextView textview;
    private Button test_button;
    private Thread thread;
    private GuaGuaCard guaguacard;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_guagua);
        textview = findViewById(R.id.textview);
        test_button = findViewById(R.id.test_button);
        guaguacard = findViewById(R.id.guaguacard);

        textview.setText("世有伯乐，然后有千里马。千里马常有，而伯乐不常有。故虽有名马，祗辱于奴隶人之手，骈死于槽枥之间，不以千里称也。\n" +
                "\n" +
                "马之千里者，一食或尽粟一石。食马者不知其能千里而食也。是马也，虽有千里之能，食不饱，力不足，才美不外见，且欲与常马等不可得，安求其能千里也？\n" +
                "\n" +
                "策之不以其道，食之不能尽其材，鸣之而不能通其意，执策而临之，曰：“天下无马！”呜呼！其真无马邪？其真不知马也！");
    }
}
