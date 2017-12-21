package tool.word2html;

import java.nio.charset.Charset;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * Hello world!
 *
 */

public class App {
	public static void main(String[] args) {

		try {
			String infilepath = "D:\\tmp\\bpm\\manual.doc";

			// convert doc to html file
			String htmlcontent = FileTools.convert2Html(infilepath);
			String cleanfile = Jsoup.clean(htmlcontent, basicWithImages()

			);
			// Save to file
			String outfilepath = infilepath.substring(0, infilepath.lastIndexOf(".")) + ".html";
			Document doc = Jsoup.parse(cleanfile);
			doc.charset(Charset.forName("UTF-8"));

			FileTools.writeFileContent(outfilepath, doc.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Hello World!");

	}

	public static Whitelist basicWithImages() {
		return new Whitelist().addTags("a", "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "dd",
				"div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img", "li", "ol", "p", "pre", "q",
				"small", "strike", "strong", "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u",
				"ul")

		.addAttributes("a", "href", "title").addAttributes("blockquote", "cite").addAttributes("col", "span", "width")
				.addAttributes("colgroup", "span", "width")
				.addAttributes("img", "align", "alt", "height", "src", "title", "width")
				.addAttributes("ol", "start", "type").addAttributes("q", "cite")
				.addAttributes("table", "summary", "width")
				.addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width")
				.addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope", "width").addAttributes("ul", "type")

		.addProtocols("a", "href", "ftp", "http", "https", "mailto").addProtocols("blockquote", "cite", "http", "https")
				.addProtocols("q", "cite", "http", "https");
	}

}
