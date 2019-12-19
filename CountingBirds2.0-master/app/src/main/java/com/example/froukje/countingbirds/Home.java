package com.example.froukje.countingbirds;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class Home extends NavigationDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        buildMenu();
        TextView textView =(TextView)findViewById(R.id.tvLink);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href=http://www.weidevogelvereniging.nl/?menu=ijsvogel>Ijsvogel</a>";
        textView.setText(Html.fromHtml(text,Html.FROM_HTML_MODE_LEGACY));
        textView.setTextColor(getColor(R.color.blue));
    }
}