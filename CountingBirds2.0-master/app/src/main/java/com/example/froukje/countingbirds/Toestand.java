package com.example.froukje.countingbirds;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

public class Toestand extends NavigationDrawer {
    DataReader dr = new DataReader();
    String date = "";
    String soort = "";
    Endangered toestand = Endangered.Onbekend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toestand);
        buildMenu();

        getData(dr.getStrIp());

        Button btBereken = (Button) findViewById(R.id.btBereken);
        btBereken.setOnClickListener(new View.OnClickListener(){

        public void onClick(View view) {
            AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.etSoort);
            soort = actv.getText().toString();
            EditText et = (EditText) findViewById(R.id.etJaar);
            date = et.getText().toString();
            getDataSearch(dr.getStrIp());
            }
        });
    }

    private void getData(String strIp) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = strIp +"/weidevogelsBE/webresources/appweidevogels.nestelegsel";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        uitLezen(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    private void uitLezen(String response) {

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response)));
            NodeList legsels = doc.getElementsByTagName("nestelegsels");
            NodeList alLegsels = (legsels.item(0)).getChildNodes();
            ArrayList<String> theLegsel = new ArrayList<>();

            for (int i = 0; i < alLegsels.getLength(); i++)
            {
                Element n = (Element) alLegsels.item(i);
                NodeList tag = n.getElementsByTagName("soort");
                Node soortNode = tag.item(0);

                if (!theLegsel.contains(soortNode.getTextContent()))
                    theLegsel.add(soortNode.getTextContent());
            }

            AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.etSoort);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, theLegsel);
            actv.setAdapter(adapter);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void getDataSearch(String strIp) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = strIp +"/weidevogelsBE/webresources/appweidevogels.nestelegsel";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        uitLezenSearch(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    private void uitLezenSearch(String response) {

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response)));
            NodeList legsels = doc.getElementsByTagName("nestelegsels");
            NodeList alLegsels = (legsels.item(0)).getChildNodes();
            ArrayList<String> theLegsel = new ArrayList<>();

            int totaalEieren = 0;

            for (int i = 0; i < alLegsels.getLength(); i++)
            {
                Element n = (Element) alLegsels.item(i);

                NodeList tag = n.getElementsByTagName("soort");
                Node soortNode = tag.item(0);
                NodeList tag1 = n.getElementsByTagName("datum");
                Node datumNode = tag1.item(0);

                if (soort.equals(soortNode.getTextContent()))
                {
                    if (date.equals(datumNode.getTextContent().substring(0, 4)))
                    {
                        Node ei = n.getChildNodes().item(1);
                        totaalEieren += Integer.parseInt(ei.getTextContent());
                    }
                }
            }

            if (totaalEieren >= 2500)
            {
                toestand = Endangered.Goed;
            }
            else if (totaalEieren >= 1000 && totaalEieren < 2500)
            {
                toestand = Endangered.Normaal;
            }
            else if (totaalEieren >= 500 && totaalEieren < 1000)
            {
                toestand = Endangered.Risico;
            }
            else if (totaalEieren < 500)
            {
                toestand = Endangered.Kritiek;
            }
            else if (totaalEieren == 0)
            {
                toestand = Endangered.Onbekend;
            }

            TextView tv = (TextView) findViewById(R.id.tvEieren);
            tv.setText("" + totaalEieren);
            tv = (TextView) findViewById(R.id.tvToestand);
            tv.setText(toestand.name());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private enum Endangered
    {
        Goed,
        Normaal,    //<2500
        Risico,     //<1000
        Kritiek,    //<500
        Onbekend
    }
}
