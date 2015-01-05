package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * Static class for using reflection to do things we are not supposed to. 
 * (aka call private methods, get private var values, etc.)
 * 
 * @author F4113nb34st
 *
 */
public final class ReflectionUtil
{
	/**
	 * Returns the given method from the given object.
	 * @param object The object to look in.
	 * @param name The name of the method.
	 * @param args The arguments of the method.
	 * @return The method.
	 */
	@SuppressWarnings({"rawtypes"})
	public static final Method getMethod(Object obj, String name, Class... args)
	{
		return getStaticMethod(obj.getClass(), name, args);
	}
	
	/**
	 * Returns the given static method from the given clazz.
	 * @param clazz The class to look in.
	 * @param name The name of the method.
	 * @param args The arguments of the method.
	 * @return The method.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Method getStaticMethod(Class clazz, String name, Class... args)
	{
		try
		{
			return clazz.getMethod(name, args);
		} catch(NoSuchMethodException ex)
		{
			System.err.println("[ReflectionUtil] No such method " + name + " with the given args!");
			ex.printStackTrace();
		} catch(SecurityException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns the given private method from the given object.
	 * @param object The object to look in.
	 * @param name The name of the method.
	 * @param args The arguments of the method.
	 * @return The method.
	 */
	@SuppressWarnings({"rawtypes"})
	public static final Method getPrivateMethod(Object obj, String name, Class... args)
	{
		return getPrivateStaticMethod(obj.getClass(), name, args);
	}
	
	/**
	 * Returns the given private static method from the given clazz.
	 * @param clazz The class to look in.
	 * @param name The name of the method.
	 * @param args The arguments of the method.
	 * @return The method.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Method getPrivateStaticMethod(Class clazz, String name, Class... args)
	{
		try
		{
			return clazz.getDeclaredMethod(name, args);
		} catch(NoSuchMethodException ex)
		{
			System.err.println("[ReflectionUtil] No such method " + name + " with the given args!");
			ex.printStackTrace();
		} catch(SecurityException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Calls the given method.
	 * @param method The method to call.
	 * @param instance The object to call it on.
	 * @param args The args to pass to the method.
	 * @return The result of the call.
	 * @throws RuntimeException if the resulting method throws an exception
	 */
	public static final Object callMethod(Method method, Object instance, Object...args)
	{
		try
		{
			return method.invoke(instance, args);
		}catch(IllegalAccessException ex)
		{
			System.err.println("[ReflectionUtil] Method " + method.getName() + " is not accessible, use makePublic to access it!");
		}catch(IllegalArgumentException ex)
		{
			System.err.println("[ReflectionUtil] Invalid args for Method " + method.getName() + "!");
		}catch(InvocationTargetException ex)
		{
			ex.printStackTrace();
			throw new RuntimeException();
		}
		makePrivate(method);
		return null;
	}
	
	/**
	 * Calls the given static method.
	 * @param method The method to call.
	 * @param args The args to pass to the method.
	 * @return The result of the call.
	 * @throws RuntimeException if the resulting method throws an exception
	 */
	public static final Object callStaticMethod(Method method, Object...args)
	{
		return callMethod(method, null, args);
	}
	
	/**
	 * Calls the given private static method.
	 * @param method The method to call.
	 * @param args The args to pass to the method.
	 * @return The result of the call.
	 * @throws RuntimeException if the resulting method throws an exception
	 */
	public static final Object callPrivateStaticMethod(Method method, Object...args)
	{
		return callPrivateMethod(method, null, args);
	}
	
	/**
	 * Calls the given private method.
	 * @param method The method to call.
	 * @param instance The object to call it on.
	 * @param args The args to pass to the method.
	 * @return The result of the call.
	 * @throws RuntimeException if the resulting method throws an exception
	 */
	public static final Object callPrivateMethod(Method method, Object instance, Object...args)
	{
		makePublic(method);
		try
		{
			return method.invoke(instance, args);
		}catch(IllegalAccessException ex)
		{
			System.err.println("[ReflectionUtil] Method " + method.getName() + " is not accessible, use makePublic to access it!");
		}catch(IllegalArgumentException ex)
		{
			System.err.println("[ReflectionUtil] Invalid args for Method " + method.getName() + "!");
		}catch(InvocationTargetException ex)
		{
			ex.printStackTrace();
			throw new RuntimeException();
		}
		makePrivate(method);
		return null;
	}
	
	/**
	 * Makes the given method public.
	 * @param method The method to modify.
	 */
	public static final void makePublic(Method method)
	{
		method.setAccessible(true);
	}
	
	/**
	 * Makes the given method private.
	 * @param method The method to modify.
	 */
	public static final void makePrivate(Method method)
	{
		method.setAccessible(false);
	}
}
