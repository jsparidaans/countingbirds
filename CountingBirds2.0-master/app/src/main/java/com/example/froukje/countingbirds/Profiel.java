package com.example.froukje.countingbirds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.froukje.countingbirds.Classes.Vrijwilliger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

public class Profiel extends AppCompatActivity {


    private EditText naamVrijw;
    private EditText postcodeVrijw;
    private EditText huisnummerVrijw;
    private EditText emailVrijw;
    private EditText telVrijw;

    private String naam = "";
    private DataReader dr = new DataReader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel);
        getDataAlleVrijwilligers(dr.getStrIp());
        Bundle b = getIntent().getExtras();
        naam = b.getString("Input");

        naamVrijw = (EditText) findViewById(R.id.naamVrijw);
        postcodeVrijw = (EditText) findViewById(R.id.postcodeVrijw);
        huisnummerVrijw = (EditText) findViewById(R.id.huisnummerVrijw);
        emailVrijw = (EditText) findViewById(R.id.emailVrijw);
        telVrijw = (EditText) findViewById(R.id.telVrijw);


    }

    private void uitLezenAlleVrijwilligers(String response) {

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response)));
            NodeList vrijwilligers = doc.getElementsByTagName("vrijwilligers");
            NodeList alleVrijwilligers = (vrijwilligers.item(0)).getChildNodes();

            for (int i = 0; i < alleVrijwilligers.getLength(); i++) {
                Element n = (Element) alleVrijwilligers.item(i);

                NodeList tag2 = n.getElementsByTagName("naam");
                Node naamNode = tag2.item(0);


                if (naam.equals(naamNode.getTextContent())) {


                    NodeList tag = n.getElementsByTagName("bsn");
                    Node bsn = tag.item(0);
                    NodeList tag1 = n.getElementsByTagName("huisnummer");
                    Node huisnummer = tag1.item(0);

                    NodeList tag3 = n.getElementsByTagName("postcode");
                    Node postcode = tag3.item(0);


                    try {
                        NodeList tag4 = n.getElementsByTagName("email");
                        Node email = tag4.item(0);
                        emailVrijw.setText("" + email.getTextContent());
                    } catch (NullPointerException nul) {

                    }

                    try {
                        NodeList tag5 = n.getElementsByTagName("telnummer");
                        Node telnummer = tag5.item(0);
                        telVrijw.setText("" + telnummer.getTextContent());
                    } catch (NullPointerException nul) {

                    }

                    naamVrijw.setText("" + naamNode.getTextContent());
                    postcodeVrijw.setText("" + postcode.getTextContent());
                    huisnummerVrijw.setText("" + huisnummer.getTextContent());


                }


            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void getDataAlleVrijwilligers(String strIp) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = strIp + "/weidevogelsBE/webresources/appweidevogels.vrijwilliger";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        uitLezenAlleVrijwilligers(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
        queue.add(stringRequest);


        //String response is het resultaat van de query (in xml format)

    }
}
