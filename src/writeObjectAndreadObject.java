import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class writeObjectAndreadObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String... strings) throws Exception {  
        File file = new File("out.ser");  
  
        ObjectOutputStream oos = new ObjectOutputStream(  
            new FileOutputStream(file));  
        SessionDTO dto = new SessionDTO(1);  
        System.out.println("data : " + dto.getData()  
        + " activation time : " + dto.getActivationTime()); 
        oos.writeObject(dto);
        oos.close();
  
        ObjectInputStream ois = new ObjectInputStream(  
            new FileInputStream(file));  
        SessionDTO dtoS = (SessionDTO) ois.readObject();  
  
//        类SessionDTO展现的是要在两个服务器之间传输的session。它包含了一些信息在字段data上，
//        该字段需要被序列化。但是它还有另外一个字段activationTime，该字段应该是session对象第
//        一次出现在任意服务器上的时间。它不在我们想要传输的信息之列。这个字段应该在反序列化之
//        后在赋值。进一步来说，没必要把它放在stream中在服务器中传递，因为它占据了不必要的空间。 
//        解决这种情况可以使用writeObject和readObject。
        System.out.println("data : " + dtoS.getData()  
            + " activation time : " + dtoS.getActivationTime());  
        ois.close();
        
        ObjectOutputStream oos2 = new ObjectOutputStream(  
                new FileOutputStream(file));  
        SessionDTOMyself dto2 = new SessionDTOMyself(1);  
        System.out.println("data : " + dto2.getData()  
        + " activation time : " + dto2.getActivationTime());  
        oos2.writeObject(dto2);  
        oos2.close();  
  
        ObjectInputStream ois2 = new ObjectInputStream(  
            new FileInputStream(file));  
        SessionDTOMyself dtoS2 = (SessionDTOMyself) ois2.readObject();  
  
        System.out.println("data : " + dtoS2.getData()  
            + " activation time : " + dtoS2.getActivationTime());  
        ois2.close();  
    }  
}

//在Java中使用Serialization相当简单。如果你有一些对象想要进行序列化，你只需实现Serializable接口。
//然后，你可以使用ObjectOutputStream将该对象保存至文件或发送到其他主机。所有的non-transient和
//non-static字段都将被序列化，并且由反序列化重构造出一模一样的对象联系图（譬如许多引用都指向该对象）
class SessionDTO implements Serializable {  
    private static final long serialVersionUID = 1L;  
    private int data; // Stores session data  
  
    // Session activation time (creation, deserialization)  
    private long activationTime;   
  
    public SessionDTO(int data) {  
        this.data = data;  
        this.activationTime = System.currentTimeMillis();  
    }  
  
    public int getData() {  
        return data;  
    }  
  
    public long getActivationTime() {  
        return activationTime;  
    }  
}

//方法writeObject处理对象的序列化。如果声明该方法，它将会被ObjectOutputStream调用而不是默认
//的序列化进程。如果你是第一次看见它，你会很惊奇尽管它们被外部类调用但事实上这是两个private的
//方法。并且它们既不存在于java.lang.Object，也没有在Serializable中声明。那么ObjectOutputStream
//如何使用它们的呢？这个吗，ObjectOutputStream使用了反射来寻找是否声明了这两个方法。因为
//ObjectOutputStream使用getPrivateMethod，所以这些方法不得不被声明为priate以至于供
//ObjectOutputStream来使用。 
//
//在两个方法的开始处，你会发现调用了defaultWriteObject()和defaultReadObject()。它们做的是默认
//的序列化进程，就像写/读所有的non-transient和 non-static字段(但他们不会去做serialVersionUID
//		的检查).通常说来，所有我们想要自己处理的字段都应该声明为transient。这样的话，
//defaultWriteObject/defaultReadObject便可以专注于其余字段，而我们则可为这些特定的字段
//(指transient)定制序列化。使用那两个默认的方法并不是强制的，而是给予了处理复杂应用时更多的灵活性。 
class SessionDTOMyself implements Serializable {  
    private static final long serialVersionUID = 1L;  
    private transient int data; // Stores session data  
  
    //Session activation time (creation, deserialization)  
    private transient long activationTime;   
  
    public SessionDTOMyself(int data) {  
        this.data = data;  
        this.activationTime = System.currentTimeMillis();  
    }  
  
    private void writeObject(ObjectOutputStream oos) throws IOException {  
        oos.defaultWriteObject();  
        oos.writeInt(data);  
        System.out.println("session serialized");  
    }  
  
    private void readObject(ObjectInputStream ois) throws IOException,  
            ClassNotFoundException {  
        ois.defaultReadObject();  
        data = ois.readInt();  
        activationTime = System.currentTimeMillis();  
        System.out.println("session deserialized");  
    }  
  
    public int getData() {  
        return data;  
    }  
  
    public long getActivationTime() {  
        return activationTime;  
    }  
}