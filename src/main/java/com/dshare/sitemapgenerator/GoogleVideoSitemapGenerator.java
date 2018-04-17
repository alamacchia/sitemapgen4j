package com.dshare.sitemapgenerator;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Builds a sitemap for Google Video search.  To configure options, use {@link #builder(URL, File)}
 * @author Dan Fabulich
 * @see <a href="http://www.google.com/support/webmasters/bin/answer.py?answer=80472">Creating Video Sitemaps</a>
 */
public class GoogleVideoSitemapGenerator extends SitemapGenerator<GoogleVideoSitemapUrl,GoogleVideoSitemapGenerator> {

	/** Configures a builder so you can specify sitemap generator options
	 * 
	 * @param baseUrl All URLs in the generated sitemap(s) should appear under this base URL
	 * @param baseDir Sitemap files will be generated in this directory as either "sitemap.xml" or "sitemap1.xml" "sitemap2.xml" and so on.
	 * @return a builder; call .build() on it to make a sitemap generator
	 */
	public static SitemapGeneratorBuilder<GoogleVideoSitemapGenerator> builder(URL baseUrl, File baseDir) {
		return new SitemapGeneratorBuilder<GoogleVideoSitemapGenerator>(baseUrl, baseDir, GoogleVideoSitemapGenerator.class);
	}
	
	/** Configures a builder so you can specify sitemap generator options
	 * 
	 * @param baseUrl All URLs in the generated sitemap(s) should appear under this base URL
	 * @param baseDir Sitemap files will be generated in this directory as either "sitemap.xml" or "sitemap1.xml" "sitemap2.xml" and so on.
	 * @return a builder; call .build() on it to make a sitemap generator
	 */
	public static SitemapGeneratorBuilder<GoogleVideoSitemapGenerator> builder(String baseUrl, File baseDir) throws MalformedURLException {
		return new SitemapGeneratorBuilder<GoogleVideoSitemapGenerator>(baseUrl, baseDir, GoogleVideoSitemapGenerator.class);
	}

	GoogleVideoSitemapGenerator(AbstractSitemapGeneratorOptions<?> options) {
		super(options, new Renderer());
	}
	
	/**Configures the generator with a base URL and directory to write the sitemap files.
	 * 
	 * @param baseUrl All URLs in the generated sitemap(s) should appear under this base URL
	 * @param baseDir Sitemap files will be generated in this directory as either "sitemap.xml" or "sitemap1.xml" "sitemap2.xml" and so on.
	 * @throws MalformedURLException
	 */
	public GoogleVideoSitemapGenerator(String baseUrl, File baseDir)
			throws MalformedURLException {
		this(new SitemapGeneratorOptions(baseUrl, baseDir));
	}

	/**Configures the generator with a base URL and directory to write the sitemap files.
	 * 
	 * @param baseUrl All URLs in the generated sitemap(s) should appear under this base URL
	 * @param baseDir Sitemap files will be generated in this directory as either "sitemap.xml" or "sitemap1.xml" "sitemap2.xml" and so on.
	 */
	public GoogleVideoSitemapGenerator(URL baseUrl, File baseDir) {
		this(new SitemapGeneratorOptions(baseUrl, baseDir));
	}
	
	/**Configures the generator with a base URL and a null directory. The object constructed
	 * is not intended to be used to write to files. Rather, it is intended to be used to obtain
	 * XML-formatted strings that represent sitemaps.
	 * 
	 * @param baseUrl All URLs in the generated sitemap(s) should appear under this base URL
	 */
	public GoogleVideoSitemapGenerator(String baseUrl) throws MalformedURLException {
		this(new SitemapGeneratorOptions(new URL(baseUrl)));
	}
	
	/**Configures the generator with a base URL and a null directory. The object constructed
	 * is not intended to be used to write to files. Rather, it is intended to be used to obtain
	 * XML-formatted strings that represent sitemaps.
	 * 
	 * @param baseUrl All URLs in the generated sitemap(s) should appear under this base URL
	 */
	public GoogleVideoSitemapGenerator(URL baseUrl) {
		this(new SitemapGeneratorOptions(baseUrl));
	}

	private static class Renderer extends AbstractSitemapUrlRenderer<GoogleVideoSitemapUrl> implements ISitemapUrlRenderer<GoogleVideoSitemapUrl> {

		public Class<GoogleVideoSitemapUrl> getUrlClass() {
			return GoogleVideoSitemapUrl.class;
		}
		
		public String getXmlNamespaces() {
			return "xmlns:video=\"http://www.google.com/schemas/sitemap-video/1.1\"";
		}

		public void render(GoogleVideoSitemapUrl url, StringBuilder sb, W3CDateFormat dateFormat) {
			StringBuilder tagSb = new StringBuilder();
			if (url.getHasVideoContentLoc()) {
				tagSb.append("    <video:video>\n");
				if (url.getPlayerUrl() != null) {
					renderTag(tagSb, "video", "content_loc", url.getContentUrl());
					tagSb.append("<video:player_loc allow_embed=\"");
					tagSb.append(url.getAllowEmbed());
					tagSb.append("\">");
					tagSb.append(url.getPlayerUrl());
					tagSb.append("</video:player_loc>\n");
				}
				renderTag(tagSb, "video", "thumbnail_loc", url.getThumbnailUrl());
				renderTag(tagSb, "video", "title", url.getTitle());
				renderTag(tagSb, "video", "description", url.getDescription());
				renderTag(tagSb, "video", "rating", url.getRating());
				renderTag(tagSb, "video", "view_count", url.getViewCount());
				if (url.getUploaderInfo()!=null) {
					tagSb.append("<video:uploader info=\"");
					tagSb.append(url.getUploaderInfo());
					tagSb.append("\">");
					tagSb.append(url.getUploader());
					tagSb.append("</video:uploader>\n");
				}else if (url.getUploader()!=null)  {
					renderTag(tagSb, "video", "uploader", url.getCategory());
				}
				if (url.getPublicationDate() != null) {
					renderTag(tagSb, "video", "publication_date", dateFormat.format(url.getPublicationDate()));
				}
				if (url.getTags() != null) {
					for (String tag : url.getTags()) {
						renderTag(tagSb, "video", "tag", tag);
					}
				}
				if (url.getGalleryLoc()!=null && url.getGalleryLocTitle()!=null) {
					tagSb.append("<video:gallery_loc title=\"");
					tagSb.append(url.getGalleryLocTitle());
					tagSb.append("\">");
					tagSb.append(url.getGalleryLoc());
					tagSb.append("</video:gallery_loc>\n");
				}
				renderTag(tagSb, "video", "category", url.getCategory());
				renderTag(tagSb, "video", "family_friendly", url.getFamilyFriendly());
				renderTag(tagSb, "video", "duration", url.getDurationInSeconds());
				renderTag(tagSb, "video", "requires_subscription", "no");
				tagSb.append("    </video:video>\n");
			}
			super.render(url, sb, dateFormat, tagSb.toString());
			
		}
		
	}
	
}
