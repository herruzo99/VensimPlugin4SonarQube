import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.FileLinesContextFactory;
import rules.VensimCheck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VensimSquidSensor implements Sensor {


    private static final String NAME = "Vensim Squid Sensor";

    private final Checks<VensimCheck> checks;
    private final FileLinesContextFactory fileLinesContextFactory;
    private final NoSonarFilter noSonarFilter;

    public VensimSquidSensor(FileLinesContextFactory fileLinesContextFactory, CheckFactory checkFactory, NoSonarFilter noSonarFilter) {
        this.checks = checkFactory
                .<VensimCheck>create(VensimRuleRepository.REPOSITORY_KEY)
                .addAnnotatedChecks(VensimRuleRepository.getChecks());


        this.fileLinesContextFactory = fileLinesContextFactory;
        this.noSonarFilter = noSonarFilter;
    }


    @Override
    public void describe(SensorDescriptor sensorDescriptor) {
        sensorDescriptor.onlyOnLanguage(VensimLanguage.KEY)
                .name(NAME)
                .onlyOnFileType(InputFile.Type.MAIN);

    }

    @Override
    public void execute(SensorContext sensorContext) {
        FilePredicates p = sensorContext.fileSystem().predicates();
        Iterable<InputFile> files = sensorContext.fileSystem().inputFiles(p.hasLanguage(VensimLanguage.KEY));
        List<InputFile> list = new ArrayList<>();
        files.forEach(list::add);
        List<InputFile> inputFiles = Collections.unmodifiableList(list);
        VensimScanner scanner = new VensimScanner(sensorContext, checks, fileLinesContextFactory, noSonarFilter, inputFiles);
        scanner.scanFiles();
    }
}
