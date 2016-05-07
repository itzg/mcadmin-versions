package me.itzg.mcadmin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Geoff Bourne
 * @since 0.2
 */
public class GetVersions {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Missing arg: mcadmin_URL");
            System.exit(1);
        }

        extractVersions(args[0], System.out);
    }

    static int extractVersions(String mcadminUrl, PrintStream out) throws IOException {
        final Document page = Jsoup.connect(mcadminUrl)
                .header("User-Agent", "Java")
                .get();

        final Elements jarElems = page.select(".jar-div");

        int found = 0;
        for (Element jarDiv : jarElems) {
            final Elements verElems = jarDiv.select(".description-div .version");
            final Element verElem = verElems.first();
            Entry entry = null;
            if (verElem != null) {
                final String version = verElem.text();
                final String[] versionParts = version.split(" ");
                if (versionParts.length != 2) {
                    System.err.println("Invalid version field "+version+" at "+verElem);
                }
                else {
                    entry = new Entry();
                    entry.type = versionParts[0];
                    entry.version = versionParts[1];
                }
            }

            final Elements linkElems = jarDiv.select(".server-jar a.list-link");
            final Element linkElem = linkElems.first();
            if (linkElem != null && entry != null) {
                entry.url = linkElem.attr("href");

                ++found;
                out.println(entry);
            } else if (entry != null) {
                System.err.println("Found description of jar but no link element: "+jarDiv);
            }
        }

        return found;
    }

    private static class Entry {
        String type;
        String version;
        String url;

        @Override
        public String toString() {
            return String.format("%s::%s::%s", type, version, url);
        }
    }
}
