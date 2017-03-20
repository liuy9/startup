package com.cicada.startup.common.http;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.config.AppPreferences;
import com.cicada.startup.common.utils.DeviceUtils;

/**
 * BaseURL
 * <p>
 * 创建时间: 16/5/3 上午10:57 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class BaseURL {


    /************************************************** 服务器错误信息定义 *****************************************/
    // TODO:服务器错误信息定义
    /**
     * 服务器网络连接(超时)错误
     */
    public static final String APP_EXCEPTION_HTTP_TIMEOUT = "0";
    /**
     * 服务器连接正常200
     */
    public static final String APP_EXCEPTION_HTTP_200 = "200";
    /**
     * 服务器连接错误404（找不到页面错误）
     */
    public static final String APP_EXCEPTION_HTTP_404 = "404";
    /**
     * 服务器连接错误500（内部服务错误）
     */
    public static final String APP_EXCEPTION_HTTP_500 = "500";
    /**
     * 服务器数据解析错误、以及任何未定义的错误
     */
    public static final String APP_EXCEPTION_HTTP_OTHER = "-100";

    /**
     * 业务成功代码：0000000<br>
     */
    public static final String APP_BUSINESS_SUCCESS = "0000000";
    /**
     * Token失效或错误的Token 登录失效，请重新登录-0200001<br>
     * <br>
     * <a
     * href="http://wiki.thinkjoy.local/pages/viewpage.action?pageId=25264313"
     * >异常代码参考Wiki资料</a><br>
     */
    public static final String APP_BUSINESS_LOGIN_AGAIN = "0200001";
    /**
     * 账号已存在
     */
    public static final String APP_BUSINESS_ACCOUNT_EXIST = "0100001";
    /**
     * 用户未注册
     */
    public static final String USER_UN_REGISTER_ERROR_CODE = "0100009";
    /**
     * 重复审核敏感词
     */
    public static final String APP_BUSINESS_MESSAGE_CHECKED = "0150003";

    /**
     * 重复处理允许或拒绝
     */
    public static final String APP_BUSINESS_MESSAGE_RE_CHECKED = "1000003";

    /**
     * 您已申请或被邀请加入该家庭，请在系统消息中查看最新状态
     */
    public static final String APP_BUSINESS_ALREADY_INVITE_APPLY_ERROR_CODE = "0140005";
    /**
     * 用户没有开通摄像头功能
     */
    public static final String APP_BUSINESS_IPC_NO_PERMISSION = "0110006";

    /**
     * 成长书库无效卡
     */
    public static final String APP_BUSINESS_BOOK_HOUSE_INVALID_CARD = "1000015";


    /**
     * 获取当前环境－服务器域名、地址
     */
    public static String getBaseURL() {
        return AppContext.appEnvConfig.getApiUrl();
    }


    /**
     * 发现页面
     *
     * @return
     */
    public static String getDiscoverUrl() {
        String path = "/discover/home";
        return AppContext.appEnvConfig.getWebUrl() + path;
    }

    /**
     * 看文章
     * @return
     */
    public static String getFreedomActice(){
        return AppContext.appEnvConfig.getWebUrl()+"/activity/list";
    }



    /**
     * 热门视频
     *
     * @return
     */
    public static String getLiveList() {
        String path = "/live/list";
        return AppContext.appEnvConfig.getWebUrl() + path;
    }

    /**
     * 积分商城url
     *
     * @return
     */
    public static String getCreditStoreUrl() {
        String url = AppContext.appEnvConfig.getApiUrl() + "credit/credit/dbUserAutoLogin?token=" + AppPreferences.getInstance().getLoginToken() + "&redirect=";
        return url;
    }

    /**
     * 会员url
     *
     * @return
     */
    public static String getVipUrl() {
        String path = "/vipCenter/home?packageType=101&privilegeType=1";
        return AppContext.appEnvConfig.getWebUrl() + path;
    }

    public static String getArticleDetailUrl(int topicId) {
        String path = "/topic/detail?articleId="
                + topicId;
        return AppContext.appEnvConfig.getWebUrl() + path;
    }

    public static String getActivityDetailUrl(int activityId) {
        String path = "/activity/detail?activityId=" + activityId;
        return AppContext.appEnvConfig.getWebUrl() + path;
    }

    /**
     * 每日育儿协议
     *
     * @return
     */
    public static String getAppProtocal() {
        String url = AppContext.appEnvConfig.getWebUrl() + "/static/service-info.html";
        return url;
    }


    public static String getBabyBookUrl() {
        return AppContext.appEnvConfig.getWebUrl() + "/babyDrip/book/detail";
    }

    public static String getUserCacheKey(String tag) {
        return AppPreferences.getInstance().getUserId() + "_" + tag;
    }

    /**
     * @return 获取有赞商城地址
     */
    public static String getYouZanUrl() {
        return "https://h5.koudaitong.com/v2/showcase/homepage?alias=vhwpre2u";
    }

    public static String getVideoDetail(int liveId) {
        String path = "/live/detail?liveId=" + liveId+"&version="+ DeviceUtils.getVersionName(AppContext.getContext());
        return AppContext.appEnvConfig.getWebUrl() + path;
    }

}
