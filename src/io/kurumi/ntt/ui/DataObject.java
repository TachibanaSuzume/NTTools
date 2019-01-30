package io.kurumi.ntt.ui;

import cn.hutool.json.*;
import com.pengrad.telegrambot.model.*;
import io.kurumi.ntt.*;
import io.kurumi.ntt.serialize.*;
import io.kurumi.ntt.twitter.*;
import io.kurumi.ntt.ui.ext.*;
import io.kurumi.ntt.ui.request.*;
import java.io.*;

public class DataObject extends JSONObject {
    
    public CallbackQuery query;
    public Message msg;
    public Chat chat;
    
    public DataObject() {
        super();
    }
    
    public DataObject(String json) {
        super(json);
    }
    
    public DataObject(JSONObject json) {
        super(json);
    }
    
    public DataObject(Message msg) {
        super();
        this.msg = msg;
        this.chat = msg.chat();
    }
    
    public DataObject(CallbackQuery query) {
        super(query.data());
        this.query = query;
        this.msg = query().message();
        this.chat = msg().chat();
    }
    
    public void confirmQuery() {
        
        new AnswerCallback(query).exec();
        
    }
    
    public Message msg() {
        
        return msg;
        
    }
    
    public Chat chat() {
        
        return chat;
        
    }
    
    public String text() {
        
        return msg().text();
        
    }
    
    public void deleteMsg() {
        
        MsgExt.delete(msg());
        
    }
    
    public CallbackQuery query() {
        
        return query;
        
    }
    
    public SendMsg send(String... msg) {
        
        return new SendMsg(chat,msg);
        
    }
    
    public AnswerCallback reply() {

        return new AnswerCallback(query());

    }
    
    public SendMsg reply(String... msg) {

        return new SendMsg(msg(),msg);

    }
    
    public EditMsg edit(String... msg) {
        
        return new EditMsg(msg(),msg);
        
    }
    
    public void setPoint(String point) {
        
        put("p",point);
        
    }
    
    public String getPoint() {
        
        return getStr("p");
        
    }
    
    public void setindex(String point) {

        put("i",point);

    }

    public String getIndex() {

        return getStr("i");

    }
    
    public void setUser(TwiAccount account) {
        
        put("a",account.accountId);
        
    }
    
    public TwiAccount getUser(UserData UserData) {
        
        return UserData.findUser(getLong("a",-1L));
        
    }
    
    public void setUserIndex(UserData u,TwiAccount account) {

        put("ai",u.twitterAccounts.indexOf(account));

    }

    public TwiAccount getUserByIndex(UserData userData) {

        return userData.twitterAccounts.get(getInt("ai"));

    }
    
    
    public DataObject getData(String key) {

        return new DataObject(getJSONObject(key));

    }
    
    
    public void putSerializable(String key,Serializable obj) {
        
        put(key,SerUtil.toString(obj));
        
    }
    
    public <T> T getSerilizable(String key) {
        
        return SerUtil.toObject(getStr(key));
        
    }

}
