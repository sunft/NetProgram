package com.sunft.net.netty.httpjson.codec;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author Lilinfeng
 * @version 1.0
 * @date 2014Äê3ÔÂ1ÈÕ
 */
public class HttpJsonRequest {

    private FullHttpRequest request;
    private Object body;

    public HttpJsonRequest(FullHttpRequest request, Object body) {
        this.request = request;
        this.body = body;
    }

    /**
     * @return the request
     */
    public final FullHttpRequest getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public final void setRequest(FullHttpRequest request) {
        this.request = request;
    }

    /**
     * @return the object
     */
    public final Object getBody() {
        return body;
    }

    /**
     * @param object the object to set
     */
    public final void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "HttpJsonRequest [request=" + request + ", body =" + body + "]";
    }
}

