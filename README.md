myLessCSS-maven-plugin
===================

LessCSS. Requires Maven 3.x or higher.

### Usage
The following POM plugin configuration

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    ...
    <build>
        ...
        <plugins>
            <plugin>
                <groupId>ua.in.dej</groupId>
                <artifactId>myLessCss</artifactId>
                <version>1.0-less1.7.0-RELEASE</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>extract</goal>
                        </goals>
                        <!-- Optional. Default value: package. -->
                        <phase>prepare-package</phase>
                    </execution>
                </executions>
                <configuration>

                    <!-- Optional. LessJS compiler arguments. -->
                    <options>
                        <option>
                            -x
                        </option>
                    </options>

                    <!-- Optional. Default value: ${project.build.directory} -->
                    <buildDirectory>
                        ${project.basedir}/target/test/styles
                    </buildDirectory>

                    <!-- Optional. Remove NodeJS binary and LessJS sources on build complete. Default value: true -->
                    <cleanAfter>
                        false
                    </cleanAfter>

                    <!-- Optional. LessCSS executable script path. -->
                    <lesscssBinaryPath>
                        /tmp/lessjs/bin/lessc
                    </lesscssBinaryPath>

                    <!-- Optional. NodeJS executable binary path. -->
                    <nodejsBinaryPath>
                        /tmp/node
                    </nodejsBinaryPath>

                    <!-- Required. List of source less files and destination files. -->
                    <fileList>
                        <myFileRecord>
                            <srcPath>main.less</srcPath>
                            <dstPath>main.css</dstPath>
                        </myFileRecord>
                        <myFileRecord>
                            <srcPath>onemore.less</srcPath>
                            <dstPath>onemore.gss</dstPath>
                        </myFileRecord>
                        <myFileRecord>
                            <srcPath>error.less</srcPath>
                            <dstPath>error.css</dstPath>
                        </myFileRecord>
                    </fileList>

                </configuration>
            </plugin>
        </plugins>
    </build>
    ...
    <pluginRepositories>
        ...
        <pluginRepository>
            <id>sonatype</id>
            <url>https://oss.sonatype.org/content/repositories/releases/</url>
        </pluginRepository>
    </pluginRepositories>
</project>

### Supported platforms

The plugin supplies a NodeJS binary for the following platforms:

* Windows (32 and 64 bit)
* Mac OS (32 and 64 bit)
* Linux (i386 and amd64)
