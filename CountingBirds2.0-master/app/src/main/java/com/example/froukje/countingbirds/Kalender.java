package com.example.froukje.countingbirds;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.froukje.countingbirds.Classes.Persoon;
import com.example.froukje.countingbirds.Classes.Vrijwilliger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

public class Kalender extends NavigationDrawer {

    private String tagB = "";
    private DataReader dr = new DataReader();
    private ArrayList<Vrijwilliger> deVrijwilliger = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender);
        buildMenu();

        getDataAlleVrijwilligers(dr.getStrIp());

        ListView list = (ListView) findViewById(R.id.calendar_list);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setDate(System.currentTimeMillis(), false, true);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {

                String date = "";

                month += 1;

                if (day < 10) {
                    date += "0" + day;
                } else {
                    date += day;
                }

                if (month < 10) {
                    date += "/0" + month;
                } else {
                    date += "/" + month;
                }

                date += "/" + year;
                if (date.equals("06/04/2018")) {


                } else {

                }
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(Kalender.this, KalenderNext.class);
                String user = (String) adapterView.getItemAtPosition(position);
                for (Vrijwilliger vrijwilliger : deVrijwilliger) {
                    if (vrijwilliger.getNaam().equals(user)) {
                        intent.putExtra("Input", user);
                        break;
                    }
                }
                startActivity(intent);
            }
        });
    }

    private void uitLezenAlleVrijwilligers(String response) {

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response)));
            NodeList vrijwilligers = doc.getElementsByTagName("vrijwilligers");
            NodeList alleVrijwilligers = (vrijwilligers.item(0)).getChildNodes();

            for (int i = 0; i < alleVrijwilligers.getLength(); i++) {
                Element n = (Element) alleVrijwilligers.item(i);
                NodeList tag = n.getElementsByTagName("bsn");

                Node bsn = tag.item(0);
                NodeList tag1 = n.getElementsByTagName("huisnummer");
                Node huisnummer = tag1.item(0);
                NodeList tag2 = n.getElementsByTagName("naam");
                Node naam = tag2.item(0);
                NodeList tag3 = n.getElementsByTagName("postcode");
                Node postcode = tag3.item(0);

                Vrijwilliger vrijwilliger = new Vrijwilliger(bsn.getTextContent(), huisnummer.getTextContent(), naam.getTextContent(), postcode.getTextContent());


                deVrijwilliger.add(vrijwilliger);


            }

            ArrayList<String> namen = new ArrayList<>();
            for (Vrijwilliger x : deVrijwilliger) {
                namen.add(x.getNaam());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, namen);
            ListView lv = (ListView) findViewById(R.id.calendar_list);
            lv.setAdapter(adapter);

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
