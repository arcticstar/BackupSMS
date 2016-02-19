package com.auxgroup.backupsms;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auxgroup.backupsms.entities.SmsInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SmsInfo sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_backupsms= (Button) findViewById(R.id.btn_backupsms);
        btn_backupsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backupSms();
            }
        });
    }

    private void backupSms() {
        //1.查出所有的短信；
        Uri uri = Uri.parse("content://sms/");
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{"_id", "address", "date", "type", "body"}, null, null, null);
        if (cursor != null&&cursor.getCount()>0) {
            List<SmsInfo> smsInfoList=new ArrayList<>();
            while (cursor.moveToNext()){
                sms = new SmsInfo();
                sms.setId(cursor.getInt(0));//设置短信的id；
                sms.setAddress(cursor.getString(1));//设置短信收件人地址
                sms.setDate(cursor.getLong(2));
                sms.setType(cursor.getInt(3));
                sms.setBody(cursor.getString(4));
                smsInfoList.add(sms);
            }
            cursor.close();
            //2.序列化到本地
            writeToLocal(smsInfoList);
        }

    }

    private void writeToLocal(List<SmsInfo> smsInfoList) {
        XmlSerializer serializer = Xml.newSerializer();
        FileOutputStream fos= null;
        try {
            fos = new FileOutputStream("/mnt/sdcard/sms.xml");
            serializer.setOutput(fos,"utf-8");
            serializer.startDocument("utf-8",true);
            serializer.startTag(null,"smss");
            for (SmsInfo smsInfo:smsInfoList) {
                serializer.startTag(null,"sms");
                serializer.attribute(null,"id", String.valueOf(smsInfo.getId()));
                //写地址
                serializer.startTag(null,"address");
                serializer.text(smsInfo.getAddress());
                serializer.endTag(null,"address");
                //写时间
                serializer.startTag(null,"date");
                serializer.text(String.valueOf(smsInfo.getDate()));
                serializer.endTag(null,"date");
                //写类型
                serializer.startTag(null,"type");
                serializer.text(String.valueOf(smsInfo.getType()));
                serializer.endTag(null,"type");
                //写内容
                serializer.startTag(null,"body");
                serializer.text(smsInfo.getBody());
                serializer.endTag(null,"body");

                serializer.endTag(null,"sms");
            }
            serializer.endTag(null,"smss");
            
            serializer.endDocument();
            Toast.makeText(this,"备份成功",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this,"备份失败",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"备份失败",Toast.LENGTH_SHORT).show();
        }


    }
}
