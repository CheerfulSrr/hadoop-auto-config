package io.greekn;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HadoopAutoConfigApplicationTest {

    @Test
    public void test1() throws Exception {
        TarArchiveInputStream stream = new TarArchiveInputStream(
                new GzipCompressorInputStream(
                        new BufferedInputStream(
                                new FileInputStream("D:\\tmp\\hadoop-2.3.0-cdh5.0.0.tar.gz"))));

        byte[] buffer = new byte[4096];
        TarArchiveEntry tarArchiveEntry;
        while ((tarArchiveEntry = stream.getNextTarEntry()) != null) {
            if (tarArchiveEntry.isDirectory()) {
                continue;
            }

            File outputFile = new File("D:\\tmptar\\" + tarArchiveEntry.getName());

            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                while (stream.read(buffer) > 0) {
                    fos.write(buffer);
                }
            }
        }

    }

}