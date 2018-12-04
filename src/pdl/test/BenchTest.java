package pdl.test;

import org.junit.Before;
import org.junit.Test;
import pdl.wiki.*;

import java.io.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test les URL du fichier wikiurls et les r�partit en 2 fichiers
 * en fonction des extracteurs (dans output, soit html soit wikitext)
 */
public class BenchTest
{
    Extractor extractorwiki;
    Extractor extractorhtml;
    List<String> csvwiki;
    List<String> csvhtml;

    /**
     * Initialise les extracteurs html et wikitext avant de faire les tests
     */
    @Before
    public void setUp() throws Exception
    {
        extractorhtml = new HTMLExtractor();
        extractorwiki = new WikiTextExtractor();
    }

    /*
     *  the challenge is to extract as many relevant tables as possible
     *  and save them into CSV files
     *  from the 300+ Wikipedia URLs given
     *  see below for more details
     **/
    @Test
    public void testBenchExtractors() throws Exception
    {

        String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
        // directory where CSV files are exported (HTML extractor)
        String outputDirHtml = "output" + File.separator + "html" + File.separator;
        assertTrue(new File(outputDirHtml).isDirectory());
        // directory where CSV files are exported (Wikitext extractor)
        String outputDirWikitext = "output" + File.separator + "html" + File.separator;
        assertTrue(new File(outputDirWikitext).isDirectory());

        File file = new File("inputdata" + File.separator + "wikiurls.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String url;
        int nurl = 0;
        while ((url = br.readLine()) != null)
        {
            String wurl = BASE_WIKIPEDIA_URL + url;
            System.out.println("Wikipedia url: " + wurl);
            Url unurl = new Url(wurl);
            String csvFileName = mkCSVFileName(url, 1);
            System.out.println("CSV file name: " + csvFileName);

            if (unurl.getTableCount() > 0)
            {
                csvhtml = extractorhtml.getCSV(unurl);
                csvwiki = extractorwiki.getCSV(unurl);
                writefile("html", url, csvhtml);
                writefile("wikitext", url, csvwiki);
            }

            nurl++;
        }

        br.close();
        assertEquals(nurl, 336);


    }

    /**
     * Cr�e le fichier CSV � partir de l'url
     * @param String url qui est l'url du fichier que l'on veut cr�er
     * @param int n qui est le num�ro du tableau de cette page
     * @return String le chemin du fichier CSV
     */
    private String mkCSVFileName(String url, int n)
    {
        return url.trim() + "-" + n + ".csv";
    }

    /**
     * Ecrit dans les fichiers html et wikitext le lien du fichier CSV
     * @param String method qui d�finit si le fichier doit aller dans html ou wikitext
     * @param String url qui correspond � l'url de la page wikip�dia
     * @param List<String> qui est la liste des liens CSV
     */
    private void writefile(String method, String url, List<String> csvlist) throws IOException
    {
        int i = 1;
        FileOutputStream fos = null;
        for (String uncsv : csvlist)
        {
            File csvfile = new File("output/" + method + "/" + mkCSVFileName(url, 1));
            fos = new FileOutputStream(csvfile.getAbsolutePath());
            fos.write(uncsv.getBytes());
            fos.flush();
            fos.close();
            i++;
        }
    }

}
