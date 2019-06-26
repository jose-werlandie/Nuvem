package nuvem_de_bolso.client;

    
import javax.swing.Icon;

///MODEL FOR CUSTOM ROW IN A JLIST
    public class ImgsNText
     {
        //FIELDS
        private String name;
        private Icon img;

        //CONSTRUCTOR
        public ImgsNText(String text,Icon icon)
        {
            this.name = text;
            this.img = icon;
        }

        //GETTERS AND SET

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Icon getImg() {
        return img;
    }

    public void setImg(Icon img) {
        this.img = img;
    }

 }