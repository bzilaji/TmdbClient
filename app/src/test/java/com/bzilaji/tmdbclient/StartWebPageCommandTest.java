package com.bzilaji.tmdbclient;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.bzilaji.tmdbclient.utils.StartWebPageCommand;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class StartWebPageCommandTest {

    public static final String DEFAULT_URL = "default_url";
    @Mock
    Context context;
    @Mock
    PackageManager packageManager;

    private Intent intent;
    private Intent emptyIntent;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        intent = new Intent();
        emptyIntent = new Intent();
        List<ResolveInfo> list = new ArrayList<>(1);
        list.add(new ResolveInfo());
        List<ResolveInfo> empty_list = new ArrayList<>(1);
        Mockito.when(context.getPackageManager()).thenReturn(packageManager);
        Mockito.when(packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY)).thenReturn(list);
        Mockito.when(packageManager.queryIntentActivities(emptyIntent,
                PackageManager.MATCH_DEFAULT_ONLY)).thenReturn(empty_list);

    }

    @Test
    public void testStartWebPageCommandShouldStartActivity() {
        StartWebPageCommand sut = new TestableStartWebPageCommand(DEFAULT_URL, context, intent);
        sut.execute();
        verify(packageManager).queryIntentActivities(eq(intent),
                eq(PackageManager.MATCH_DEFAULT_ONLY));
        verify(context).startActivity(intent);
    }

    @Test
    public void testStartWebPageCommandWhenIntentCanNotBeHandledShouldNotStartActivity() {
        StartWebPageCommand sut = new TestableStartWebPageCommand(DEFAULT_URL, context, emptyIntent);
        sut.execute();
        verify(packageManager).queryIntentActivities(eq(emptyIntent),
                eq(PackageManager.MATCH_DEFAULT_ONLY));
        verify(context, times(0)).startActivity(intent);
    }

    private class TestableStartWebPageCommand extends StartWebPageCommand {

        private final Intent intent;

        public TestableStartWebPageCommand(String url, Context context, Intent intent) {
            super(url, context);
            this.intent = intent;
        }

        @Override
        protected Intent createIntent() {
            return intent;
        }
    }
}
