package com.eipteam.healthsafe;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.spy;

@Config(sdk = Build.VERSION_CODES.O_MR1)

@RunWith(RobolectricTestRunner.class)
//@PrepareForTest({ ReportFragment.class })
public class MainActivityUnitTest extends TestCase {
  private Application context;
  private MainActivity ma;
  private ChooseDisplay cd;

    @Before
    public void setUp() throws Exception
    {
        ma = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
        cd = Robolectric.buildActivity(ChooseDisplay.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void testOnCreate() {
        MainActivity spyma = spy(new MainActivity());
        Button btn = (Button) ma.findViewById(R.id.Tconnexion);
        assertNotNull(ma);
        btn.getAccessibilityClassName();
        int id = btn.getId();
        assertEquals(R.id.Tconnexion, id);
        }

    @Test()
    public void testConnection() {
        Bundle b = new Bundle();
        PersistableBundle pb = new PersistableBundle();
        ma.onSaveInstanceState(b, pb);
        assertNotNull(ma);

        }
}
