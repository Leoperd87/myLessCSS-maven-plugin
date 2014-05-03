myLessCSS-maven-plugin
===================

LessCSS. Requires Maven 3.x or higher.

### Usage
The following POM plugin configuration

    <plugins>
        <plugin>
            <groupId>ua.in.dej</groupId>
            <artifactId>myLessCss</artifactId>
            <version>1.0-SNAPSHOT</version>
            <executions>
                <execution>
                    <goals>
                        <goal>extract</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <options>
                    <!--<option>-->
                        <!---x-->
                    <!--</option>-->
                </options>

                <fileList>
                    <myFileRecord>
                        <srcPath>test\styles\main.less</srcPath>
                        <dstPath>test\styles\main.css</dstPath>
                    </myFileRecord>
                    <myFileRecord>
                        <srcPath>test\styles\onemore.less</srcPath>
                        <dstPath>test\styles\onemore.gss</dstPath>
                    </myFileRecord>
                    <myFileRecord>
                        <srcPath>test\styles\error.less</srcPath>
                        <dstPath>test\styles\error.css</dstPath>
                    </myFileRecord>
                </fileList>


            </configuration>
        </plugin>
    </plugins>

### Supported platforms

The plugin supplies a NodeJS binary for the following platforms:

* Windows (32 and 64 bit)
* Mac OS (32 and 64 bit)
* Linux (i386 and amd64)
