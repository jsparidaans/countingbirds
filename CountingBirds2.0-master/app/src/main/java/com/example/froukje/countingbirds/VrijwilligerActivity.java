package com.example.froukje.countingbirds;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class VrijwilligerActivity extends NavigationDrawer {

    private DataReader dr = new DataReader();
    private ArrayList<Vrijwilliger> deVrijwilliger = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrijwilliger);
        buildMenu();


        String[] vrijw = new String[]{"Joey Sparidaans", "Thierry Janson", "Froukje Zeldenrust",
                "Mark Schevers"};

        getDataAlleVrijwilligers(dr.getStrIp());

        final ListView listView = (ListView) findViewById(R.id.lijst);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(VrijwilligerActivity.this, Profiel.class);
                String user = (String) parent.getItemAtPosition(position);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vrijwilliger, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_calendar) {
            Intent intent = new Intent(this, Kalender.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            ListView lv = (ListView) findViewById(R.id.lijst);
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
