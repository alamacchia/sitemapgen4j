package com.dshare.sitemapgenerator;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Builds a sitemap for Google News.  To configure options, use {@link #builder(URL, File)}
 * @author Dan Fabulich
 * @see <a href="http://www.google.com/support/news_pub/bin/answer.py?answer=74288">Creating a News Sitemap</a>
 */
public class AlternateNewsSitemapGenerator extends SitemapGenerator<AlternateNewsSitemapUrl,AlternateNewsSitemapGenerator> {

	/** 1000 URLs max in a Google News sitemap. */
	public static final int MAX_URLS_PER_SITEMAP = 1000;
	
	/** Configures a builder so you can specify sitemap generator options
	 * 
	 * @param baseUrl All URLs in the generated sitemap(s) should appear under this base URL
	 * @param baseDir Sitemap files will be generated in this directory as either "sitemap.xml" or "sitemap1.xml" "sitemap2.xml" and so on.
	 * @return a builder; call .build() on it to make a sitemap generator
	 */
	public static SitemapGeneratorBuilder<AlternateNewsSitemapGenerator> builder(URL baseUrl, File baseDir) {
		SitemapGeneratorBuilder<AlternateNewsSitemapGenerator> builder = 
			new SitemapGeneratorBuilder<AlternateNewsSitemapGenerator>(baseUrl, baseDir, AlternateNewsSitemapGenerator.class);
		return builder;
	}
	
	/** Configures a builder so you can specify sitemap generator options
	 * 
	 * @param baseUrl All URLs in the generated sitemap(s) should appear under this base URL
	 * @param baseDir Sitemap files will be generated in this directory as either "sitemap.xml" or "sitemap1.xml" "sitemap2.xml" and so on.
	 * @return a builder; call .build() on it to make a sitemap generator
	 */
	public static SitemapGeneratorBuilder<AlternateNewsSitemapGenerator> builder(String baseUrl, File baseDir) throws MalformedURLException {
		SitemapGeneratorBuilder<AlternateNewsSitemapGenerator> builder = 
			new SitemapGeneratorBuilder<AlternateNewsSitemapGenerator>(baseUrl, baseDir, AlternateNewsSitemapGenerator.class);
		return builder;
	}
	
	AlternateNewsSitemapGenerator(AbstractSitemapGeneratorOptions<?> options) {
		super(options, new Renderer());
	}

	/** Configures the generator with a base URL and directory to write the sitemap files.
	 * 
	 * @param baseUrl All URLs in the generated sitemap(s) should appear under this base URL
	 * @param baseDir Sitemap files will be generated in this directory as either "sitemap.xml" or "sitemap1.xml" "sitemap2.xml" and so on.
	 * @throws MalformedURLException
	 */
	public AlternateNewsSitemapGenerator(String baseUrl, File baseDir)
			throws MalformedURLException {
		this(new SitemapGeneratorOptions(baseUrl, baseDir));
	}

	/** Configures the generator with a base URL and directory to write the sitemap files.
	 * 
	 * @param baseUrl All URLs in the generated sitemap(s) should appear under this base URL
	 * @param baseDir Sitemap files will be generated in this directory as either "sitemap.xml" or "sitemap1.xml" "sitemap2.xml" and so on.
	 */
	public AlternateNewsSitemapGenerator(URL baseUrl, File baseDir) {
		this(new SitemapGeneratorOptions(baseUrl, baseDir));
	}

	/**Configures the generator with a base URL and a null directory. The object constructed
	 * is not intended to be used to write to files. Rather, it is intended to be used to obtain
	 * XML-formatted strings that represent sitemaps.
	 * 
	 * @param baseUrl All URLs in the generated sitemap(s) should appear under this base URL
	 */
	public AlternateNewsSitemapGenerator(String baseUrl) throws MalformedURLException {
		this(new SitemapGeneratorOptions(new URL(baseUrl)));
	}
	
	/**Configures the generator with a base URL and a null directory. The object constructed
	 * is not intended to be used to write to files. Rather, it is intended to be used to obtain
	 * XML-formatted strings that represent sitemaps.
	 * 
	 * @param baseUrl All URLs in the generated sitemap(s) should appear under this base URL
	 */
	public AlternateNewsSitemapGenerator(URL baseUrl) {
		this(new SitemapGeneratorOptions(baseUrl));
	}
	
	private static class Renderer extends AbstractSitemapUrlRenderer<AlternateNewsSitemapUrl> implements ISitemapUrlRenderer<AlternateNewsSitemapUrl> {

		public Class<AlternateNewsSitemapUrl> getUrlClass() {
			return AlternateNewsSitemapUrl.class;
		}

		public String getXmlNamespaces() {
			return "xmlns:news=\"http://www.google.com/schemas/sitemap-news/0.9\" xmlns:xhtml=\"http://www.w3.org/1999/xhtml\"";
		}

		public void render(AlternateNewsSitemapUrl url, StringBuilder sb, W3CDateFormat dateFormat) {
			StringBuilder tagSb = new StringBuilder();
			List<AlternateNewsSitemapUrl> alternates = url.getAlternates();
			for (AlternateNewsSitemapUrl alternateNewsSitemapUrl : alternates) {
				tagSb.append("    <xhtml:link rel=\"alternate\" href=\""+alternateNewsSitemapUrl.getUrl().toString()+"\" hreflang=\""+alternateNewsSitemapUrl.getLang()+"\" />\n");
			}
			super.render(url, sb, dateFormat, tagSb.toString());
			
		}
		
	}

}
