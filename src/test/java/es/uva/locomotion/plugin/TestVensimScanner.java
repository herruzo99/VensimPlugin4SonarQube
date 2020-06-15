package es.uva.locomotion.plugin;


import es.uva.locomotion.service.ServiceController;
import es.uva.locomotion.testutilities.RuleTestUtilities;
import es.uva.locomotion.rules.VensimCheck;
import es.uva.locomotion.utilities.JsonSymbolTableBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.measure.NewMeasure;
import org.sonar.api.utils.log.Logger;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static es.uva.locomotion.testutilities.TestUtilities.*;
import static org.mockito.Mockito.*;

public class TestVensimScanner {

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testScannerLogMessageParseException() throws Exception {

        Logger logger = Mockito.mock(Logger.class);
        VensimScanner.LOG = logger;
        InputFile inputFile = Mockito.mock(InputFile.class);
        when(inputFile.contents()).thenReturn("This isn't a vensim model");
        when(inputFile.toString()).thenReturn("notAVensimModel.mdl");



        VensimScanner scanner = RuleTestUtilities.getScanner();


        List<InputFile> files = new ArrayList<>();
        files.add(inputFile);
        scanner.scanFiles(files);

        Mockito.verify(logger).error("Unable to parse the file '{}'. Message {}","notAVensimModel.mdl", "mismatched input '<EOF>' expecting {':IGNORE:', ':EXCEPT:', '[', ':=', Keyword, INFO_UNIT}");

    }


    @Test
    public void testIfAfileFailsTheRestExecutes() throws IOException, NoSuchFieldException, IllegalAccessException {


        Logger logger = Mockito.mock(Logger.class);


        JsonSymbolTableBuilder builder = Mockito.mock(JsonSymbolTableBuilder.class);
        VensimScanner.LOG = logger;
        InputFile fileBefore = Mockito.mock(InputFile.class);
        InputFile wrongFile = Mockito.mock(InputFile.class);
        InputFile fileAfter = Mockito.mock(InputFile.class);
        when(wrongFile.contents()).thenReturn("This isn't a vensim model");
        when(fileBefore.contents()).thenReturn(loadFile("invertedDependencies.mdl"));
        when(fileAfter.contents()).thenReturn(loadFile("testCyclicDependencies.mdl"));

        ServiceController mockServiceController = mock(ServiceController.class);


        List<InputFile> files = new ArrayList<>();
        files.add(fileBefore);
        files.add(wrongFile);
        files.add(fileAfter);


        SensorContext context = Mockito.mock(SensorContext.class);
        when(context.isCancelled()).thenReturn(false);
        NewMeasure measure = Mockito.mock(NewMeasure.class);
        when(measure.forMetric(Mockito.any())).thenReturn(measure);
        when(measure.on(Mockito.any())).thenReturn(measure);
        when(measure.withValue(Mockito.any())).thenReturn(measure);

        when(context.newMeasure()).thenReturn(measure);

        CheckFactory factory = new CheckFactory(RuleTestUtilities.getAllActiveRules());
        Checks<VensimCheck> checks =  factory.<VensimCheck>create(VensimRuleRepository.REPOSITORY_KEY)
                .addAnnotatedChecks(VensimRuleRepository.getChecks());

        VensimScanner scanner = spy(new VensimScanner(context,checks,builder,mockServiceController));
        when(mockServiceController.getSymbolsFromDb(anyList())).thenReturn(null);

        Mockito.doCallRealMethod().when(scanner).scanFiles(files);
        Mockito.doCallRealMethod().when(scanner).scanFile(Mockito.any());
        Mockito.doCallRealMethod().when(scanner).getParseTree(Mockito.any());
        Mockito.doCallRealMethod().when(scanner).checkIssues(Mockito.any());



        Mockito.doNothing().when(scanner).generateJsonOutput();


        scanner.scanFiles(files);


        Mockito.verify(scanner,Mockito.times(2)).checkIssues(Mockito.any());
        Mockito.verify(logger,Mockito.times(1)).error(Mockito.any(),Mockito.any(),Mockito.any());
        Mockito.verify(logger,Mockito.times(0)).warn(Mockito.any());

    }


}
