package com.cicada.startup.common.http.upload;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.R;
import com.cicada.startup.common.config.AppPreferences;
import com.cicada.startup.common.http.BaseURL;
import com.cicada.startup.common.http.domain.UploadResponse;
import com.cicada.startup.common.http.domain.UploadResult;
import com.cicada.startup.common.http.retrofit.DefaultSubscriber;
import com.cicada.startup.common.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 上传文件
 * <p>
 * 创建时间: 16/7/14 下午4:52 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class UploadFileHelper {

    public static final int UPLOAD_SUCCESS_CODE = 200;
    private static Retrofit instance;


    private static <T> T createService(Class<T> clazz) {

        if (null == instance) {
            synchronized (UploadFileHelper.class) {
                if (null == instance) {

                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                    OkHttpClient client = new OkHttpClient.Builder()
//                            .addNetworkInterceptor(new StethoInterceptor())
                            .addInterceptor(new TokenInterceptor())
                            .addInterceptor(logging)
                            .connectTimeout(60 * 5, TimeUnit.SECONDS)
                            .readTimeout(60 * 5, TimeUnit.SECONDS)
                            .writeTimeout(60 * 5, TimeUnit.SECONDS)
                            .build();
                    instance = new Retrofit.Builder()
                            .baseUrl(BaseURL.getBaseURL())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .build();
                }
            }
        }

        return instance.create(clazz);
    }


    private static class TokenInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder newBuilder = chain.request().newBuilder();
            HttpUrl url = chain.request().url();
            newBuilder.url(url + "?token=" + AppPreferences.getInstance().getLoginToken());
            return chain.proceed(newBuilder.build());
        }
    }

    public static void uploadFiles(final List<String> files, final MultiUploadListener listener) {

        Observable<UploadResponse<List<UploadResult>>> uploadObservable = getUploadObservable(files);
        uploadObservable
                .subscribe(new DefaultSubscriber<UploadResponse<List<UploadResult>>>() {
                    @Override
                    public void onSuccess(UploadResponse<List<UploadResult>> result) {
                        if (UPLOAD_SUCCESS_CODE == result.getCode()) {
                            List<UploadResult> data = result.getData();
                            listener.onSuccess(data);
                            FileUtils.deleteUploadTempPic(files);
                        } else {
                            onFailure(BaseURL.APP_EXCEPTION_HTTP_500,
                                    AppContext.getContext().getString(R.string.app_exception_server));
                        }
                    }

                    @Override
                    public void onFailure(String errorCode, String errorMessage) {
                        listener.onFailure(errorCode, errorMessage);
                        FileUtils.deleteUploadTempPic(files);
                    }
                });


    }

    public static Observable<UploadResponse<List<UploadResult>>> getUploadObservable(List<String> files) {
        Map<String, RequestBody> params = new HashMap<>();

        for (String path : files) {
            File file = new File(path);
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("*/*"), file);
            params.put("files\"; filename=\"" + file.getName(), photoRequestBody);
        }
        return createService(UploadFileApi.class)
                .uploadFiles(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static void uploadImage(final String filePath, final UploadListener listener) {
        File file = new File(filePath);
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), photoRequestBody);
        createService(UploadFileApi.class)
                .uploadFile(part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<UploadResponse<UploadResult>>() {
                    @Override
                    public void onSuccess(UploadResponse<UploadResult> result) {
                        if (UPLOAD_SUCCESS_CODE == result.getCode()) {
                            UploadResult data = result.getData();
                            listener.onSuccess(data);
                            FileUtils.deleteUploadTempPic(filePath);
                        } else {
                            onFailure(BaseURL.APP_EXCEPTION_HTTP_500,
                                    AppContext.getContext().getString(R.string.app_exception_server));
                        }
                    }

                    @Override
                    public void onFailure(String errorCode, String errorMessage) {
                        listener.onFailure(errorCode, errorMessage);
                        FileUtils.deleteUploadTempPic(filePath);
                    }
                });
    }


    public interface UploadListener {
        void onSuccess(UploadResult result);

        void onFailure(String errorCode, String errorMessage);
    }

    public interface MultiUploadListener {
        void onSuccess(List<UploadResult> result);

        void onFailure(String errorCode, String errorMessage);
    }
}
