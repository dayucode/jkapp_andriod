/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package zblibrary.demo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zblibrary.demo.application.DemoApplication;
import zblibrary.demo.model.UserBean;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.manager.HttpManager;
import zuo.biao.library.model.Parameter;
import zuo.biao.library.util.MD5Util;
import zuo.biao.library.util.SettingUtil;
import zuo.biao.library.util.StringUtil;

/**
 * HTTP请求工具类
 *
 * @author Lemon
 * @use 添加请求方法xxxMethod >> HttpRequest.xxxMethod(...)
 * @must 所有请求的url、请求方法(GET, POST等)、请求参数(key-value方式，必要key一定要加，没提供的key不要加，value要符合指定范围)
 * 都要符合后端给的接口文档
 */
public class HttpRequest {
    private static final String TAG = "HttpRequest";
    /**
     * 基础URL，这里服务器设置可切换
     */
    public static final String URL_BASE = SettingUtil.getCurrentServerAddress();
    public static final String PAGE_NUM = "pageNum";
    public static final String RANGE = "range";
    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String CURRENT_USER_ID = "currentUserId";
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";

    public static void register(final Map<String,Object> request,
                                final int requestCode, final OnHttpResponseListener listener) {
        HttpManager.getInstance().post(request, URL_BASE + "/user/register", requestCode, listener);
    }
    public static void verifyPhone(final String account,
                                   final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("account", account);
        HttpManager.getInstance().post(request, URL_BASE + "/user/verifyAccount", requestCode, listener);
    }
    public static void login(final String phone, final String password,
                             final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("phone", phone);
        request.put("password",password);
        HttpManager.getInstance().post(request, URL_BASE + "/user/login", requestCode, listener);
    }
    public static void getUserInfo(String userId, final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("id", userId);
        HttpManager.getInstance().get(request, URL_BASE + "/user/getUserInfo", requestCode, listener);
    }
    public static void saveHeartRate(final Map<String,Object> request,
                                     final int requestCode, final OnHttpResponseListener listener) {
        HttpManager.getInstance().post(request, URL_BASE + "/heart_rate/add", requestCode, listener);
    }
    public static void lastTimeHeartRate(String userId, final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        HttpManager.getInstance().get(request, URL_BASE + "/heart_rate/lastTimeTest", requestCode, listener);
    }
    public static void saveBloodPressure(final Map<String,Object> request,
                                     final int requestCode, final OnHttpResponseListener listener) {
        HttpManager.getInstance().post(request, URL_BASE + "/blood_pressure/add", requestCode, listener);
    }
    public static void lastTimeBloodPressure(String userId, final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        HttpManager.getInstance().get(request, URL_BASE + "/blood_pressure/lastTimeTest", requestCode, listener);
    }
    public static void saveEyeTest(final Map<String,Object> request,
                                     final int requestCode, final OnHttpResponseListener listener) {
        HttpManager.getInstance().post(request, URL_BASE + "/eye_test/add", requestCode, listener);
    }
    public static void lastTimeEyeTest(String userId, final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        HttpManager.getInstance().get(request, URL_BASE + "/eye_test/lastTimeTest", requestCode, listener);
    }
    public static void lastThreeMonthHeartRate(String userId, final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        HttpManager.getInstance().get(request, URL_BASE + "/heart_rate/lastThreeMonth", requestCode, listener);
    }
    public static void lastThreeMonthBloodPressure(String userId, final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        HttpManager.getInstance().get(request, URL_BASE + "/blood_pressure/lastThreeMonth", requestCode, listener);
    }
    public static void lastThreeMonthEyeTest(String userId, final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        HttpManager.getInstance().get(request, URL_BASE + "/eye_test/lastThreeMonth", requestCode, listener);
    }
    public static void lastThreeMonthSugar(String userId, final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        HttpManager.getInstance().get(request, URL_BASE + "/sugar/lastThreeMonth", requestCode, listener);
    }
    public static void lastThreeMonthObesity(String userId, final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        HttpManager.getInstance().get(request, URL_BASE + "/obesity/lastThreeMonth", requestCode, listener);
    }
    public static void getTweetsListByType(int type,final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("type", type);
        HttpManager.getInstance().get(request, URL_BASE + "/tweets/getTweetsListByType", requestCode, listener);
    }
    public static void getTweetsById(String id,final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("id", id);
        HttpManager.getInstance().get(request, URL_BASE + "/tweets/getTweetsById", requestCode, listener);
    }
    public static void saveSugar(final Map<String,Object> request,
                                         final int requestCode, final OnHttpResponseListener listener) {
        HttpManager.getInstance().post(request, URL_BASE + "/sugar/add", requestCode, listener);
    }
    public static void lastTimeSugar(String userId, final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        HttpManager.getInstance().get(request, URL_BASE + "/sugar/lastTimeTest", requestCode, listener);
    }

    public static void saveObesity(final Map<String,Object> request,
                                 final int requestCode, final OnHttpResponseListener listener) {
        HttpManager.getInstance().post(request, URL_BASE + "/obesity/add", requestCode, listener);
    }
    public static void lastTimeObesity(String userId, final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        HttpManager.getInstance().get(request, URL_BASE + "/obesity/lastTimeTest", requestCode, listener);
    }
    public static void modifyUser(final Map<String,Object> request,
                                     final int requestCode, final OnHttpResponseListener listener) {
        HttpManager.getInstance().post(request, URL_BASE + "/user/modify", requestCode, listener);
    }
    public static void getUserReport(String id,final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", id);
        HttpManager.getInstance().get(request, URL_BASE + "/user/getUserReport", requestCode, listener);
    }

    public static void deleteTz(final String userId,
                                   final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        HttpManager.getInstance().post(request, URL_BASE + "/obesity/del", requestCode, listener);
    }
    public static void deleteXy(final String userId,
                                final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        HttpManager.getInstance().post(request, URL_BASE + "/blood_pressure/del", requestCode, listener);
    }
    public static void deleteSL(final String userId,
                                final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        HttpManager.getInstance().post(request, URL_BASE + "/eye_test/del", requestCode, listener);
    }
    public static void deleteXL(final String userId,
                                final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        HttpManager.getInstance().post(request, URL_BASE + "/heart_rate/del", requestCode, listener);
    }
    public static void deleteSg(final String userId,
                                final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        HttpManager.getInstance().post(request, URL_BASE + "/sugar/del", requestCode, listener);
    }

    /**
     * 翻译，根据有道翻译API文档请求
     * http://fanyi.youdao.com/openapi?path=data-mode
     * <br > 本Demo中只有这个是真正可用，其它需要自己根据接口文档新增或修改
     *
     * @param word
     * @param requestCode
     * @param listener
     */
    public static void translate(String word, final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("q", word);
        request.put("keyfrom", "ZBLibrary");
        request.put("key", 1430082675);
        request.put("type", "data");
        request.put("doctype", "json");
        request.put("version", "1.1");
        HttpManager.getInstance().get(request, "http://fanyi.youdao.com/openapi.do", requestCode, listener);
    }
    public static final int USER_LIST_RANGE_ALL = 0;
    public static final int USER_LIST_RANGE_RECOMMEND = 1;

    /**
     * 获取用户列表
     *
     * @param range
     * @param pageNum
     * @param requestCode
     * @param listener
     */
    public static void getUserList(int range, int pageNum, final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put(CURRENT_USER_ID, DemoApplication.getInstance().getCurrentUserId());
        request.put(RANGE, range);
        request.put(PAGE_NUM, pageNum);

        HttpManager.getInstance().get(request, URL_BASE + "/user/list", requestCode, listener);
    }
    /**
     * 添加请求参数，value为空时不添加，最快在 19.0 删除
     *
     * @param list
     * @param key
     * @param value
     */
    @Deprecated
    public static void addExistParameter(List<Parameter> list, String key, Object value) {
        if (list == null) {
            list = new ArrayList<Parameter>();
        }
        if (StringUtil.isNotEmpty(key, true) && StringUtil.isNotEmpty(value, true)) {
            list.add(new Parameter(key, value));
        }
    }


}