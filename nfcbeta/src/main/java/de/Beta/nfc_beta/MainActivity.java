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

    /* Activity Result Codes*/
    public static final int RQS_PICK_CONTACT = 2;
    public static final int RQS_PICK_SOUND = 3;
    public static final int RQS_PICK_IMAGE = 4;
    /*END*/

    public static NFCFramework framework;
    public static InterfaceUI iface;
    public static DebugFragment df;
    public static wContactFragment wcf;
    public static wTextFragment wtf;
    public static wPictureFragment wpf;
    public static wSoundFragment wsf;
    public static wURLFragment wuf;
    public static FragmentManager fragmentManager;
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //TODO FATAL ERROR iface initialized before framwork, framework requires iface
        //TODO fix obstacle

        //framework = new NFCFramework(this, iface);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));



        df = new DebugFragment();
        wcf = wContactFragment.newInstance(5);
        wpf = wPictureFragment.newInstance(7);
        wsf = wSoundFragment.newInstance(8);
        wtf = wTextFragment.newInstance(9);
        wuf = wURLFragment.newInstance(10);

        iface = new InterfaceUI(this);
        //framework.installService(); //TODO BUGT RUM!


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position+1){
            //SKIP 1 weil überschrift NFC-Beta
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, DebugFragment.newInstance(position + 1))
                        .commit();
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, InfoFragment.newInstance(position + 1))
                        .commit();
                break;
            //SKIP 4 weil überschrift Schreiben
            case 5:
                fragmentManager.beginTransaction()
                        .replace(R.id.container,wcf)
                        .commit();
                break;
            case 6:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, wMuteFragment.newInstance(position + 1))
                        .commit();
                break;
            case 7:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, wpf)
                        .commit();
                break;
            case 8:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, wsf)
                        .commit();
                break;
            case 9:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, wtf)
                        .commit();
                break;
            case 10:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, wuf)
                        .commit();
                break;

        }

    }



    public void onSectionAttached(int number) {
        switch (number) {
            //SKIP 1 weil überschrift NFC-Beta
            case 2:
                mTitle = getString(R.string.title_section1);
                break;
            case 3:
                mTitle = getString(R.string.title_section2);
                break;
            //SKIP 4 weil überschrift Schreiben
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
        /*int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }*/
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
                framework.installService();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RQS_PICK_CONTACT:
                if (resultCode == RESULT_OK) {
                    Uri contactData = data.getData();

                    Cursor cursor = managedQuery(contactData, null, null, null, null);
                    cursor.moveToFirst();
                    String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                    Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);
                    AssetFileDescriptor fd;
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String nummer = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    try {
                        fd = getContentResolver().openAssetFileDescriptor(uri, "r");
                        FileInputStream fis = fd.createInputStream();
                        byte[] buf = new byte[(int) fd.getDeclaredLength()];
                        fis.read(buf);
                        wcf.setContactPayload(new String(buf), name, nummer);
                        Toast.makeText(this, "Contact: " + name + " selected to write on NFC-Tag!", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load Contact: " + name, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Failure result, could not load Contact.", Toast.LENGTH_SHORT).show();
                }
                break;
            case RQS_PICK_SOUND:
                // sound zum schreiben vorbereiten
                break;
            case RQS_PICK_IMAGE:
                // image zum schreiben vorbereiten
                break;
            default:
                iface.printDebugWarn("Activity Result unknown!");
                break;
        }
    }


}
