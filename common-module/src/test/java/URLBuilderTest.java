import com.cicada.startup.common.http.URLBuilder;

import org.junit.Test;

/**
 * TODO 功能描述
 * <p/>
 * 创建时间: 16/10/25 上午11:10 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */

public class URLBuilderTest {

    @Test
    public void test1() {
        String path = "www.baidu.com";

        System.out.println(new URLBuilder(path)
                .addParams("version", "1.5.1")
                .addParams("token", "23424")
                .build()
        );
    }

    @Test
    public void test2() {
        String path = "www.baidu.com?name=1";

//        http://www.daydaybaby.com/freedom/activity/detail?version=1.0.0&activityId=11600
        System.out.println(new URLBuilder(path)
                .addParams("version", "1.5.1")
                .addParams("token", "23424")
                .build()
        );
    }


    @Test
    public void test3() {
        String path = "http://www.daydaybaby.com/freedom/activity/detail?version12132=1.0.0&activityId=11600";

        
        System.out.println(new URLBuilder(path)
                .addParams("version", "1.5.1")
                .addParams("token", "23424")
                .build()
        );
    }
}
