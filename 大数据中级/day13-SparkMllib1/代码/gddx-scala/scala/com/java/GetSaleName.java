package com.java;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Hive UDF 案例
public class GetSaleName extends UDF {

	public String evaluate (String url) 
	{
		String str = null;
		Pattern p = Pattern.compile("sale/[a-zA-Z0-9]+");
		Matcher m = p.matcher(url);
		if (m.find()) {
			str = m.group(0).toLowerCase().split("/")[1];
        }
		return str;
	}

	//http://cms.yhd.com/sale/IhSwTYNxnzS?tc=ad.0.0.15116-32638141.1&tp=1.1.708.0.3.LEHaQW1-10-35dOM&ti=4FAK
	public static void main(String[] args) {
		String url = "http://cms.yhd.com/sale/IhSwTYNxnzS?tc=ad.0.0.15116-32638141.1&tp=1.1.708.0.3.LEHaQW1-10-35dOM&ti=4FAK" ;
		GetSaleName gs = new GetSaleName();
		System.out.println(gs.evaluate(url)) ;
		
	}

}
