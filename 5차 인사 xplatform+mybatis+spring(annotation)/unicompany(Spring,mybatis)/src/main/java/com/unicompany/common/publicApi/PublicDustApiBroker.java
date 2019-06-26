package com.unicompany.common.publicApi;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.tobesoft.xplatform.data.PlatformData;
import com.unicompany.common.mapper.DatasetBeanMapper;

import java.io.BufferedReader;
import com.unicompany.base.to.DustBean;

public class PublicDustApiBroker {

	public List<DustBean> requestDustGrade()throws Exception{
		
		List<DustBean> dustlist=new ArrayList<>();
		
    	StringBuilder urlBuilder = new StringBuilder("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=3ld%2Fwur%2BZnTXOUPsdKqREhYF14o8APnsL%2Fdrpj55jcT04EjVdsJYRfLm29XB%2FrQ9lH1WqUMzy%2FDrBNJshZA3Sw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("sidoName","UTF-8") + "=" + URLEncoder.encode("경남", "UTF-8")); /*시도 이름 (서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)*/
        urlBuilder.append("&" + URLEncoder.encode("searchCondition","UTF-8") + "=" + URLEncoder.encode("HOUR", "UTF-8")); /*요청 데이터기간 (시간 : HOUR, 하루 : DAILY)*/
        urlBuilder.append("&" + URLEncoder.encode("ver","UTF-8") + "=" + URLEncoder.encode("1.3", "UTF-8")); /*버전1.3*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        // XML파싱용 도큐먼트빌드팩토리 생성
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         DocumentBuilder builder = factory.newDocumentBuilder();

         // 도큐먼트빌트팩토리에 응답 문자열을 넣어줌
         Document document = builder.parse(new InputSource(new StringReader(sb.toString())));
         
         // 주소노드 리스트 받기
         NodeList nodelist = document.getElementsByTagName("item");
         for(int i = 0; i < nodelist.getLength() ; i++) {
            DustBean dustBean = new DustBean();
            
            Element item = (Element)nodelist.item(i);
            Element stationName = (Element)item.getElementsByTagName("stationName").item(0);
            Element pmValue = (Element)item.getElementsByTagName("pm10Value").item(0);
            Element pmGrade = (Element)item.getElementsByTagName("pm10Grade1h").item(0);
      
            String stationNameV=null;
            String pmValueV=null;
            String pmGradeV=null;
       
            stationNameV = stationName.getFirstChild().getNodeValue();
            pmValueV = pmValue.getFirstChild().getNodeValue();
            System.out.println("ssss"+stationNameV);
            if(pmGrade.getFirstChild()==null) {
            pmGradeV="수치측정불가";
            System.out.println("mmm"+pmGradeV);
            }
            else {
            	pmGradeV=pmGrade.getFirstChild().getNodeValue();
            	System.out.println("mmm"+pmGradeV);
            }
            dustBean.setStationName(stationNameV);
            dustBean.setPmValue(pmValueV);
            dustBean.setPmGrade(pmGradeV);
            
            dustlist.add(dustBean);
            
         }
         return dustlist;
	}
}
