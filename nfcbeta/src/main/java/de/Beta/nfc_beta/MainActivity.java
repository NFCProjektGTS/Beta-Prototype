package de.Beta.nfc_beta;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.FileInputStream;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static NFCFramework framework;
    public static InterfaceUI iface;
    public static DebugFragment df;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        df = new DebugFragment();
        iface = new InterfaceUI(this);

        framework = new NFCFramework(this, iface);
        //framework.installService(); //TODO BUGT RUM!


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position+1){
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance(position +1 ))
                        .commit();

                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, DebugFragment.newInstance(position +1))
                        .commit();
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, InfoFragment.newInstance(position +1))
                        .commit();
                break;
            case 4:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, wContactFragment.newInstance(position +1))
                        .commit();
                break;
            case 5:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, wMuteFragment.newInstance(position +1))
                        .commit();
                break;
            case 6:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, wPictureFragment.newInstance(position +1))
                        .commit();
                break;
            case 7:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, wSoundFragment.newInstance(position +1))
                        .commit();
                break;
            case 8:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, wTextFragment.newInstance(position +1))
                        .commit();
                break;
            case 9:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, wURLFragment.newInstance(position +1))
                        .commit();
                break;



        }

    }

    public void showTextFragment(String text){

        getSupportFragmentManager().beginTransaction().replace(R.id.container,TextFragment.newInstance(text)).commit();

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 2:
                mTitle = getString(R.string.title_section1);
                break;
            case 3:
                mTitle = getString(R.string.title_section2);
                break;
            case 5:
                mTitle = getString(R.string.title_section3);
                break;
            case 6:
                mTitle = getString(R.string.title_section4);
                break;
            case 7:
                mTitle = getString(R.string.title_section5);
                break;
            case 8:
                mTitle = getString(R.string.title_section6);
                break;
            case 9:
                mTitle = getString(R.string.title_section7);
                break;
            case 10:
                mTitle = getString(R.string.title_section8);
                break;
           // case 9:
             //   mTitle = getString(R.string.title_section9);
               // break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (framework != null) {
            if (framework.checkNFC()) {
                framework.uninstallService();
            }
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (framework != null) {
            framework.resolveIntent(getIntent());
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        setIntent(new Intent());
        if (framework != null) {
            if (framework.checkNFC()) {
                //framework.installService();//TODO BUGT RUM!
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) { //TODO switch requestCode!!
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();

                Cursor cursor = managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();
                String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);
                AssetFileDescriptor fd;
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                try {
                    fd = getContentResolver().openAssetFileDescriptor(uri, "r");
                    FileInputStream fis = fd.createInputStream();
                    byte[] buf = new byte[(int) fd.getDeclaredLength()];
                    fis.read(buf);
                    framework.setPayload(new String(buf));
                    Toast.makeText(this, "Contact: " + name + " selected to write on NFC-Tag!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Failed to load Contact: " + name, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }
        if (!framework.getPayload().equals("")) {
            framework.createWriteNdef(NdefCreator.vCard(framework.getPayload()));
            framework.enableWrite();
        }
    }

}
