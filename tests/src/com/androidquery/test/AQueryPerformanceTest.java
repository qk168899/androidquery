package com.androidquery.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;
import com.androidquery.util.XmlDom;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class AQueryPerformanceTest extends AbstractTest<AQueryTestActivity> {

	
	public AQueryPerformanceTest() throws SAXException {		
		super(AQueryTestActivity.class);
		
		
    }
	
	protected void setUp() throws Exception {
		
		AjaxCallback.setNetworkLimit(8);
		
        super.setUp();
        
    }
	
	private  AjaxCallback<String> create(){
		String url = "http://www.kyotojp.com/limousine-big5.html";
		AjaxCallback<String> cb = new AjaxCallback<String>();
		cb.url(url).type(String.class).weakHandler(this, "serialCb");
		return cb;
	}
	
	
	public void testSerialHttpGet() {
		
		AQUtility.time("serial");
		
		for(int i = 0; i < 8; i++){			
			AjaxCallback<String> cb = create();
			cb.async(getActivity());		
			cb.block();
		}
		
		AQUtility.timeEnd("serial", 0);
		
    }
	
	public void testParallelHttpGet() {
		
		AQUtility.time("parallel");
		
		for(int i = 0; i < 8; i++){			
			AjaxCallback<String> cb = create();
			cb.async(getActivity());
		}
		
		waitAsync(10000);
		
		
		
    }
	
	
	public void serialCb(String url, String html, AjaxStatus status){
		
		AQUtility.debug("+");
		AQUtility.timeEnd("parallel", 0);
	}
	
}
