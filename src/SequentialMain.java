import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class SequentialMain {
	private static HashMap<String,Integer> article;

	public static void main(String[] args) {
		article = new HashMap<String,Integer>();
		BufferedReader br = null;
		
		try {
			int firstCounter =0;
			int secCounter = 0;
			int thirdCounter = 0;
			int fourthCounter =0;
			int fifthCounter =0;
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("C:\\pagecounts"));
 
			while ((sCurrentLine = br.readLine()) != null) {
//				System.out.println(sCurrentLine);
				String temp = sCurrentLine;
				
				if(FilterIsEN(temp) == true) {
					firstCounter++;
					//System.out.println(sCurrentLine);
					if(FilterIsNotSpecialPage(temp) == true) {
						secCounter++;
						if(FilterIsUppercase(temp) == true) {
							thirdCounter++;
							if(FilterForFileExtension(temp) == true) {
								fourthCounter++;
								if(FilterBoilterplate(temp) == true) {
									fifthCounter++;

									
								}
							}
							
						}
					}
					
				}
			}
			System.out.println(firstCounter);
			System.out.println(secCounter);
			System.out.println(thirdCounter);
			System.out.println(fourthCounter);
			System.out.println(fifthCounter);
			printSortedArticles();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static void printSortedArticles() {
		LinkedHashMap<String, Integer> sortedArticle = sortArticle(article);
		int maxiCounter =0;
		for(String s: sortedArticle.keySet()){
			int numberofAccess = sortedArticle.get(s);
//			System.out.println(s+" "+numberofAccess);
			maxiCounter++;
			if(maxiCounter == sortedArticle.size())
				System.out.println(s+" "+numberofAccess);
			if(maxiCounter == sortedArticle.size()-1)
				System.out.println(s+" "+numberofAccess);
		}
	}

	private static boolean FilterBoilterplate(String line) {
		boolean bool;
		String temp = line.substring(3);
		
		String startFromAccess = "";
		String accessTimes = "";
		String title = "";
		StringBuffer buf = new StringBuffer();
		StringBuffer accessBuf =new StringBuffer();
		for(int i=0;i<temp.length();i++) {
			if(temp.charAt(i) != ' ') {
				buf.append(temp.charAt(i));
			}
			else{
				startFromAccess = temp.substring(i+1);
				break;
			}
				
		}
		
		title = buf.toString();
		if(title.matches("^(404_error/|Main_Page|Hypertext_Transfer_Protocol|Favicon.ico|Search)$")) {
			bool = false;
		}
		else {
			for(int j=0;j<startFromAccess.length();j++) {
				if(startFromAccess.charAt(j) != ' ') {
					accessBuf.append(startFromAccess.charAt(j));
				}
				else
					break;
			}
			
			accessTimes = accessBuf.toString();
			//put title and access times into HashMap
			article.put(title, Integer.valueOf(accessTimes));
			bool = true;
		}
		
		return bool;
	}

	private static boolean FilterForFileExtension(String line) {
		boolean bool;
		String temp = line.substring(3);
	
//		if (buf.toString().matches("(\\.jpg|\\.gif|\\.png|\\.JPG|\\.GIF|\\.PNG|\\.txt|\\.ico)$"))
		if(temp.matches(".*(\\.jpg|\\.gif|\\.png|\\.JPG|\\.GIF|\\.PNG|\\.txt|\\.ico)\\s[0-9]{0,}\\s[0-9]{0,}")) 
			bool = false;
		else
			bool = true;
		
		return bool;
	}

	private static boolean FilterIsUppercase(String line) {
		boolean bool;
		String temp = line.substring(3);
		
		if(Character.isLowerCase(temp.charAt(0)) == true) 
			bool = false;
		else
			bool = true;
		
		return bool;
	}

	private static boolean FilterIsNotSpecialPage(String line) {
		boolean bool;
		String temp = line.substring(3);
		
		if(temp.matches("^(Media:|Special:|Talk:|User:|User_talk:|Project:|Project_talk:|"
				+ "File:|File_talk:|MediaWiki:|MediaWiki_talk:|Template:|Template_talk:|"
				+ "Help:|Help_talk:|Category:|Category_talk:|Portal:|Wikipedia:|Wikipedia_talk:).*")) {
			bool = false;
		}
		else
			bool = true;
		
		return bool;
	}

	private static boolean FilterIsEN(String line) {
		boolean bool;
		String temp = line.substring(0, 3);
		
		if(temp.equals("en "))
			bool = true;
		else
			bool = false;
		
		return bool;
		
	}
	/**
	 * 
	 * 
	 * @param map
	 * @return
	 * 
	 *  List<Map.Entry<K, V>> list =
        new LinkedList<>( map.entrySet() );
    Collections.sort( list, new Comparator<Map.Entry<K, V>>()
    {
        @Override
        public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
        {
            return (o1.getValue()).compareTo( o2.getValue() );
        }
    } );

    Map<K, V> result = new LinkedHashMap<>();
    for (Map.Entry<K, V> entry : list)
    {
        result.put( entry.getKey(), entry.getValue() );
    }
    return result;
	 */
	private static LinkedHashMap<String,Integer> sortArticle(Map<String,Integer> map){
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return (o1.getValue()).compareTo( o2.getValue() );
			}
		});
		LinkedHashMap<String,Integer> result = new LinkedHashMap<String, Integer>();
		for(Map.Entry<String, Integer> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		
		return result;
	}
}

