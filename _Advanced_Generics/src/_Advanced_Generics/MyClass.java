package _Advanced_Generics;

import java.util.List;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyClass {

	protected List<String> stringList = new ArrayList<String>();
	
	public List<String> getStringList() {
		return this.stringList;
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		Method method = MyClass.class.getMethod("getStringList", null);
		Type returnType = method.getGenericReturnType();
		
		if (returnType instanceof ParameterizedType) {
			System.out.println("return type type is: " + returnType.getTypeName());
			ParameterizedType type = (ParameterizedType) returnType;
			Type[] typeArguments = type.getActualTypeArguments();
			for (Type typeArgument : typeArguments) {
				Class<? extends Type> typeArgsClass = typeArgument.getClass();
				System.out.println("TypeClass = " + typeArgsClass);
			}
		}
	}
}
