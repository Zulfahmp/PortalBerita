package com.zulfa.portalberita;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zulfa.portalberita.crud.Konfigurasi;
import com.zulfa.portalberita.crud.RequestHandler;
import com.zulfa.portalberita.crud.TambahActivity;
import com.zulfa.portalberita.crud.TampilActivity;
import com.zulfa.portalberita.crud.UpDelActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Pengaduan extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private String JSON_STRING;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pengaduanfragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = (ListView)view.findViewById(R.id.listview);
        fab = (FloatingActionButton) view.findViewById(R.id.tambah);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(getContext(), TambahActivity.class);
                startActivity(b);
            }
        });

        listView.setOnItemClickListener(this);
        getJSON();
    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi.TAG_ID);
                String name = jo.getString(Konfigurasi.TAG_NAMA);
                String nim = jo.getString(Konfigurasi.TAG_NIM);
                String alamat = jo.getString(Konfigurasi.TAG_ALAMAT);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Konfigurasi.TAG_ID, id);
                employees.put(Konfigurasi.TAG_NAMA, name);
                employees.put(Konfigurasi.TAG_NIM, nim);
                employees.put(Konfigurasi.TAG_ALAMAT, alamat);
                list.add(employees);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getContext(), list , R.layout.list_item,
                new String[]{Konfigurasi.TAG_ID,Konfigurasi.TAG_NAMA,Konfigurasi.TAG_NIM,Konfigurasi.TAG_ALAMAT},
                new int[]{R.id.tvid, R.id.name, R.id.nim, R.id.alamat});
        listView.setAdapter(adapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Mengambil Data","Mohon Tunggu...",false,false);
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Konfigurasi.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent a = new Intent(getContext(), UpDelActivity.class);
        HashMap<String, String> map = (HashMap)parent.getItemAtPosition(position);
        String empId = map.get(Konfigurasi.TAG_ID).toString();
        a.putExtra(Konfigurasi.EMP_ID, empId);
        startActivity(a);
    }
}
