package shm;

import net.openhft.chronicle.bytes.MappedBytesStore;
import net.openhft.chronicle.bytes.MappedFile;

import java.io.IOException;

public class ShmToogleMain {
  static final boolean set = Boolean.getBoolean("set");

  public static void main(String[] args) throws IOException {
    MappedFile mappedFile = MappedFile.mappedFile("mapped", 64*1024);
    MappedBytesStore mbb = mappedFile.acquireByteStore(0);
    int from = set ? 0 : 1;
    int to = set ? 1 : 0;
    for(int i = 0; i < 10; i++) {
      while(!mbb.compareAndSwapInt(0L, from, to))
        Thread.yield();;
    }
    System.out.println("Toggling now");
    long start = System.nanoTime();
    int runs = 10_000;
    for(int i = 0; i < runs; i++) {
      while(!mbb.compareAndSwapInt(0L, from, to));
    }
    long time = System.nanoTime() - start;
    System.out.println("Toggle took an average of " + time/runs + "ns.");
  }
}
