package com.example.froukje.countingbirds;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.View;
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
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilderFactory;

public class BedrijfActivity extends NavigationDrawer {

    private DataReader dr = new DataReader();
    private ListView lvUpper;
    private ArrayAdapter<String> listAdapter;
    private TextView tvBedrijf,tvBedrijfID,tvEigenaarID,tvTelefoon,tvEmail,tvPostCode,tvPlaats,tvStraat,tvBoerGebied;
    private String bedrijfNaam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bedrijf);
        buildMenu();
        getDataAlleBedrijven(dr.getStrIp());


        lvUpper = (ListView) findViewById(R.id.lvUpper);
        tvBedrijf = (TextView) findViewById(R.id.tvBedrijf);
        tvBedrijfID = (TextView) findViewById(R.id.tvBedrijfID);
        tvEigenaarID = (TextView) findViewById(R.id.tvEigenaarID);
        tvTelefoon = (TextView) findViewById(R.id.tvTelefoon);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvPostCode = (TextView) findViewById(R.id.tvPostCode);
        tvPlaats = (TextView) findViewById(R.id.tvPlaats);
        tvStraat = (TextView) findViewById(R.id.tvStraat);
        tvBoerGebied = (TextView) findViewById(R.id.tvBoerGebied);

        /**String[] planets = new String[]{"Baalen, C.A van", "Baalen, J.A./H.G van", "Baalen, L.", "Boesterd, W en A den", "Bos, B", "Bosch, P van den", "Bruyn, A.F de ",
         "Gameren, H van ", "Heiningen / SBB, H van ", "Hertog, E den ", "Jongh, C de ", "Kardol, L.H ", "Kock, A ,", "Kool, F ", "Land, T.L van't ", "Laponder, A ", "Noord, T van ",
         "Oort, H van ", "Pippel, H ", "Pippel, T ", "Satter, H ", "Noord, J van ", "Vervoorn, G ", "Vervoorn-Van Ooijen, A ", "Zandwijk, A en H van ", "Zandwijk, H van",
         "Zandwijk, K van "};
         ArrayList<String> bedrijfList = new ArrayList<String>();
         bedrijfList.addAll(Arrays.asList(planets));

<<<<<<< HEAD
         listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bedrijfList);
=======

        lvUpper.setAdapter(listAdapter);
>>>>>>> Joey

         lvUpper.setAdapter(listAdapter);**/


        ListView lv = (ListView) findViewById(R.id.lvUpper);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bedrijfNaam = adapterView.getAdapter().getItem(i).toString();
                getData(dr.getStrIp());
            }
        });

    }
    private void getData(String strIp) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = strIp +"/weidevogelsBE/webresources/appweidevogels.bedrijf";
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
        //String response is het resultaat van de query (in xml format)
    }
    private void uitLezen(String response) {

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response)));
            NodeList bedrijven = doc.getElementsByTagName("bedrijfs");
            NodeList alBedrijven = (bedrijven.item(0)).getChildNodes();

            for (int i = 0; i < alBedrijven.getLength(); i++) {
                Element n = (Element) alBedrijven.item(i);

                NodeList tag1 = n.getElementsByTagName("bedrijfnaam");
                Node bedrijfNaamNode = tag1.item(0);

                if (bedrijfNaam.equals(bedrijfNaamNode.getTextContent())) {
                    NodeList tag = n.getElementsByTagName("bedrijfid");
                    Node bedrijfIdNode = tag.item(0);
                    tvBedrijfID.setText(bedrijfIdNode.getTextContent());

                    tvBedrijf.setText(bedrijfNaamNode.getTextContent());

                    NodeList tag2 = n.getElementsByTagName("boergebied");
                    Node boergebiedNode = tag2.item(0).getFirstChild();
                    tvBoerGebied.setText(boergebiedNode.getTextContent());

                    NodeList tag3 = n.getElementsByTagName("eigenaarid");
                    Node eigenaarIdNode = tag3.item(0).getFirstChild();
                    tvEigenaarID.setText(eigenaarIdNode.getTextContent());

                    try {
                        NodeList tag4 = n.getElementsByTagName("email");
                        Node emailNode = tag4.item(0);
                        tvEmail.setText(emailNode.getTextContent());
                    } catch (Exception e) {
                        tvEmail.setText("Email onbekend");
                    }

                    NodeList tag5 = n.getElementsByTagName("plaats");
                    Node plaatsNode = tag5.item(0);
                    tvPlaats.setText(plaatsNode.getTextContent());

                    NodeList tag6 = n.getElementsByTagName("postcode");
                    Node pcNode = tag6.item(0);
                    tvPostCode.setText(pcNode.getTextContent());

                    NodeList tag7 = n.getElementsByTagName("straat");
                    Node straatNode = tag7.item(0);
                    tvStraat.setText(straatNode.getTextContent());

                    try {
                        NodeList tag8 = n.getElementsByTagName("telefoonnummer");
                        Node telefoonNode = tag8.item(0);
                        tvTelefoon.setText(telefoonNode.getTextContent());
                    } catch (Exception e) {
                        tvTelefoon.setText("Telefoonnummer onbekend");
                    }

                    break;
                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void getDataAlleBedrijven(String strIp) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = strIp +"/weidevogelsBE/webresources/appweidevogels.bedrijf" ;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        uitLezenAlleBedrijven(response);
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
    private void uitLezenAlleBedrijven(String response) {

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response)));
            NodeList bedrijven = doc.getElementsByTagName("bedrijfs");
            NodeList alBedrijven = (bedrijven.item(0)).getChildNodes();
            ArrayList<String> theBedrijven = new ArrayList<>();
            for (int i = 0; i < alBedrijven.getLength(); i++) {
                Node n = alBedrijven.item(i);
                Node bedrijfNaam = n.getChildNodes().item(1);

                theBedrijven.add(bedrijfNaam.getTextContent());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, theBedrijven);
            ListView lv = (ListView) findViewById(R.id.lvUpper);
            lv.setAdapter(adapter);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}