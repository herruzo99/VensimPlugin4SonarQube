package es.uva.locomotion.plugin;

import es.uva.locomotion.parser.visitors.VensimVisitorContext;
import es.uva.locomotion.service.ServiceController;
import es.uva.locomotion.parser.*;
import es.uva.locomotion.rules.VensimCheck;


import es.uva.locomotion.utilities.JsonSymbolTableBuilder;
import es.uva.locomotion.utilities.SymbolTableGenerator;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.TextRange;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VensimScanner {

    protected static Logger LOG = Loggers.get(VensimScanner.class);

    private final SensorContext context;
    private final Checks<VensimCheck> checks;
    private  final JsonSymbolTableBuilder jsonBuilder;
  
    private ServiceController serviceController;

    public VensimScanner(SensorContext context, Checks<VensimCheck> checks, JsonSymbolTableBuilder builder, ServiceController serviceController) {
        this.context = context;
        this.checks = checks;
        this.jsonBuilder = builder;
        this.serviceController = serviceController;


    }

    public void scanFiles(List<InputFile> inputFiles) {
        for (InputFile vensimFile : inputFiles) {
            if (context.isCancelled()) {
                return;
            }
            try {

                scanFile(vensimFile);
            } catch (Exception e) {
                LOG.warn("Unable to analyze file '{}'. Error: {}", vensimFile.toString(), e);
            }
        }

       
        generateJsonOutput();

    }

    protected void generateJsonOutput() {
       JsonArray symbolTable =  jsonBuilder.build();

        try {

            File file = new File("symbolTable.json");
            JsonWriter writer = Json.createWriter(new FileOutputStream(file));
            writer.writeArray(symbolTable);
            writer.close();
        } catch (FileNotFoundException e) {
            LOG.warn("Unable to create symbolTable.json, Error:{}",e.getMessage() );
        }

    }

    protected ModelParser.FileContext getParseTree(String file_content){
        ModelLexer lexer = new ModelLexer(CharStreams.fromString(file_content));

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ModelParser parser = new ModelParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new VensimErrorListener());

        return parser.file();
    }

    public void scanFile(InputFile inputFile) {

        try {
            String content = inputFile.contents();
            String module = getModuleNameFromFileName(inputFile.filename());

            ModelParser.FileContext root = getParseTree(content);


            SymbolTable table = SymbolTableGenerator.getSymbolTable(root);
            jsonBuilder.addSymbolTable(inputFile.filename(),table);

            SymbolTable dbTable = null;
            if(serviceController.isAuthenticated())
                dbTable = serviceController.getSymbolsFromDb(new ArrayList<>(table.getSymbols()));

            VensimVisitorContext visitorContext = new VensimVisitorContext(root, table, dbTable);

            checkIssues(visitorContext);
            saveIssues(inputFile,visitorContext.getIssues());

            int lines = content.split("[\r\n]+").length;

            context.<Integer>newMeasure().forMetric(CoreMetrics.NCLOC).on(inputFile).withValue(lines).save();

            if(serviceController.isAuthenticated() && dbTable != null)
                serviceController.injectNewSymbols(module,new ArrayList<>(table.getSymbols()),dbTable);
        } catch (IOException e) {
            LOG.error("Unable to analyze file '{}'. Error: {}", inputFile.filename(), e);
        }catch (ParseCancellationException e){
            LOG.error("Unable to parse the file '{}'. Message {}",inputFile.filename(),e.getLocalizedMessage());
        }

    }

    protected String getModuleNameFromFileName(String fileName){
        String suffix = VensimLanguage.VENSIM_PLAIN_TEXT_SUFIX;
        if(fileName.endsWith(suffix))
            return fileName.substring(0,fileName.length()-suffix.length());
        else
            return fileName;


    }


    public void checkIssues(VensimVisitorContext fileContext){

        for (VensimCheck check : checks.all()) {
            check.scan(fileContext);
        }
    }


    protected void saveIssues(InputFile file, List<Issue> issues){
        for (Issue preciseIssue : issues) {
            RuleKey ruleKey = checks.ruleKey(preciseIssue.getCheck());
 
            NewIssue newIssue = context
                    .newIssue()
                    .forRule(ruleKey);


            TextRange range = file.selectLine(preciseIssue.getLine());
            NewIssueLocation newLocation = newIssue.newLocation()
                    .on(file);
            newLocation.message(preciseIssue.getMessage());
            newLocation.at(range);

            newIssue.at(newLocation);
            newIssue.overrideSeverity(preciseIssue.getSeverity());
            newIssue.save();
        }

    }


}
