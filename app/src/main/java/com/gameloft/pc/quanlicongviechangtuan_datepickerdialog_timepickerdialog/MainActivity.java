package com.gameloft.pc.quanlicongviechangtuan_datepickerdialog_timepickerdialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText edtCv, edtNd;
    TextView txtDate,txtTime;
    Button btnDate, btnTime, btnAdd;
    ListView lvCongViec;

    //Khai báo Datasource lưu trữ danh sách công việc
    ArrayList<CongViecTrongTuan> arrCongViec=new ArrayList<CongViecTrongTuan>();

    //Khai báo ArrayAdapter cho ListView
    ArrayAdapter<CongViecTrongTuan> adapter=null;

    Calendar cal;
    Date dateFinish;
    Date hourFinish;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFormWidgets();
        getDefaultInfor();
        addEventFormWidgets();

    }

    //hàm dùng để load các control theo Id (ánh xạ)
    private void getFormWidgets() {
        edtCv=(EditText)findViewById(R.id.edtcongviec);
        edtNd= (EditText) findViewById(R.id.edtnoidung);
        txtDate= (TextView) findViewById(R.id.txtdate);
        txtTime=(TextView)findViewById(R.id.txttime);
        btnDate=(Button)findViewById(R.id.btndate);
        btnTime=(Button)findViewById(R.id.btntime);
        btnAdd=(Button)findViewById(R.id.btncongviec);
        lvCongViec=(ListView)findViewById(R.id.lvcongviec);

        //Gán DataSource vào ArrayAdapter
        adapter=new ArrayAdapter<CongViecTrongTuan>(this, android.R.layout.simple_list_item_1, arrCongViec);

        //gán Adapter vào ListView
        lvCongViec.setAdapter(adapter);

    }

    //hàm lấy các thông số mặc định khi ứng dụng chạy lần đầu tiên
    private void getDefaultInfor() {
        //lấy ngày hiện tại của hệ thống
        cal= Calendar.getInstance();
        SimpleDateFormat sdf=null;

        //Định dạng ngày / tháng /năm
        sdf=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=sdf.format(cal.getTime());
        //Hiển thị lên giao diện
        txtDate.setText(strDate);

        //Định dạng giờ phút am/pm
        sdf=new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String strTime=sdf.format(cal.getTime());
        //hiển thị lên giao diện
        txtTime.setText(strTime);

        //lấy giờ theo 24 để lập trình theo Tag
        sdf=new SimpleDateFormat("HH:mm",Locale.getDefault());
        txtTime.setTag(sdf.format(cal.getTime()));

        //đưa con trỏ về lại edtCv sau khi nhấn button
        edtCv.requestFocus();

        //gán cal.getTime() cho ngày hoàn thành và giờ hoàn thành
        dateFinish=cal.getTime();
        hourFinish=cal.getTime();
    }


    //Hàm gán các sự kiện cho các control
    private void addEventFormWidgets() {
        //sử dụng bắt sự kiện kiểu
        btnDate.setOnClickListener(new MyButtonEvent());
        btnTime.setOnClickListener(new MyButtonEvent());
        btnAdd.setOnClickListener(new MyButtonEvent());

        lvCongViec.setOnItemClickListener(new MyListviewEvent());
        lvCongViec.setOnItemLongClickListener(new MyListviewEvent());

    }

    private class MyButtonEvent implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.btndate:
                    showDatePickerDialog();
                    break;
                case R.id.btntime:
                    showTimePickerDialog();
                    break;
                case R.id.btncongviec:
                    xuLiThemCongViec();
                    break;
            }


        }
    }

    //Class sự kiện của ListView
    private class MyListviewEvent implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //Hiển thị nội dung công việc tại vị trí thứ i
            Toast.makeText(MainActivity.this, arrCongViec.get(i).getDescription(), Toast.LENGTH_LONG).show();
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            //xóa dòng thứ i
            arrCongViec.remove(i);
            //đặt lại dữ liệu sau khi xóa dòng i
            adapter.notifyDataSetChanged();
            return false;
        }
    }

    //Hàm hiển thị DatePicker dialog
    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                txtDate.setText(dayOfMonth +"/"+ (monthOfYear+1) +"/"+ year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish=cal.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=txtDate.getText()+"";
        //dùng split để cắt chuỗi lấy ranh giới là "/"
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        //tháng tính từ 0->11 nên lấy từ chuỗi định dạng 1->12 thì phải trừ đi 1
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(MainActivity.this, callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành ");
        pic.show();

    }

    //Hàm hiển thị TimePickerDialog
    private void showTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener callback=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                //Xử lý lưu giờ và AM,PM
                String s= hourOfDay+":"+minute;
                int hourTam=hourOfDay;
                if(hourTam>12)
                    hourTam=hourTam-12;
                txtTime.setText(hourTam+":"+minute+ (hourOfDay>12?" PM":" AM"));
                //lưu giờ thực vào tag
                txtTime.setTag(s);

                //lưu vết lại giờ vào hourFinish
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                hourFinish=cal.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong TimePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=txtTime.getTag()+"";
        String strArr[]=s.split(":");
        int gio=Integer.parseInt(strArr[0]);
        int phut=Integer.parseInt(strArr[1]);
        TimePickerDialog time=new TimePickerDialog(MainActivity.this, callback, gio, phut, true);
        time.setTitle("Chọn giờ hoàn thành");
        time.show();

    }

    //Hàm xử lý đưa công việc vào ListView khi nhấn nút Thêm Công việc
    private void xuLiThemCongViec() {
        String title=edtCv.getText()+"";
        String decription=edtNd.getText()+"";
        CongViecTrongTuan congviec=new CongViecTrongTuan(title,decription,dateFinish,hourFinish);
        arrCongViec.add(congviec);
        adapter.notifyDataSetChanged();
        //sau khi cập nhật thì reset dữ liệu và cho focus tới editCV
        edtCv.setText("");
        edtNd.setText("");
        edtCv.requestFocus();
    }



}
