import java.io.Serializable;
import java.util.*;

public class Article implements Serializable {
    String title;
    String content;
    int uID; //stores the unique ID generated by the coordinator
    int parentID;
    List<Integer> childList;

    public Article(String title, String content, int uID, int parentID) {
        this.title = title;
        this.content = content;
        this.uID = uID;
        this.parentID = parentID;
        childList = new LinkedList<Integer>();
    }

    public Article(String title, String content) {
        this(title, content, 0, -1);
    }

    public Article(String content, int parentID) {
        this(null, content, 0, parentID);
    }

    @Override
    public boolean equals(Object obj1) {
        if(obj1 == null || !(obj1 instanceof Article))
            return false;
        Article obj = (Article) obj1;
        return title.equals(obj.title) && content.equals(obj.content);
    }

    @Override
    public int hashCode() {
        return title.hashCode() + content.hashCode();
    }

    @Override
    public String toString() {
        return title+ "\n" + content + "," + uID + "," + parentID;
    }
}