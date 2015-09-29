package shm;

import net.openhft.chronicle.bytes.MappedBytesStore;
import net.openhft.chronicle.bytes.MappedFile;

import java.io.IOException;

public class ShmMain {
  public static void main(String[] args) throws IOException {
    MappedFile mappedFile = MappedFile.mappedFile("mapped", 64*1024);
    MappedBytesStore mbb = mappedFile.acquireByteStore(0);
    System.out.println(mbb.compareAndSwapInt(0L, 0, 1));
  }
}
