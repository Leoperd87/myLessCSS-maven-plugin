package ua.in.dej.myLessCss;


import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.codehaus.plexus.archiver.UnArchiver;
import org.codehaus.plexus.archiver.manager.ArchiverManager;
import org.codehaus.plexus.archiver.manager.NoSuchArchiverException;
import org.codehaus.plexus.util.FileUtils;

import java.io.*;
import java.util.List;

/**
 * Mojo for adding the LessCSS executable to a build environment.
 *
 * @goal extract
 * @phase package
 */
public class ExtractMojo extends AbstractMojo {

    /**
     * Directory containing the build files.
     *
     * @parameter expression="${project.build.directory}"
     */
    private String buildDirectory;

    /**
     * Params to executor
     *
     * @parameter default-value=[]
     */
    private String[] options;

    /**
     * File list to executor
     *
     * @parameter default-value=[]
     */
    private myFileRecord[] fileList;

    /**
     * @component
     */
    protected ArchiverManager archiverManager;

    /**
     * @parameter default-value="${plugin}"
     * @required
     * @readonly
     */
    protected PluginDescriptor pluginDescriptor;

    /**
     *
     *
     * @throws org.apache.maven.plugin.MojoExecutionException if there is a problem
     */
    public void execute() throws MojoExecutionException {
        String separator = System.getProperty("file.separator");

        if (fileList != null && fileList.length > 0) {
            File targetDir = new File(buildDirectory + separator + "less");

            prepareTargetDirectory(targetDir);
            extractArtifact(findArtifact(pluginDescriptor.getArtifacts(), "nodejs-maven-binaries"), targetDir);
            extractArtifact(findArtifact(pluginDescriptor.getArtifacts(), "lesscss-maven-sources"), targetDir);

            String argv[] = new String[]{
                    targetDir + separator + "node",
                    targetDir + separator + "less.js" + separator + "bin" + separator + "lessc"
            };

            if (options != null && options.length > 0) {
                for (String option : options) {
                    argv = this.arrRecordToArray(argv, option);
                }
            }

            for (myFileRecord fr : fileList) {
                System.out.print(fr.getSrcPath() + " > " + fr.getDstPath() + "\n");

                String[] thisBuildArgs = argv.clone();

                thisBuildArgs = this.arrRecordToArray(thisBuildArgs, buildDirectory + separator + fr.getSrcPath());
                thisBuildArgs = this.arrRecordToArray(thisBuildArgs, buildDirectory + separator + fr.getDstPath());

                Runtime r = Runtime.getRuntime();
                Process p = null;
                BufferedReader is;
                String line;

                try {
                    p = r.exec(thisBuildArgs);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                is = new BufferedReader(new InputStreamReader(p.getInputStream()));

                try {
                    while ((line = is.readLine()) != null)
                        System.out.println(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                is = new BufferedReader(new InputStreamReader(p.getErrorStream()));

                Boolean hasError = false;

                try {
                    while ((line = is.readLine()) != null) {
                        System.err.println(line);
                        hasError = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (hasError) {
                    throw new NullPointerException("demo"); //todo
                }

                try {
                    p.waitFor();  // wait for process to complete
                } catch (InterruptedException e) {
                    System.err.println(e);  // "Can'tHappen"
                    return;
                }
            }
        } else {
            System.out.print("No files");
        }
    }

    private String[] arrRecordToArray(String[] in, String addR) {
        final int l = in.length + 1;
        String[] out = new String[l];
        for (int i = 0; i < in.length; i++) {
            out[i] = in[i];
        }
        out[in.length] = addR;
        return out;
    }

    private void prepareTargetDirectory(File targetDir) throws MojoExecutionException {
        try {
            targetDir.mkdirs();
            FileUtils.cleanDirectory(targetDir);
        } catch (IOException e) {
            throw new MojoExecutionException("Could not prepare targetDirectory for extraction: " + e.getMessage(), e);
        }

    }

    private Artifact findArtifact(List<Artifact> artifacts, String artifactName) throws MojoExecutionException {
        for (Artifact artifact : artifacts) {
            if (artifact.getArtifactId().equals(artifactName)) {
                return artifact;
            }
        }
        throw new MojoExecutionException("Could not locate dependency of plugin. Is the build environment's platform supported?");
    }

    private void extractArtifact(Artifact artifact, File destinationDirectory) throws MojoExecutionException {
        try {
            UnArchiver unArchiver = archiverManager.getUnArchiver(artifact.getFile());
            unArchiver.setUseJvmChmod(true);
            unArchiver.setIgnorePermissions(false);
            unArchiver.setSourceFile(artifact.getFile());
            unArchiver.setDestDirectory(destinationDirectory);
            unArchiver.extract();
        } catch (NoSuchArchiverException e) {
            throw new MojoExecutionException("Could not find archiver for artifact " + artifact.getArtifactId());
        }
    }
}
