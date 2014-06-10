package org.hsc.silk.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hsc.silk.dao.AuditTypeDao;
import org.hsc.silk.dao.ChecklistDao;
import org.hsc.silk.dao.ChecklistItemDao;
import org.hsc.silk.dao.RegulationDao;
import org.hsc.silk.dao.RegulationItemDao;
import org.hsc.silk.dao.RemarkChoiceDao;
import org.hsc.silk.model.AuditType;
import org.hsc.silk.model.BpLocation;
import org.hsc.silk.model.Bpartner;
import org.hsc.silk.model.Checklist;
import org.hsc.silk.model.ChecklistItem;
import org.hsc.silk.model.Regulation;
import org.hsc.silk.model.RegulationItem;
import org.hsc.silk.model.RemarkChoice;
import org.hsc.silk.model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SILKClient {
	public static final String tag = "SILKClient";
	public static final String SILK_URL = "http://161.200.192.38/SilkWeb/1";
	public static final String USER_PATH = "/silkuser";
	public static final String CHECKLIST_PATH = "/checklist";
	public static final String CHECKLIST_ITEM_PATH = "/checklistItem";
	public static final String REGULATION_PATH = "/regulation";
	public static final String REGULATION_ITEM_PATH = "/regulationItem";
	public static final String AUDIT_TYPE_PATH = "/auditType";
	public static final String BPARTNER_PATH = "/bpartner?search=";
	public static final String BP_LOCATION_PATH = "/bpLocation?bpid=";
	public static final String REMARK_CHOICE_PATH = "/remarkChoice";
	public static final int MAX_UPDATE_PROGRESS = 100;
	
	private ChecklistDao checklistDao;
	private ChecklistItemDao checklistItemDao;
	private RegulationDao regulationDao;
	private RegulationItemDao regulationItemDao;
	private AuditTypeDao auditTypeDao;
	private RemarkChoiceDao remarkChoiceDao;
	
	public SILKClient(Context context){
		checklistDao = new ChecklistDao(context);
		checklistItemDao = new ChecklistItemDao(context);
		regulationDao = new RegulationDao(context);
		regulationItemDao = new RegulationItemDao(context);
		auditTypeDao = new AuditTypeDao(context);
		remarkChoiceDao = new RemarkChoiceDao(context);
	}

	private class NetworkTask extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			String json = null;
			String url = params[0];
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse;
			try {
				httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				InputStream is = httpEntity.getContent();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						is, "utf-8"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				json = sb.toString();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json;
		}
		
	}
	
	public interface SILKClientListener{
		public void onProgressChanged(int progress);
		public void onUpdateFinished();
	}
	
	public void updateData(SILKClientListener listener){
		int progress = 0;
		listener.onProgressChanged(progress);
		List<Checklist> checklistList = getChecklistList();
		checklistDao.save(checklistList);
		progress+=20;
		listener.onProgressChanged(progress);
		
		List<ChecklistItem> checklistItemList = getChecklistItemList();
		checklistItemDao.save(checklistItemList);
		progress+=20;
		listener.onProgressChanged(progress);
		
		List<Regulation> regulationList = getRegulationList();
		regulationDao.save(regulationList);
		progress+=20;
		listener.onProgressChanged(progress);
		
		List<RegulationItem> regulationItemList = getRegulationItemList();
		regulationItemDao.save(regulationItemList);
		progress+=20;
		listener.onProgressChanged(progress);
		
		List<AuditType> auditTypeList = getAuditTypeList();
		auditTypeDao.save(auditTypeList);
		progress+=10;
		listener.onProgressChanged(progress);
		
		List<RemarkChoice> remarkChoiceList = getRemarkChoiceList();
		remarkChoiceDao.save(remarkChoiceList);
		progress+=10;
		listener.onProgressChanged(progress);
		
		listener.onUpdateFinished();
	}
	
	public String getJsonStringFromUrl(String url){
		String json = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream is = httpEntity.getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(tag, "IOException");
			e.printStackTrace();
		}
		return json;
	}
	
	/*
	public String getJsonStringFromUrl(String url){
		String json = null;
		NetworkTask task = new NetworkTask();
		try {
			json = task.execute(url).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	*/
	public User getUser(String name, String password){
		Log.i(tag, "getUser : " + name);
		StringBuilder sb = new StringBuilder(SILK_URL+USER_PATH) ;
		sb.append("?name="+name);
		sb.append("&password="+password);
		Log.i(tag, "url : " + sb.toString());
		User user = null;
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(getJsonStringFromUrl(sb.toString()));
			Log.i(tag, jsonObj.toString());
			if(jsonObj != null){
				user = User.newInstance(jsonObj);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}

	public List<Checklist> getChecklistList() {
		StringBuilder sb = new StringBuilder(SILK_URL+CHECKLIST_PATH) ;
		List<Checklist> checklistList = null;
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(getJsonStringFromUrl(sb.toString()));
			if(jsonArray != null){
				checklistList = Checklist.newInstanceList(jsonArray);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return checklistList;
	}


	public List<ChecklistItem> getChecklistItemList() {
		StringBuilder sb = new StringBuilder(SILK_URL+CHECKLIST_ITEM_PATH) ;
		List<ChecklistItem> list = null;
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(getJsonStringFromUrl(sb.toString()));
			if(jsonArray != null){
				list = ChecklistItem.newInstanceList(jsonArray);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}


	public List<Regulation> getRegulationList() {
		StringBuilder sb = new StringBuilder(SILK_URL+REGULATION_PATH) ;
		List<Regulation> list = null;
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(getJsonStringFromUrl(sb.toString()));
			if(jsonArray != null){
				list = Regulation.newInstanceList(jsonArray);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}


	public List<RegulationItem> getRegulationItemList() {
		StringBuilder sb = new StringBuilder(SILK_URL+REGULATION_ITEM_PATH) ;
		List<RegulationItem> list = null;
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(getJsonStringFromUrl(sb.toString()));
			if(jsonArray != null){
				list = RegulationItem.newInstanceList(jsonArray);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}


	public List<AuditType> getAuditTypeList() {
		StringBuilder sb = new StringBuilder(SILK_URL+AUDIT_TYPE_PATH) ;
		List<AuditType> list = null;
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(getJsonStringFromUrl(sb.toString()));
			if(jsonArray != null){
				list = AuditType.newInstanceList(jsonArray);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}


	public List<RemarkChoice> getRemarkChoiceList() {
		StringBuilder sb = new StringBuilder(SILK_URL+REMARK_CHOICE_PATH) ;
		List<RemarkChoice> list = null;
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(getJsonStringFromUrl(sb.toString()));
			if(jsonArray != null){
				list = RemarkChoice.newInstanceList(jsonArray);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	public List<Bpartner> getBPartnerList(String searchName) {
		StringBuilder urlBuilder = new StringBuilder(SILK_URL+BPARTNER_PATH);
		List<Bpartner> bpList = new ArrayList<Bpartner>();
		JSONArray jsonArray;
		try {
			urlBuilder.append(new String(searchName.getBytes(), "utf-8"));
			jsonArray = new JSONArray(getJsonStringFromUrl(urlBuilder.toString()));
			if(jsonArray != null){
				bpList = Bpartner.newInstanceList(jsonArray);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return bpList;	
	}
	public List<BpLocation> getBPLocationList(int bpid){
		StringBuilder urlBuilder = new StringBuilder(SILK_URL+BP_LOCATION_PATH);
		List<BpLocation> bpLocationList = new ArrayList<BpLocation>();
		JSONArray jsonArray;
		try {
			urlBuilder.append(String.valueOf(bpid));
			jsonArray = new JSONArray(getJsonStringFromUrl(urlBuilder.toString()));
			if(jsonArray != null){
				bpLocationList = BpLocation.newInstanceList(jsonArray);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return bpLocationList;	
	}

	
}
