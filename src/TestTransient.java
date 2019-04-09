import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
* ����JAVA�������л��������л��Լ�transient��ʹ���ܽ�
*/
class Rectangle implements Serializable{
    
    private static final long serialVersionUID = 1710022455003682613L;
    private Integer width;
    private Integer height;
    private transient Integer area;



    public Rectangle (Integer width, Integer height){
        this.width = width;
        this.height = height;
        this.area = width * height;
    }

    public void setArea(){
        this.area = this.width * this.height;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer(40);
        sb.append("width : ");
        sb.append(this.width);
        sb.append("\nheight : ");
        sb.append(this.height);
        sb.append("\narea : ");
        sb.append(this.area);
        return sb.toString();
    }
}
public class TestTransient {
	 public static void main(String args[]) throws Exception {
	        Rectangle rectangle = new Rectangle(3,4);
	        System.out.println("1.ԭʼ����\n"+rectangle);
	        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("rectangle"));
	        // ����д�����
	        o.writeObject(rectangle);
	        o.close();

	        // ������ȡ����
	        ObjectInputStream in = new ObjectInputStream(new FileInputStream("rectangle"));
	        Rectangle rectangle1 = (Rectangle)in.readObject();
	        System.out.println("2.�����л���Ķ���\n"+rectangle1);
	        rectangle1.setArea();
	        System.out.println("3.�ָ���ԭʼ����\n"+rectangle1);
	        in.close();
	    }
}
