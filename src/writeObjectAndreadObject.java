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
  
//        ��SessionDTOչ�ֵ���Ҫ������������֮�䴫���session����������һЩ��Ϣ���ֶ�data�ϣ�
//        ���ֶ���Ҫ�����л�����������������һ���ֶ�activationTime�����ֶ�Ӧ����session�����
//        һ�γ���������������ϵ�ʱ�䡣������������Ҫ�������Ϣ֮�С�����ֶ�Ӧ���ڷ����л�֮
//        ���ڸ�ֵ����һ����˵��û��Ҫ��������stream���ڷ������д��ݣ���Ϊ��ռ���˲���Ҫ�Ŀռ䡣 
//        ��������������ʹ��writeObject��readObject��
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

//��Java��ʹ��Serialization�൱�򵥡��������һЩ������Ҫ�������л�����ֻ��ʵ��Serializable�ӿڡ�
//Ȼ�������ʹ��ObjectOutputStream���ö��󱣴����ļ����͵��������������е�non-transient��
//non-static�ֶζ��������л��������ɷ����л��ع����һģһ���Ķ�����ϵͼ��Ʃ��������ö�ָ��ö���
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

//����writeObject�����������л�����������÷����������ᱻObjectOutputStream���ö�����Ĭ��
//�����л����̡�������ǵ�һ�ο����������ܾ��澡�����Ǳ��ⲿ����õ���ʵ����������private��
//�������������ǼȲ�������java.lang.Object��Ҳû����Serializable����������ôObjectOutputStream
//���ʹ�����ǵ��أ������ObjectOutputStreamʹ���˷�����Ѱ���Ƿ���������������������Ϊ
//ObjectOutputStreamʹ��getPrivateMethod��������Щ�������ò�������Ϊpriate�����ڹ�
//ObjectOutputStream��ʹ�á� 
//
//�����������Ŀ�ʼ������ᷢ�ֵ�����defaultWriteObject()��defaultReadObject()������������Ĭ��
//�����л����̣�����д/�����е�non-transient�� non-static�ֶ�(�����ǲ���ȥ��serialVersionUID
//		�ļ��).ͨ��˵��������������Ҫ�Լ�������ֶζ�Ӧ������Ϊtransient�������Ļ���
//defaultWriteObject/defaultReadObject�����רע�������ֶΣ����������Ϊ��Щ�ض����ֶ�
//(ָtransient)�������л���ʹ��������Ĭ�ϵķ���������ǿ�Ƶģ����Ǹ����˴�����Ӧ��ʱ���������ԡ� 
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