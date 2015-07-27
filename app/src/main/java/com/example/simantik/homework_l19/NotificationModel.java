package com.example.simantik.homework_l19;

/**
 * Created by simantik on 25.07.2015.
 */
public class NotificationModel {
    public String message;
    public String title;
    public String subtitle;
    public String tickerText;
    public int vibrate;
    public int sound;

    public NotificationModel(String _message,String _title,String _subtitle,String _tickerText,int _vibrate, int _sound){
        message=_message;
        title=_title;
        subtitle=_subtitle;
        tickerText=_tickerText;
        vibrate=_vibrate;
        sound=_sound;
    }
}
