package com.dshare.sitemapgenerator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class AlternateNewsSitemapUrl extends WebSitemapUrl{
	
	private List<AlternateNewsSitemapUrl> alternates;
	private String lang;
	
	public static class Options extends AbstractSitemapUrlOptions<AlternateNewsSitemapUrl, Options> {
		private List<AlternateNewsSitemapUrl> alternates;
		private String lang;
	
		/** Specifies an URL and publication date (which is mandatory for Google News) */
		public Options(String url, List<AlternateNewsSitemapUrl> alternates) throws MalformedURLException {
			this(new URL(url), alternates);
		}
		
		/** Specifies an URL and publication date (which is mandatory for Google News) */
		public Options(URL url, List<AlternateNewsSitemapUrl> alternates) {
			super(url, AlternateNewsSitemapUrl.class);
			this.alternates = alternates;
		}
		
		/** Specifies an URL and publication date (which is mandatory for Google News) */
		public Options(URL url, String lang) {
			super(url, AlternateNewsSitemapUrl.class);
			this.lang = lang;
		}
		
		
	}
	
	/** Specifies an URL and publication date (which is mandatory for Google News) */
	public AlternateNewsSitemapUrl(URL url, List<AlternateNewsSitemapUrl> alternates) {
		this(new Options(url, alternates));
	}
	
	/** Specifies an URL and publication date (which is mandatory for Google News) */
	public AlternateNewsSitemapUrl(URL url, String lang) {
		this(new Options(url, lang));
	}
	

	/** Configures an URL with options */
	public AlternateNewsSitemapUrl(Options options) {
		super(options);
		alternates = options.alternates;
		lang = options.lang;
	}


	public List<AlternateNewsSitemapUrl> getAlternates() {
		return alternates;
	}


	public String getLang() {
		return lang;
	}
	
	
}
