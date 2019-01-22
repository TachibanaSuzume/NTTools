package io.kurumi.ntt.auth;

import cn.hutool.core.util.*;
import io.kurumi.ntt.twitter.*;
import java.net.*;
import org.nanohttpd.protocols.http.*;
import org.nanohttpd.protocols.http.response.*;
import io.kurumi.ntt.md.*;

public class NanoAuthServer extends NanoHTTPD {

    private AuthManager manager;
    private String domain;

    public NanoAuthServer(AuthManager manager, int port,String domain) {
        super(port);
        this.manager = manager;
        this.domain = domain;
    }
    

    @Override
    public Response handle(IHTTPSession session) {

        URL url = URLUtil.url(session.getUri());

        switch (url.getPath()) {

            case "/check": return Response.newFixedLengthResponse("ok");

            case "/callback" : return callback(session);

            case "/success" : return success(session);

            case "/failed" : return failed(session);

        }

        return super.handle(session);
    }

    private Response success(IHTTPSession session) {

        String[] msg = new String[] {

            "# NTTBot 添加账号","",

            "Twitter 账号 : " + URLUtil.decode(session.getParms().get("user")) + " 添加成功！","",

            "请返回Bot (◦˙▽˙◦)"

        };

        String page = Markdown.parsePage("成功 \\(≧▽≦)/", ArrayUtil.join(msg, "\n"));

        return Response.newFixedLengthResponse(page);


    }

    private Response failed(IHTTPSession session) {

        String[] msg = new String[] {

            "# NTTBot 添加账号","",

            "失败了 T^T 乃可以返回Bot重试",
            "是不是刷新了这个界面？",

        };

        String page = Markdown.parsePage("失败了呢.. T^T ", ArrayUtil.join(msg, "\n"));

        return Response.newFixedLengthResponse(page);

    }



    private Response callback(IHTTPSession session) {

        String oauth_token = session.getParms().get("oauth_token");
        String oauth_verifier = session.getParms().get("oauth_verifier");

        TwiAccount account = manager.auth(oauth_token, oauth_verifier);

        Response resp = Response.newFixedLengthResponse(Status.REDIRECT_SEE_OTHER, MIME_PLAINTEXT, "");
        
        if (account == null) {
            
            resp.addHeader("Location", domain + "/failed");

            return resp;

        } else {

            resp.addHeader("Location", domain + "/success?user=" + URLUtil.encode(account.getFormatedName()));

            return resp;

        }

    }


}
