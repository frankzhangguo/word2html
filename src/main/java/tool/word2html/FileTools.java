package tool.word2html;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

public class FileTools {
	public static String ENCODING = "UTF-8";

	public static String getFileContent(String filePath) throws FileNotFoundException {
		if (filePath != null) {
			File file = new File(filePath);
			if (!file.exists()) {
				throw new FileNotFoundException(filePath);
			}

			try {
				return FileUtils.readFileToString(file, ENCODING);
			} catch (IOException e) {
				System.err.println(String.format("Error parsing file: %s", filePath));
			}
		}
		return "";
	}

	public static void writeFileContent(String filePath, String content) throws IOException {
		if (filePath != null) {
			File file = new File(filePath);
			FileUtils.writeStringToFile(file, content, ENCODING);
		}
	}

	public static String convert2Html(String fileName)
			throws TransformerException, IOException, ParserConfigurationException {

		final String imgDir = "images";

		Path outPath = Paths.get(fileName).getParent().resolve(imgDir);

		FileUtils.deleteQuietly(outPath.toFile());

		// Files.deleteIfExists(outPath);
		Files.createDirectory(outPath);

		HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(fileName));
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
		wordToHtmlConverter.setPicturesManager(new PicturesManager() {
			@Override
			public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches,
					float heightInches) {
				return imgDir + "/" + suggestedName;
			}
		});
		wordToHtmlConverter.processDocument(wordDocument);
		// save pictures
		List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();

		if (pics != null) {
			for (int i = 0; i < pics.size(); i++) {
				Picture pic = pics.get(i);
				try {
					pic.writeImageContent(new FileOutputStream(outPath.resolve(pic.suggestFullFileName()).toString()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		Document htmlDocument = wordToHtmlConverter.getDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(out);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, ENCODING);
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		out.close();

		return new String(out.toByteArray());

	}
}
