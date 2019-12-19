package com.example.froukje.countingbirds;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
<<<<<<< HEAD
=======
import android.os.Bundle;
>>>>>>> Joey
import android.view.MenuItem;

public class NavigationDrawer extends AppCompatActivity {

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        buildMenu();
    }

    public void buildMenu(){

        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerlayout, R.string.Open, R.string.Close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nv = (NavigationView) findViewById(R.id.menu);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem){

                switch (menuItem.getItemId()) {
                    case (R.id.bedrijven):
                        try {
                            Intent intent = new Intent(NavigationDrawer.this, BedrijfActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case (R.id.vrijwilligers):
                        try {
                            Intent intent = new Intent(NavigationDrawer.this, VrijwilligerActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case (R.id.agenda):
                        try {
                            Intent intent = new Intent(NavigationDrawer.this, Kalender.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case (R.id.nesten):
                        try {
                            Intent intent = new Intent(NavigationDrawer.this, Nesten.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;


                    case (R.id.toestand):
                        try {
                            Intent intent = new Intent(NavigationDrawer.this, Toestand.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case (R.id.gps):
                        try {
                            Intent intent = new Intent(NavigationDrawer.this, GPS.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case (R.id.home):
                        try {
                            Intent intent = new Intent(NavigationDrawer.this, Home.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case (R.id.logout):
                        try {
                            Intent intent = new Intent(NavigationDrawer.this, LoginActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                }
                return true;
            }
        });

        /**{
            public void onClick(View view) {

            }
        });**/
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (mToggle.onOptionsItemSelected(item)) {
                return true ;
            }
        return super.onOptionsItemSelected(item);
    }
}
