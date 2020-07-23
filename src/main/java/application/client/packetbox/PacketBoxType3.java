package application.client.packetbox;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PacketBoxType3 extends PacketBox {
    private String[] urls = {"https://www.google.com/", "https://www.naver.com/", "https://www.daum.net/",
            "https://blog.naver.com/nebi25", "https://www.facebook.com/", "https://www.acmicpc.net/",
            "https://www.ex-em.com/"};

    private int randomNumber;

    private URL url;

    private Map<String, String> headerInfo = new HashMap<>();

    public PacketBoxType3(Socket socket) throws Exception {
        super(socket);
        randomNumber = super.createRandomNumber(0, urls.length - 1);
        url = new URL(urls[randomNumber]);
        setHeaderInfo();
    }

    public void setHeaderInfo() throws Exception {
        URLConnection urlCon = url.openConnection();

        this.headerInfo.put("urlCon.getContentType", urlCon.getContentType());
        this.headerInfo.put("urlCon.getContent", urlCon.getContent().toString());
        this.headerInfo.put("urlCon.getContentEncoding", urlCon.getContentEncoding());

        Map<String, List<String>> map = urlCon.getHeaderFields();

        map.forEach((key, value) -> {
            this.headerInfo.put(key, value.toString());
        });
    }

    @Override
    public String toString() {
        return String.format("PacketBoxType3:[%s, url:%s, headerInfo:[%s]]", super.getPublicDataString(), url.toString(), headerInfo.toString());
    }

    public String[] getUrls() {
        return urls;
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    public URL getUrl() {
        return url;
    }

    public Map<String, String> getHeaderInfo() {
        return headerInfo;
    }
}
