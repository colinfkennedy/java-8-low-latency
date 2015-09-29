package unsafe;

import net.openhft.chronicle.core.Jvm;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeMain {
  public static final Unsafe UNSAFE;

  static {
    try {
      Field theUnsafe = Jvm.getField(Unsafe.class, "theUnsafe");
      UNSAFE = (Unsafe) theUnsafe.get(null);
    } catch(IllegalAccessException | IllegalArgumentException e) {
      throw new AssertionError(e);
    }
  }

  public static void main(String[] args) {
    byte[] bytes = {1, 2, 3, 4, 5, 6, 7, 8};
    int hc = UNSAFE.getInt(bytes, 1L);
    System.out.println(hc);


    System.out.println(bytes);
    System.out.println(Integer.toHexString(bytes.hashCode()));

    int hc2 = UNSAFE.getInt(bytes, 1L);
    System.out.println(Integer.toHexString(hc2));

    UNSAFE.putInt(bytes, 1L, 0x1ABCDEF0);

    System.out.println(bytes);
    System.out.println(Integer.toHexString(bytes.hashCode()));

    UNSAFE.putInt(bytes, 1L, 0x0);
    System.out.println(bytes);
    System.out.println(Integer.toHexString(bytes.hashCode()));
  }
}
