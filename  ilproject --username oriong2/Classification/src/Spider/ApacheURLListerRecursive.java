package Spider;
/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ivy.util.FileUtil;
import org.apache.ivy.util.Message;
import org.apache.ivy.util.url.URLHandlerRegistry;

/**
 * Utility class which helps to list urls under a given url. This has been tested with Apache 1.3.33
 * server listing, as the one used at ibiblio, and with Apache 2.0.53 server listing, as the one on
 * mirrors.sunsite.dk.
 */
public class ApacheURLListerRecursive {
	// ~ Static variables/initializers ------------------------------------------
	int initialWide = 0;
	private List<String> urlList = new ArrayList<String>();
	private static final Pattern PATTERN = Pattern.compile(
			"<a[^>]*href=\"([^\"]*)\"[^>]*>(?:<[^>]+>)*?([^<>]+?)(?:<[^>]+>)*?</a>",
			Pattern.CASE_INSENSITIVE);

	// ~ Methods ----------------------------------------------------------------

	/**
	 * Returns a list of sub urls of the given url. The returned list is a list of URL.
	 * 
	 * @param url
	 *            The base URL from which to retrieve the listing.
	 * @return a list of sub urls of the given url.
	 * @throws IOException
	 *             If an error occures retrieving the HTML.
	 */
	public List<String> listAll(String sMainUrl, String sRestUrl, int depth, int wide,int iChildrenWide, String suffix) throws IOException { 
		URL url = new URL(sMainUrl + sRestUrl);
		initialWide = wide;
		retrieveListing(sMainUrl, url, depth, wide,iChildrenWide, suffix);
		return getUrlList();
	}

	/**
	 * Retrieves a {@link List} of {@link URL}s corresponding to the files and/or directories found
	 * at the supplied base URL.
	 * @param sMainUrl 
	 * 
	 * @param url
	 *            The base URL from which to retrieve the listing.
	 * @param includeFiles
	 *            If true include files in the returned list.
	 * @param includeDirectories
	 *            If true include directories in the returned list.
	 * @return A {@link List} of {@link URL}s.
	 * @throws IOException 
	 * @throws IOException
	 *             If an error occures retrieving the HTML.
	 */
	public void retrieveListing(String sMainUrl, URL url, int depth, int wide,int iChildrenWide, String suffix) 
	{
		sMainUrl = normalizeUrl(sMainUrl);
		
		if(depth > 0 ){
			// add trailing slash for relative urls
			/*        if (!url.getPath().endsWith("/") && !url.getPath().endsWith(".html")) {
            url = new URL(url.getProtocol(), url.getHost(), url.getPort(), url.getPath() + "/");
        }*/
			BufferedReader r;
			try {
				r = new BufferedReader(new InputStreamReader(URLHandlerRegistry.getDefault()
						.openStream(url)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return;
			}

			String htmlText;
			try {
				htmlText = FileUtil.readEntirely(r);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return;
			}

			Matcher matcher = PATTERN.matcher(htmlText);

			while (matcher.find() && wide > 0) {
				// get the href text and the displayed text
				String href = matcher.group(1);

				if (!validURL(href)) {
					// the groups were not found (shouldn't happen, really)
					continue;
				}

				HttpURLConnection  oHttpURLConnection = null;
				try{
					URL uhref = new URL(href);
					oHttpURLConnection  = (HttpURLConnection)uhref.openConnection();
					
					if (!href.startsWith("http") 
							|| !href.contains(suffix)
							|| !IsAGoobUrl(oHttpURLConnection)){
						if(!WithMainUrlConcat(sMainUrl, href, suffix)){
							continue;
						}
					}
				}
				catch(Exception ex){
					if(!WithMainUrlConcat(sMainUrl, href, suffix)){
						continue;
					}
					else{
						href = sMainUrl + href;
					}
				}
				finally{
					if(oHttpURLConnection != null)
						oHttpURLConnection.disconnect();
				}
				
				
				// handle complete URL listings
				if (href.startsWith("http:") || href.startsWith("https:")) {
					try {
						if (href.contains(suffix) && !(href.lastIndexOf("http")>1) && !getUrlList().contains(href)) {
							getUrlList().add(href);
							System.out.println(href);
							wide--;
						}
						//if (href.endsWith(".com") || href.endsWith(".es")){
						//}
					} catch (Exception ignore) {
						// incorrect URL, ignore
						continue;
					}
				}
			}
			
			List<String> urlListCopy =  (List<String>) ((ArrayList) getUrlList()).clone();
			for(String sUrl :urlListCopy){
				URL oUrl;
				try {
					oUrl = new URL(sUrl);
					retrieveListing(sMainUrl, oUrl, depth-1,  iChildrenWide, iChildrenWide, suffix);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private boolean validURL(String href) {
		return href != null
		&& !(href.lastIndexOf("http")>1)
		&&  !getUrlList().contains(href) ;
	}

	private Boolean IsAGoobUrl(HttpURLConnection oHttpURLConnection)
	{
		Boolean bGoodUrl = false;	
		
		int iCode;
		try{
			iCode = oHttpURLConnection.getResponseCode();
		}
		catch(Exception ex){
			return false;
		}
		switch (iCode){
			case HttpURLConnection.HTTP_ACCEPTED:
			case HttpURLConnection.HTTP_OK:
				bGoodUrl = true;
				break;
			default:
				bGoodUrl = false;
				break;
				
		}
		
		return bGoodUrl;
	}
	
	private Boolean WithMainUrlConcat(String sMainUrl, String href, String suffix){
		Boolean bResult = false;
		href = sMainUrl + href;
		URL uhref;
		HttpURLConnection oHttpURLConnection = null;

		if(!href.contains(suffix) || !validURL(href)){
			return bResult;
		}
		
		try{
			uhref = new URL(href);
			oHttpURLConnection  = (HttpURLConnection)uhref.openConnection();
			if (IsAGoobUrl(oHttpURLConnection)){
				bResult = true;
			}
			else{
				bResult = false;
			}
		}
		catch(Exception ex){
			bResult = false;
		}
		finally{
			if(oHttpURLConnection != null)
				oHttpURLConnection.disconnect();
		}
		
		
		return bResult;
	}
	
	private String normalizeUrl(String sUrl) {
		StringBuilder sUrlNormalized = new StringBuilder(sUrl);
		while (sUrlNormalized.toString().endsWith("/")){
			sUrlNormalized.deleteCharAt(sUrlNormalized.length()-1);
		}
		sUrl = sUrlNormalized.toString();
		return sUrl;
	}

	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}

	private List<String> getUrlList() {
		return urlList;
	}
}