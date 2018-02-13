package com.lws.rxjava.demo.net;

import com.lws.rxjava.demo.bean.Translation;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by lws on 2018/2/12.
 */

public interface IGetRequest {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall1();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20China")
    Observable<Translation> getCall2();
}
