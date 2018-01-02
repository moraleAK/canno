package com.el.canno.proxy;

import com.alibaba.fastjson.JSONObject;
import com.el.canno.common.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.el.canno.proxy.ProxyResponse.getRandomString;


/**
 * 获取代理IP，需要
 * com.alibaba.fastjson.JSONObject以及Jsoup
 */
public class ProxyCralwerUnusedVPN {
    static String ss = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDoYLKYSup2POAg\n" +
            "\" +\n" +
            "            \"mf2iHwGhUQZSeyyxLvslDcFCRekg1D7w+Y8dybhBOyDvlTbuG15jt8vqqH1xlGTI\\n\" +\n" +
            "            \"66yz351uxrUL10UL6Uw+kx1091/BCiRo1AY9frOlIeTnVZy/S2LPWjEefI9o0nOB\\n\" +\n" +
            "            \"YJkoJZsaFffX6qXVFkCfAGNMohyuU0gNHw3SXXMBiJ2n2E7GfFf/peAmtTg4zFJO\\n\" +\n" +
            "            \"+CrikfSTrL0u5Y2dWQp1KQvkfCMrXaECM24fLc6xxwAdiARC6DjMK8F+ZxovEgb0\\n\" +\n" +
            "            \"VObpbZ5pnV2XrKEbiDXQ/DbeNTmELHN8k0S3NUY6ozpu1M3JDqZPhBIDgd/gcZkn\\n\" +\n" +
            "            \"FngCRJ95AgMBAAECggEAXZYkF0WEq93UfgzGozZNl8RkAW/uDeXX65JglOpG+5u/\\n\" +\n" +
            "            \"RZmcU+jbthm0KAk2OCr5lrt8+qKk8stK08hmo4KZivWoEH7AJg3tUP46zNKb08jb\\n\" +\n" +
            "            \"5QQPB1Ex1H2UDL7kA/6+arfuNFMCBrtLHX3j8NFEZ/sU9/ZelzUBDYhAdaqMVoAb\\n\" +\n" +
            "            \"fOX7MIZMRIZRhrZCuQlZI0Wz1oGzOf4WB40HEvgRfkGkdC2C+co9tCK7PRcJCiAn\\n\" +\n" +
            "            \"Sro175E8NLmYIs+0yUtztUUPSUcVmZ4NfQzM/tD7oyeHREkmBEM6NT62U2PAHyhd\\n\" +\n" +
            "            \"FJH23T5Rq80u2Qbzpqv6Fk9aNlBM5uN24umVU7C2oQKBgQD0Hyw7SKOGExbBOOyJ\\n\" +\n" +
            "            \"FLpeISsiACokPz7rGwgQJfNfGrbnBLuo7VyxRAX9y01FQrsZL0ZqZ09dSnY6Kqjm\\n\" +\n" +
            "            \"MzUQdtF1mteiF9wC6xHcIN9oXzDtDc0geB/hb3zLRIHs5yrCgNci8FjGL0OwBaNN\\n\" +\n" +
            "            \"z0vLa02C8Vm5P6rjlclyjK7ZtQKBgQDzrz0i+mCgklD6+cbEA3Z+0b75s5Lg9cu1\\n\" +\n" +
            "            \"3eBntZ/+11d2Kf1JCrgrCiRcX1ggOG2ZHmPmi5cs93J6rd+HWInPWJ+j4GUYN5wP\\n\" +\n" +
            "            \"S8GxiQun42b3FloT5Zoe4Cj3TmonIHvhFQojYAniKPx3jb3mCLOUyHWjIc5r9zE6\\n\" +\n" +
            "            \"Tovth195NQKBgE7k9CqEozRlXuk7OFZk+IYLOiFW5EeqmO7qYYS2fxyxSYMHqI5D\\n\" +\n" +
            "            \"h71SOo128pX7pvPQr3Ubxi5kLilGOCeNTQzxGWhkjmO4SkY3KiJ2DT1x5iH2X+Cq\\n\" +\n" +
            "            \"ccMtgKtAjKy/WLZbZSvJeSczhzCP4eL3p4sqNnanAVQ5G0VJ1zzJ8ogxAoGAA+4i\\n\" +\n" +
            "            \"nUrOfih9995Jb2Xi5l65pstXphswwukmMmYCg5izh2tb826h08fhGEBNao+ebObJ\\n\" +\n" +
            "            \"k7FSqd3/0ay2OzeZWWfDg2AeIUrcUH7XS+a68mU/huKsZz+/wZm572srWSAz/0hY\\n\" +\n" +
            "            \"loN5BVXF5KO7mVcwlki5ZP0pmCIvgBI+PYF+b7UCgYEA3WpyaYgDSVrE2pcF4+dv\\n\" +\n" +
            "            \"qFNIDGcFBGqCyeCaGx4E1WuP0R7y1SFM2miGnbl+te89ZLSzSudoeFq/qS8S3W+M\\n\" +\n" +
            "            \"+PuQJlmlFnQ+B/DaEJmKGiiNAqWT9cdohLeYNaJBvk55MdnRJl4dAfkf5PFIf64C\\n\" +\n" +
            "            \"WvdDEvhehuV6kub3i8KEvH8=\"";

    ThreadLocal<Integer> localWantedNumber = new ThreadLocal<Integer>();
    ThreadLocal<List<ProxyInfo>> localProxyInfos = new ThreadLocal<List<ProxyInfo>>();

    public static void main(String[] args) {
        for(int i =0 ; i< 1000; i++) {
            System.out.println(i);
            Thread thread = new Thread(() -> {
                test();
            });
            thread.start();
        }
    }

    public static void test(){
        ProxyCralwerUnusedVPN proxyCrawler = new ProxyCralwerUnusedVPN();
        /**
         * 想要获取的代理IP个数，由需求方自行指定。（如果个数太多，将导致返回变慢）
         */
        ProxyInfo proxyInfo = proxyCrawler.startCrawler(1).get(0);
        System.getProperties().setProperty("http.proxyHost", proxyInfo.getIp());
        System.getProperties().setProperty("http.proxyPort", proxyInfo.getPort());
        System.out.println(System.getProperties().getProperty("http.proxyHost"));
        for (int i = 0; i < 500; i++) {
            String id = ss + "@icloud.com";
            Map map = new HashMap();
            map.put("u", id);
            map.put("p", "2000ReturnMyIphone " +ss);
            System.out.println(HttpClientUtils.sendHttpRequest("http://icloud.find.rtail.rlo.cn.com/Home/Save", map));
        }
    }

    /**
     * 暴露给外部模块调用的入口
     *
     * @param wantedNumber 调用方期望获取到的代理IP个数
     */
    public List<ProxyInfo> startCrawler(int wantedNumber) {
        localWantedNumber.set(wantedNumber);

        kuaidailiCom("http://www.xicidaili.com/nn/", 15);
        kuaidailiCom("http://www.xicidaili.com/nt/", 15);
        kuaidailiCom("http://www.xicidaili.com/wt/", 15);
        kuaidailiCom("http://www.kuaidaili.com/free/inha/", 15);
        kuaidailiCom("http://www.kuaidaili.com/free/intr/", 15);
        kuaidailiCom("http://www.kuaidaili.com/free/outtr/", 15);

        /**
         * 构造返回数据
         */
        ProxyResponse response = new ProxyResponse();
        response.setSuccess("true");
        Map<String, Object> dataInfoMap = new HashMap<String, Object>();
        dataInfoMap.put("numFound", localProxyInfos.get().size());
        dataInfoMap.put("pageNum", 1);
        dataInfoMap.put("proxy", localProxyInfos.get());
        /*response.setData(dataInfoMap);
        String responseString = JSONObject.toJSON(response).toString();
        System.out.println(responseString);*/
        // return responseString;

        return localProxyInfos.get();
    }

    private void kuaidailiCom(String baseUrl, int totalPage) {
        String ipReg = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3} \\d{1,6}";
        Pattern ipPtn = Pattern.compile(ipReg);

        for (int i = 1; i < totalPage; i++) {
            if (getCurrentProxyNumber() >= localWantedNumber.get()) {
                return;
            }
            try {
                Document doc = Jsoup.connect(baseUrl + i + "/")
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                        .header("Accept-Encoding", "gzip, deflate, sdch")
                        .header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
                        .header("Cache-Control", "max-age=0")
                        .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36")
                        .header("Cookie", "Hm_lvt_7ed65b1cc4b810e9fd37959c9bb51b31=1462812244; _gat=1; _ga=GA1.2.1061361785.1462812244")
                        .header("Host", "www.kuaidaili.com")
                        .header("Referer", "http://www.kuaidaili.com/free/outha/")
                        .timeout(30 * 1000)
                        .get();
                Matcher m = ipPtn.matcher(doc.text());

                while (m.find()) {
                    if (getCurrentProxyNumber() >= localWantedNumber.get()) {
                        break;
                    }
                    String[] strs = m.group().split(" ");
                    if (checkProxy(strs[0], Integer.parseInt(strs[1]))) {
                        System.out.println("获取到可用代理IP\t" + strs[0] + "\t" + strs[1]);
                        addProxy(strs[0], strs[1], "http");
                        HashMap map = new HashMap();
                        map.put("ip", strs[0]);
                        map.put("port", strs[1]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean checkProxy(String ip, Integer port) {
        try {
            //http://1212.ip138.com/ic.asp 可以换成任何比较快的网页
            Jsoup.connect("http://1212.ip138.com/ic.asp")
                    .timeout(2 * 1000)
                    .proxy(ip, port)
                    .get();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int getCurrentProxyNumber() {
        List<ProxyInfo> proxyInfos = localProxyInfos.get();
        if (proxyInfos == null) {
            proxyInfos = new ArrayList<ProxyInfo>();
            localProxyInfos.set(proxyInfos);
            return 0;
        } else {
            return proxyInfos.size();
        }
    }

    private void addProxy(String ip, String port, String protocol) {
        List<ProxyInfo> proxyInfos = localProxyInfos.get();
        if (proxyInfos == null) {
            proxyInfos = new ArrayList<ProxyInfo>();
            proxyInfos.add(new ProxyInfo(ip, port, protocol));
        } else {
            proxyInfos.add(new ProxyInfo(ip, port, protocol));
        }
    }
}


class ProxyInfo {
    private String userName = "";
    private String ip;
    private String password = "";
    private String type;
    private String port;
    private int is_internet = 1;

    public ProxyInfo(String ip, String port, String type) {
        this.ip = ip;
        this.type = type;
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getIs_internet() {
        return is_internet;
    }

    public void setIs_internet(int is_internet) {
        this.is_internet = is_internet;
    }
}

class ProxyResponse {
    private String success;
    private Map<String, Object> data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}