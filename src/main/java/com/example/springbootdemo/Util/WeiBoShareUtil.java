package com.example.springbootdemo.Util;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootdemo.bean.UserModel;
import com.example.springbootdemo.service.WeiBoService;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeiBoShareUtil {

    @Autowired
    private WeiBoService weiBoService;

        private Logger logger = LoggerFactory.getLogger(com.example.springbootdemo.Util.WeiBoShareUtil.class);
        //
        private static final class GetInstance {
            static final com.example.springbootdemo.Util.WeiBoShareUtil INSTANCE = new com.example.springbootdemo.Util.WeiBoShareUtil();
            // 抖音字体编码
            static final JSONObject mapCode2Name = JSONObject.parseObject("{\"0xe602\":\"num_\",\"0xe605\":\"num_3\",\"0xe606\":\"num_4\",\"0xe603\":\"num_1\",\"0xe604\":\"num_2\",\"0xe618\":\"num_\",\"0xe619\":\"num_4\",\"0xe60a\":\"num_8\",\"0xe60b\":\"num_9\",\"0xe60e\":\"num_\",\"0xe60f\":\"num_5\",\"0xe60c\":\"num_4\",\"0xe60d\":\"num_1\",\"0xe612\":\"num_6\",\"0xe613\":\"num_8\",\"0xe610\":\"num_3\",\"0xe611\":\"num_2\",\"0xe616\":\"num_1\",\"0xe617\":\"num_3\",\"0xe614\":\"num_9\",\"0xe615\":\"num_7\",\"0xe609\":\"num_7\",\"0xe607\":\"num_5\",\"0xe608\":\"num_6\",\"0xe61b\":\"num_5\",\"0xe61c\":\"num_8\",\"0xe61a\":\"num_2\",\"0xe61f\":\"num_6\",\"0xe61d\":\"num_9\",\"0xe61e\":\"num_7\"}");

            // 对应微博字体编码的数字
            static final JSONObject mapCode2Font = JSONObject.parseObject("{\"num_9\":8,\"num_5\":5,\"num_6\":6,\"num_\":1,\"num_7\":9,\"num_8\":7,\"num_1\":0,\"num_2\":3,\"num_3\":2,\"num_4\":4}");
        }

        public static com.example.springbootdemo.Util.WeiBoShareUtil instance() {
            return com.example.springbootdemo.Util.WeiBoShareUtil.GetInstance.INSTANCE;
        }
        /**
         * 根据分享链接爬取用户相关信息
         * @param shareUrl 微博之夜链接
         * @return*/
        public JSONObject getWeiBoInfo(String shareUrl ) {


            String shareInt = "https://huodong.weibo.com/netchina2019/people";
            shareUrl = new TestJsoup().ip(shareInt);
            //shareUrl = getHTMLSource("https://huodong.weibo.com/netchina2019/people");

            //       https://www.iesdouyin.com/share/user/
            //		System.out.println("html -> "+shareUrl);
            // 对微博自定义的字体编码做处理
            shareUrl = shareUrl.replaceAll("&#", "hzsd");
            //		System.out.println("最后处理 -> "+html);

            JSONObject data = new JSONObject();
            try {
                Document doc = Jsoup.parse(shareUrl);
                List<UserModel> models = new ArrayList<UserModel>();

                Elements elements =  doc.select("div[class=con_list_content]").select("ul").select("li");

                int i = 1;
                for (Element ele:elements){

                    String name = ele.select("[class=txt]").get(0).text();

                    String num =null;
                    if (i<4){
                         num = ele.select("[class=img_mark mark"+i+"]").select("em").text();
                    }else {
                         num = ele.select("[class=img_mark]").select("em").text();
                    }


                    String votes = ele.select("[class=num]").get(0).text();

                    UserModel userModel = new UserModel();
                    userModel.setNum(Integer.valueOf(num));
                    userModel.setName(name);
                    userModel.setVotes(votes);
                    models.add(userModel);
                   data.put("Wb",models);
                    i++;
                }
            } catch (Exception e) {
                logger.error("analyse douyin user info has error.",e);
            }
            return data;
        }

        /**
         * 请求 html 源码
         */
        private String getHTMLSource(String url){
            InputStream openStream = null;
            BufferedReader buf = null;

            try {
                String line = null;
                URL theUrl= new URL(url);

                HttpURLConnection conn = (HttpURLConnection) theUrl.openConnection();

                Map<String, List<String>> map = conn.getHeaderFields();
//			System.out.println("请求头："+map.toString());
                // 遍历所有的响应头字段
                for (String key : map.keySet())
                {
                    //如果发现有重定向了新的地址
                    if ("Location".equals(key))
                    {
                        //获取新地址
                        url = map.get(key).get(0);
                        break;
                    }
                }
                theUrl= new URL(url);
                conn = (HttpURLConnection) theUrl.openConnection();
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("Accept-Charset", "UTF-8");
                conn.setRequestProperty("contentType", "UTF-8");
                conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Accept-Language", Locale.getDefault().toString());

                // 建立实际的连接
                conn.connect();
                buf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                StringBuffer str = new StringBuffer();
                while((line = buf.readLine()) != null){
                    str.append(line);
                }
                return str.toString();
            } catch (MalformedURLException e) {
                logger.error("getHTMLSource has MalformedURLException.",e);
            } catch (IOException e) {
                logger.error("getHTMLSource has IOException.",e);
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                try {
                    if(openStream!=null){
                        openStream.close();
                    }
                    if(buf!=null){
                        buf.close();
                    }
                } catch (IOException e) {
                    logger.error("getHTMLSource close stream IOException.",e);
                }
            }
            return null;

        }

        /**
         *
         * 判断是否包含中文。true：包含
         *
         */
        private boolean isChinese(String str){

            Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
            Matcher m = p.matcher(str);
            if (m.find()) {
                return true;
            }
            return false;

        }

        /**
         *
         * 抖音自定义字体对应的编码
         * 反编译成阿拉伯数字
         *
         */
        private String formatNum(String str) {
            if (isChinese(str)) {
                return "";
            }
            if ( str.length() < 8 || str.indexOf("hzsdxe6") < 0) {
                return str;
            }
            str = "0"+str.substring(4,str.length()-1);

            String resStr = com.example.springbootdemo.Util.WeiBoShareUtil.GetInstance.mapCode2Font.getString(com.example.springbootdemo.Util.WeiBoShareUtil.GetInstance.mapCode2Name.getString(str));
            if (StringUtils.isEmpty(resStr)) {
                return str;
            }
            return resStr;
        }



        public static void main(String[] args) {
                System.out.println("微博信息："+ com.example.springbootdemo.Util.WeiBoShareUtil.instance().getWeiBoInfo("a"));
        }

}
