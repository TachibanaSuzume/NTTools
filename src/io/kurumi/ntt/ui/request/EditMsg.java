package io.kurumi.ntt.ui.request;

import cn.hutool.core.util.*;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.*;
import io.kurumi.ntt.*;
import io.kurumi.ntt.twitter.*;
import io.kurumi.ntt.ui.*;
import io.kurumi.ntt.ui.model.*;
import java.util.*;

public class EditMsg extends AbsSendMsg {

    private EditMessageText edit;

    public EditMsg(Message msg, String... editMsg) {

        String contnet = msg.text();

        if (editMsg.length != 0) {

            contnet = ArrayUtil.join(editMsg, "\n");

        }

        edit = new EditMessageText(msg.chat().id(), msg.messageId(), contnet);

    }

    @Override
    public EditMsg html() {

        edit.parseMode(ParseMode.HTML);

        return this;

    }

    @Override
    public EditMsg markdown() {

        edit.parseMode(ParseMode.Markdown);

        return this;

    }

    @Override
    public EditMsg disableWebPagePreview() {

        edit.disableWebPagePreview(true);

        return this;

    }

    @Override
    public EditMsg disableNotification() {

        edit.disableWebPagePreview(true);

        return this;

    }

    @Override
    public EditMsg singleLineButton(String text, DataObject obj) {

        super.singleLineButton(text, obj);

        return this;

    }

    @Override
    public EditMsg singleLineButton(String text, String point) {

        super.singleLineButton(text, point);

        return this;

    }


    @Override
    public EditMsg singleLineButton(String text, String point, TwiAccount acc) {

        super.singleLineButton(text, point, acc);

        return this;

    }

    @Override
    public void exec() {

        if (inlineKeyBoardGroups.size() != 0) {

            InlineKeyboardButton[][]  markup = new InlineKeyboardButton[inlineKeyBoardGroups.size()][];

            for(int index = 0;index < inlineKeyBoardGroups.size();index ++) {

                markup[index] = inlineKeyBoardGroups.get(index).getButtonArray();

            }

            edit.replyMarkup(new InlineKeyboardMarkup(markup));
            
        }

        Constants.bot.execute(edit);

    }

}