package org.musicbrainz.webservice.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.musicbrainz.webservice.AuthorizationException;
import org.musicbrainz.webservice.DefaultWebService;
import org.musicbrainz.webservice.RequestException;
import org.musicbrainz.webservice.ResourceNotFoundException;
import org.musicbrainz.webservice.WebServiceException;
import org.musicbrainz.wsxml.MbXMLException;
import org.musicbrainz.wsxml.element.Metadata;

/**
 * A simple http client using Apache Commons HttpClient.
 * 
 * @author Patrick Ruhkopf
 */
public class HttpClientWebService extends DefaultWebService 
{
	/**
	 * A logger
	 */
	private Log log = LogFactory.getLog(HttpClientWebService.class);
	
	/**
	 * A {@link HttpClient} instance
	 */
	private HttpClient httpClient;
	
	/**
	 * Default constructor creates a httpClient with default properties. 
	 */
	public HttpClientWebService() 
	{
		this.httpClient = new HttpClient();	
	}
	
	/**
	 * Use this constructor to inject a configured {@link HttpClient}.
	 * 
	 * @param httpClient A configured {@link HttpClient}.
	 */
	public HttpClientWebService(HttpClient httpClient) 
	{
		this.httpClient = httpClient;
	}

	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.DefaultWebService#doGet(java.lang.String)
	 */
	@Override
	protected Metadata doGet(String url) throws WebServiceException, MbXMLException
	{		
		HttpMethod method = new GetMethod(url);
		
		// retry 3 times, do not retry if we got a response, because we
		// may only query the web service once a second
	    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
	    		new DefaultHttpMethodRetryHandler(3, false));

	    // allow automatic authentication
	    method.setDoAuthentication(true);
	    
	    try 
	    {
	      // execute the method
	      int statusCode = this.httpClient.executeMethod(method);

	      if (log.isDebugEnabled())
	      {
	    	  log.debug(method.getResponseBodyAsString());
	      }
	      
	      switch (statusCode)
	      {
	      	case HttpStatus.SC_OK:
	      		return getParser().parse(method.getResponseBodyAsStream());
	    	 
	      	case HttpStatus.SC_NOT_FOUND:
	      		throw new ResourceNotFoundException(method.getResponseBodyAsString());
	      		
	      	case HttpStatus.SC_BAD_REQUEST:
	      		throw new RequestException(method.getResponseBodyAsString());
	      		
	      	case HttpStatus.SC_FORBIDDEN:
	      		throw new AuthorizationException(method.getResponseBodyAsString());
	      		
	      	case HttpStatus.SC_UNAUTHORIZED:
	      		throw new AuthorizationException(method.getResponseBodyAsString());
	      		
	      	default:
	      		String em = "web service returned unknown status '" + statusCode + "', response was: " + method.getResponseBodyAsString();
	      		log.error(em);
	      		throw new WebServiceException(em);
	      }
	    } 
	    catch (HttpException e) 
	    {
	    	log.error("Fatal protocol violation: " + e.getMessage());
	    	throw new WebServiceException(e.getMessage(), e);
	    } 
	    catch (IOException e) {
	    	log.error("Fatal transport error: " + e.getMessage());
	    	throw new WebServiceException(e.getMessage(), e);
	    }
	    finally 
	    {
	      // Release the connection.
	      method.releaseConnection();
	    }
	 }

	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.DefaultWebService#doGet(java.lang.String, java.io.InputStream)
	 */
	@Override
	protected void doPost(String url, InputStream data) throws WebServiceException 
	{
		PostMethod method = new PostMethod(url);

		// do not retry
	    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
	    		new DefaultHttpMethodRetryHandler(0, false));

	    // allow automatic authentication
	    method.setDoAuthentication(true);
	    
	    // specify the input stream
	    method.setRequestEntity(new InputStreamRequestEntity(data));
	    
	    try 
	    {
	      // Execute the method.
	      int statusCode = this.httpClient.executeMethod(method);

	      switch (statusCode)
	      {
	      	case HttpStatus.SC_OK:
	      		return;
	    	 
	      	case HttpStatus.SC_NOT_FOUND:
	      		throw new ResourceNotFoundException(method.getResponseBodyAsString());
	      		
	      	case HttpStatus.SC_BAD_REQUEST:
	      		throw new RequestException(method.getResponseBodyAsString());
	      		
	      	case HttpStatus.SC_FORBIDDEN:
	      		throw new AuthorizationException(method.getResponseBodyAsString());
	      		
	      	case HttpStatus.SC_UNAUTHORIZED:
	      		throw new AuthorizationException(method.getResponseBodyAsString());
	      		
	      	default:
	      		String em = "web service returned unknown status '" + statusCode + "', response was: " + method.getResponseBodyAsString();
	      		log.error(em);
	      		throw new WebServiceException(em);
	      }
	    } 
	    catch (HttpException e) 
	    {
	    	log.error("Fatal protocol violation: " + e.getMessage());
	    	throw new WebServiceException(e.getMessage(), e);
	    } 
	    catch (IOException e) {
	    	log.error("Fatal transport error: " + e.getMessage());
	    	throw new WebServiceException(e.getMessage(), e);
	    } 
	    finally 
	    {
	      // Release the connection.
	      method.releaseConnection();
	    }
	}
}
