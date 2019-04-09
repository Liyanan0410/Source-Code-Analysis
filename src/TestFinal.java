
public class TestFinal {
	
	final int f1 = 1;//��һ�ֳ�ʼ����ʽ
    final int f2;
    public TestFinal() {
        f2 = 2;//�ڶ��ֳ�ʼ����ʽ
    }
    
    public void finalFunc(final int i, final Value value) {
        // i = 5; ���ܸı�i��ֵ
        // v = new Value(); ���ܸı�v��ֵ
        value.v = 5; // ���Ըı����ö����ֵ
    }
    
	public static void main(String[] args) {
		final int value1 = 1;
        // value1 = 4;//���ܸı���
        final double value2;
        value2 = 2.0;//�ڶ��ָ�ֵ��ʽ
        final Value value3 = new Value(1);//���õĵ�ַ���ܸı䣬�������õ������ǿ��Ըı��
        value3.v = 4;
        
//      ��final�����ǻ������������Լ�String����ʱ������ڱ����ڼ���֪������ȷ��ֵ����������������
//      �������ڳ���ʹ�á�Ҳ����˵���õ���final�����ĵط����൱��ֱ�ӷ��ʵ��������������Ҫ������ʱ
//      ȷ�������ֺ�C�����еĺ��滻�е�������������һ�δ����У����ڱ���b��final���Σ���˻ᱻ��
//      ��������������������ʹ�õ�b�ĵط���ֱ�ӽ�����b �滻Ϊ����ֵ�������ڱ���d�ķ���ȴ��Ҫ������ʱ
//   	ͨ�����������С�������е�������Ӧ�������ˣ�����Ҫע�⣬ֻ���ڱ����ڼ���ȷ��֪��final
//      ����ֵ������£��������Ż�����������Ż���
        String a = "hello2";
        final String b = "hello";
        String d = "hello";
        String c = b + 2;
        String e = d + 2;
        System.out.println((a == c)); //true
        System.out.println((a == e)); //false
        
        final String f = getHello(); 
        String g = f + 2;   
        System.out.println((a == g)); //false
        
        TestFinal testFinal = new TestFinal();
        int i = 0;
        testFinal.changeValue(i);
        System.out.println(i); //0
        
        StringBuffer buffer = new StringBuffer("hello");
        testFinal.changeValue(buffer);
        System.out.println(buffer); //helloworld
        
        testFinal.changeValue2(buffer); //hiworld
        System.out.println(buffer); //helloworld
	}
	
//	�����н�����Կ�������finalȥ����ͬʱ��changeValue����bufferָ�����������󣬲�����Ӱ�쵽main
//	�����е�buffer��ԭ������java���õ���ֵ���ݣ��������ñ��������ݵ������õ�ֵ��Ҳ����˵��ʵ�κ���
//	��ͬʱָ����ͬһ������������β�����ָ����һ�������ʵ�β�û���κ�Ӱ�졣
	public void changeValue2(StringBuffer buffer){
        //buffer����ָ����һ������
        buffer = new StringBuffer("hi");
        buffer.append("world");
        System.out.println(buffer);
    }
	
	public void changeValue(final StringBuffer buffer){
        //final�����������͵Ĳ���������������ָ���������󣬵��Ƕ�����ָ��������ǿ��Ը��ĵġ�
        //buffer = new StringBuffer("hi");
        buffer.append("world");
    }
	
	public void changeValue(final int i){
        //final�������ɸı�
        //i++;
        System.out.println(i);
    }
	
	public static String getHello() { 
        return "hello"; 
    } 

}

class Value {
    int v;
    public Value(int v) {
        this.v = v;
    }
}
