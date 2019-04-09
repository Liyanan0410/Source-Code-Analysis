
public class TestFinal {
	
	final int f1 = 1;//第一种初始化方式
    final int f2;
    public TestFinal() {
        f2 = 2;//第二种初始化方式
    }
    
    public void finalFunc(final int i, final Value value) {
        // i = 5; 不能改变i的值
        // v = new Value(); 不能改变v的值
        value.v = 5; // 可以改变引用对象的值
    }
    
	public static void main(String[] args) {
		final int value1 = 1;
        // value1 = 4;//不能改变了
        final double value2;
        value2 = 2.0;//第二种赋值方式
        final Value value3 = new Value(1);//引用的地址不能改变，但是引用的内容是可以改变的
        value3.v = 4;
        
//      当final变量是基本数据类型以及String类型时，如果在编译期间能知道它的确切值，则编译器会把它当
//      做编译期常量使用。也就是说在用到该final变量的地方，相当于直接访问的这个常量，不需要在运行时
//      确定。这种和C语言中的宏替换有点像。因此在上面的一段代码中，由于变量b被final修饰，因此会被当
//      做编译器常量，所以在使用到b的地方会直接将变量b 替换为它的值。而对于变量d的访问却需要在运行时
//   	通过链接来进行。想必其中的区别大家应该明白了，不过要注意，只有在编译期间能确切知道final
//      变量值的情况下，编译器才会进行这样的优化。
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
	
//	从运行结果可以看出，将final去掉后，同时在changeValue中让buffer指向了其他对象，并不会影响到main
//	方法中的buffer，原因在于java采用的是值传递，对于引用变量，传递的是引用的值，也就是说让实参和形
//	参同时指向了同一个对象，因此让形参重新指向另一个对象对实参并没有任何影响。
	public void changeValue2(StringBuffer buffer){
        //buffer重新指向另一个对象
        buffer = new StringBuffer("hi");
        buffer.append("world");
        System.out.println(buffer);
    }
	
	public void changeValue(final StringBuffer buffer){
        //final修饰引用类型的参数，不能再让其指向其他对象，但是对其所指向的内容是可以更改的。
        //buffer = new StringBuffer("hi");
        buffer.append("world");
    }
	
	public void changeValue(final int i){
        //final参数不可改变
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
