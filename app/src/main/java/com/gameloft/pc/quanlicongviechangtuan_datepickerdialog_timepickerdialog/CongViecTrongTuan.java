package com.gameloft.pc.quanlicongviechangtuan_datepickerdialog_timepickerdialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by PC on 9/19/2017.
 */

public class CongViecTrongTuan {
    private String title;
    private String description;
    private Date dateFinish;
    private Date hourFinish;

    public CongViecTrongTuan() {
    }

    public CongViecTrongTuan(String title, String description, Date dateFinish, Date hourFinish) {

        this.title = title;
        this.description = description;
        this.dateFinish = dateFinish;
        this.hourFinish = hourFinish;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    public Date getHourFinish() {
        return hourFinish;
    }

    public void setHourFinish(Date hourFinish) {
        this.hourFinish = hourFinish;
    }

    //lấy định dạng ngày:
    public String getDateFomat(Date d)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(d);
    }

    //lấy định dạng giờ phút:
    public String getHourFomat(Date d)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(d);
    }

    @Override
    public String toString() {
        return this.title +"-"+ getDateFomat(this.dateFinish) +"-"+ getHourFomat(this.hourFinish);
    }
}
