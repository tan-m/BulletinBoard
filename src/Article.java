public class Article {
    String title;
    String Content;
    int uID; //stores the unique ID generated by the coordinator
    int parentID;

    public Article(String type, String content, int uID, int parentID) {
        this.title = title;
        this.content = content;
        this.uID = uID;
        this.parentID = parentID;
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
